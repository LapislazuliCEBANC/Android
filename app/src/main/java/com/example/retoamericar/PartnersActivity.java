package com.example.retoamericar;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PartnersActivity extends AppCompatActivity {
    ListView lst;
    Button crear;
    ArrayList<Integer> identificadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        lst = findViewById(R.id.lsvPartnersLista);
        cargar();

        crear = findViewById(R.id.btnPartnersCrear);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartnersActivity.this, NuevoPartnerActivity.class);
                startActivity(i);
            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogo(identificadores.get(i));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargar();
    }

    private void cargar(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        String id = String.valueOf(((GlobalData) this.getApplication()).getIdComercial());
        //Select campos, "as _id" obligatorio para el SimpleCursorAdapter
        String[] campos = new String[]{"idPartner as _id","nombre","direccion","poblacion","cif","telefono","email"};
        //Where campos
        String[] args = new String[]{id};

        Cursor c = db.query("Partners", campos, "idComercial=?", args, null, null, null);
        if (c.moveToFirst()){
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.partner, c,
                    new String[]{"_id","nombre","direccion","poblacion","cif","telefono","email"},
                    new int[]{R.id.txvPartnerID, R.id.txvPartnerNombre, R.id.txvPartnerDireccion, R.id.txvPartnerPoblacion, R.id.txvPartnerCIF, R.id.txvPartnerTelefono, R.id.txvPartnerEmail},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            lst.setAdapter(sca);
            do {
                identificadores.add(c.getInt(0));
            }while (c.moveToNext());
        }
        db.close();
    }

    private void dialogo(int id) {
        String[] opc = {"Modificar","Eliminar","Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opcion");
        builder.setItems(opc, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                esperador(id, i);
            }
        });
        builder.show();
    }

    private void eliminar(int id){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getWritableDatabase();

        String[] args = {String.valueOf(id)};
        db.delete("Partners","idPartner=?",args);
        cargar();
    }

    private void modificar(int id){
        Intent i = new Intent(PartnersActivity.this, ModificarPartner.class);
        i.putExtra("id", String.valueOf(id));
        startActivity(i);
    }

    private void esperador(int id, int respuesta){
        if (respuesta==0){
            modificar(id);
        }else if (respuesta==1){
            eliminar(id);
        }
    }
}