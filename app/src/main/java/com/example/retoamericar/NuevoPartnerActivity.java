package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NuevoPartnerActivity extends AppCompatActivity {
    EditText nombre;
    EditText direccion;
    EditText poblacion;
    EditText cif;
    EditText telefono;
    EditText email;
    CardView crear;
    CardView cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_partner);

        nombre = findViewById(R.id.edtNuevoPartnerNombre);
        direccion = findViewById(R.id.edtNuevoPartnerDireccion);
        poblacion = findViewById(R.id.edtNuevoPartnerPoblacion);
        cif = findViewById(R.id.edtNuevoPartnerCif);
        telefono = findViewById(R.id.edtNuevoPartnerTelefono);
        email = findViewById(R.id.edtNuevoPartnerEmail);

        crear = findViewById(R.id.btnNuevoPartnerCrear);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean p1 = false, p2 = false, p3 = false, p4 = false, p5 = false, p6 = false;
                if(nombre.getText().length() == 0){
                    nombre.setError("El campo debe completarse");
                }else{
                    p1 = true;
                }
                if(direccion.getText().length() == 0){
                    direccion.setError("El campo debe completarse");
                }else{
                    p2 = true;
                }
                if(poblacion.getText().length() == 0){
                    poblacion.setError("El campo debe completarse");
                }else{
                    p3 = true;
                }
                //TODO: Comprobar 8 numeros y una letra nada mas porque no me apetece estar generando mierdas
                if(cif.getText().length() == 0){
                    cif.setError("El campo debe completarse");
                }else{
                    p4 = true;
                }
                //TODO: Comprobar que son nueve numeros
                if(telefono.getText().length() == 0){
                    telefono.setError("El campo debe completarse");
                }else{
                    p5 = true;
                }
                if(email.getText().length() == 0){
                    email.setError("El campo debe completarse");
                }else{
                    p6 = true;
                }
                if (p1 && p2 && p3 && p4 && p5 && p6){
                    escribir();
                    finish();
                }
            }
        });
        cancelar = findViewById(R.id.btnNuevoPartnerCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void escribir(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getWritableDatabase();

        ContentValues nuevo = new ContentValues();
        //idPartner es AutoIncremental: NO PONER
        nuevo.put("idComercial", ((GlobalData)this.getApplicationContext()).getIdComercial());
        nuevo.put("nombre",nombre.getText().toString());
        nuevo.put("direccion",direccion.getText().toString());
        nuevo.put("poblacion",poblacion.getText().toString());
        nuevo.put("cif",cif.getText().toString());
        nuevo.put("telefono",telefono.getText().toString());
        nuevo.put("email",email.getText().toString());

        db.insert("Partners", null, nuevo);
    }
}