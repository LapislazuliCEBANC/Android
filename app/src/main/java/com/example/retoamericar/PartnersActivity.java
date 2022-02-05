package com.example.retoamericar;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
                //TODO: El dialogo personalizado para eliminar o modificar partners (o que devuelva que boton ha pulsado entre cancelar,modificar,eliminar)
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
        //Select campos, "as _id" obligatorio para el SCA
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
        }
        db.close();
    }

}