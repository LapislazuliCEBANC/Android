package com.example.retoamericar;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class PartnersActivity extends AppCompatActivity {
    ListView lst;
    Button crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        cargar();
        ((GlobalData) this.getApplication()).setIdComercial(5);
        crear = findViewById(R.id.btnPartnersCrear);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartnersActivity.this, NuevoPartnerActivity.class);
                startActivity(i);
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

        String id = String.valueOf((((GlobalData) this.getApplication()).getIdComercial()));

        String[] campos = new String[]{"nombre as _id","direccion","poblacion","cif","telefono","email"};
        String[] args = new String[]{id};

        Cursor c = db.query("Partners", campos, "idComercial=?", args, null, null, null);
        if (c.moveToFirst()){
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.partner,c,new String[]{"_id","direccion","poblacion","cif","telefono","email"},new int[]{
                    R.id.txvPartnerNombre,
                    R.id.txvPartnerDireccion,
                    R.id.txvPartnerPoblacion,
                    R.id.txvPartnerCIF,
                    R.id.txvPartnerTelefono,
                    R.id.txvPartnerEmail
            }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            lst = findViewById(R.id.lsvPartnersLista);
            lst.setAdapter(sca);
        }
        db.close();
    }

}