package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class NuevoPedidoActivity extends AppCompatActivity {
    int pos;
    TextView idAlbaran;
    RecyclerView lista;
    Button crear;
    EditText cantCrear;
    Cursor cursorArticulos;
    Button eliminar;
    EditText idEliminar;
    String id;
    Spinner spin;
    ArrayList<Integer> identificadores = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);

        Intent data = getIntent();
        id = data.getStringExtra("id");

        idAlbaran = findViewById(R.id.txvNuevoPedidoIdAlbaran);
        idAlbaran.setText(id);

        cantCrear=findViewById(R.id.edtNuevoPedidoCantidad);
        idEliminar =findViewById(R.id.edtNuevoPedidoEliminar);

        lista=findViewById(R.id.rcvNuevoPedidoLista);
        cargarLista();

        spin=findViewById(R.id.spnNuevoPedidoSpinner);
        cargarSpinner();
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        crear=findViewById(R.id.btnNuevoPedidoCrear);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantCrear.getText().length()>0){
                    crearLinea();

                }

            }
        });

        eliminar=findViewById(R.id.btnNuevoEliminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idEliminar.getText().length()>0){
                    eliminarLinea(idEliminar.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
    }

    private void cargarLista(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        String[] args = new String[]{id};

        Cursor c = db.rawQuery(
                "SELECT l.idLinea as _id, descripcion, cantidad, precio " +
                    "FROM Articulos a, Lineas l " +
                    "WHERE l.idAlbaran = ? AND a.idArticulo = l.idArticulo",args);
        if (c.moveToFirst()){
            lista.setVisibility(View.VISIBLE);
            lista.setLayoutManager(new GridLayoutManager(this,1));

            RecyclerViewAdapterLinea rva = new RecyclerViewAdapterLinea(c);
            rva.notifyDataSetChanged();
            lista.setAdapter(rva);
        }else{
            lista.setVisibility(View.INVISIBLE);
        }
        db.close();
    }

    private void cargarSpinner(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        String id = String.valueOf((((GlobalData) this.getApplication()).getIdComercial()));

        String[] campos = new String[]{"idArticulo", "descripcion as _id", "prCost", "prVent", "existencias"};

        cursorArticulos = db.query("Articulos", campos, null, null, null, null, null);
        if (cursorArticulos.moveToFirst()){
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.articulo, cursorArticulos,
                    new String[]{"_id","prCost","prVent","existencias"},
                    new int[]{R.id.txvArticuloDescripcion, R.id.txvArticuloPrCost, R.id.txvArticuloPrVent, R.id.txvArticuloExistencias},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            sca.setDropDownViewResource(R.layout.articulo_pedido);
            spin.setAdapter(sca);
            do {
                identificadores.add(cursorArticulos.getInt(0));
            }while (cursorArticulos.moveToNext());
        }
        db.close();
    }

    private void crearLinea(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getWritableDatabase();

        cursorArticulos.moveToPosition(pos);
        int cant = Integer.valueOf(cantCrear.getText().toString());
        int existencias = cursorArticulos.getInt(4);
        if((existencias-cant)>=0){
            ContentValues nuevo = new ContentValues();
            nuevo.put("idAlbaran", id);
            nuevo.put("idArticulo", identificadores.get(pos));
            nuevo.put("cantidad", cant);
            nuevo.put("precio", cursorArticulos.getInt(2));

            db.insert("Lineas", null, nuevo);

            ContentValues valores = new ContentValues();
            valores.put("existencias",existencias-cant);
            db.update("Articulos", valores, "idArticulo="+identificadores.get(pos),null);
            cantCrear.setText("");
        }
        cargarSpinner();
        cargarLista();
    }

    private void eliminarLinea(String id){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getWritableDatabase();

        String[] args = new String[] {id};

        Cursor c=db.rawQuery("SELECT idLinea " +
                                 "FROM Lineas " +
                                 "WHERE idLinea=?",args);
        if (c.moveToFirst()){
            db.delete("Lineas","idLinea=?",args);
            cargarLista();
        }
    }
}