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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PedidosActivity extends AppCompatActivity {
    Spinner spin;
    RecyclerView lista;
    Button crear;
    int pos;
    ArrayList<Integer> identificadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        spin = findViewById(R.id.spnPedidosSpinner);
        lista = findViewById(R.id.rcvPedidosLista);
        crear = findViewById(R.id.btnPedidosCrear);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PedidosActivity.this, NuevoPedidoActivity.class);
                i.putExtra("id",String.valueOf(crearPedido()));
                startActivity(i);
            }
        });
        cargarSpinner();


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
                cargarRecyclerView();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarSpinner();
    }

    private void cargarSpinner(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        String id = String.valueOf((((GlobalData) this.getApplication()).getIdComercial()));

        String[] campos = new String[]{"idPartner as _id","nombre","direccion","poblacion","cif","telefono","email"};
        String[] args = new String[]{id};

        Cursor c = db.query("Partners", campos, "idComercial=?", args, null, null, null);
        if (c.moveToFirst()){

            SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.partner,c,
                    new String[]{"_id","nombre","direccion","poblacion","cif","telefono","email"},
                    new int[]{R.id.txvPartnerID, R.id.txvPartnerNombre, R.id.txvPartnerDireccion, R.id.txvPartnerPoblacion, R.id.txvPartnerCIF, R.id.txvPartnerTelefono, R.id.txvPartnerEmail
            }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            sca.setDropDownViewResource(R.layout.partner_pedidos);
            spin.setAdapter(sca);
            do{
                identificadores.add(c.getInt(0));
            }while (c.moveToNext());
        }
        db.close();
    }

    private void cargarRecyclerView(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();
        String idc = String.valueOf((((GlobalData) this.getApplication()).getIdComercial()));

        String[] camposPartner = new String[]{"idPartner"};
        String[] argsPartner = new String[]{idc};

        Cursor partners = db.query("Partners", camposPartner, "idComercial=?", argsPartner, null, null, null);
        if (partners.moveToPosition(pos)){

            String[] campos = new String[]{"idAlbaran as _id","fechaAlbaran","fechaEnvio","FechaPago"};
            String[] args = new String[]{String.valueOf(partners.getInt(0))};

            Cursor c = db.query("Albaranes", campos, "idPartner=?", args, null, null, null);
            if (c.moveToFirst()){
                lista.setVisibility(View.VISIBLE);
                lista.setLayoutManager(new GridLayoutManager(this,4));

                RecyclerViewAdapter rva = new RecyclerViewAdapter(c);
                rva.notifyDataSetChanged();
                lista.setAdapter(rva);
            }else{
                lista.setVisibility(View.INVISIBLE);
            }
        }
        db.close();
    }

    private int crearPedido(){
        int result = 1;
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getWritableDatabase();
        String idc = String.valueOf(((GlobalData) this.getApplication()).getIdComercial());

        String[] camposPartner = new String[]{"idPartner"};
        String[] argsPartner = new String[]{idc};

        Cursor partners = db.query("Partners", camposPartner, "idComercial=?", argsPartner, null, null, null);
        if (partners.moveToPosition(pos)){

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();

            ContentValues nuevo = new ContentValues();
            nuevo.put("fechaAlbaran", dateFormat.format(date));
            nuevo.put("idPartner", identificadores.get(pos));
            db.insert("Albaranes", null, nuevo);
            Cursor c = db.rawQuery("SELECT MAX(idAlbaran) FROM Albaranes",null,null);
            c.moveToFirst();
            result = c.getInt(0);
        }
        db.close();
        return result;
    }
}