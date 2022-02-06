package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class ModificarPartner extends AppCompatActivity {
    String id;

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
        setContentView(R.layout.activity_modificar_partner);

        Intent data = getIntent();
        id = data.getStringExtra("id");

        nombre = findViewById(R.id.edtModificarPartnerNombre);
        direccion = findViewById(R.id.edtModificarPartnerDireccion);
        poblacion = findViewById(R.id.edtModificarPartnerPoblacion);
        cif = findViewById(R.id.edtModificarPartnerCIF);
        telefono = findViewById(R.id.edtModificarPartnerTelefono);
        email = findViewById(R.id.edtModificarPartnerEmail);

        cargar();

        crear = findViewById(R.id.btnModificarPartnerModificar);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean v1=false, v2=false, v3=false, v4=false, v5=false, v6=false;
                if(nombre.getText().length() == 0){
                    nombre.setError("El campo debe completarse");
                    nombre.requestFocus();
                }else{
                    v1 = true;
                }

                if(direccion.getText().length() == 0){
                    direccion.setError("El campo debe completarse");
                    direccion.requestFocus();
                }else{
                    v2 = true;
                }

                if(poblacion.getText().length() == 0){
                    poblacion.setError("El campo debe completarse");
                    poblacion.requestFocus();
                }else{
                    v3 = true;
                }

                //Estoy utilizando el método sin letra
                if(cif.getText().length() == 0){
                    cif.setError("El campo debe completarse");
                    cif.requestFocus();
                }else if(!validarDNIsinLetra(cif.getText().toString())){
                    cif.setError("El CIF ha de ser valido");
                    cif.requestFocus();
                }else{
                    v4 = true;
                }

                if(telefono.getText().length() == 0){
                    telefono.setError("El campo debe completarse");
                    telefono.requestFocus();
                }else if(!validarTelefono(telefono.getText().toString())) {
                    telefono.setError("El telefono ha de ser valido");
                    telefono.requestFocus();
                }else{
                    v5 = true;
                }

                if(email.getText().length() == 0){
                    email.setError("El campo debe completarse");
                    email.requestFocus();
                }else{
                    v6 = true;
                }

                if (v1 && v2 && v3 && v4 && v5 && v6){
                    modificar();
                    finish();
                }
            }
        });

        cancelar = findViewById(R.id.btnModificarPartnerCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        String[] args = {id};

        Cursor c = db.rawQuery("SELECT nombre, direccion, poblacion, cif, telefono, email " +
                                   "FROM Partners " +
                                   "WHERE idPartner=?",args);
        if (c.moveToFirst()){
            nombre.setText(c.getString(0));
            direccion.setText(c.getString(1));
            poblacion.setText(c.getString(2));
            cif.setText(c.getString(3));
            telefono.setText(c.getString(4));
            email.setText(c.getString(5));
        }
        db.close();
    }

    private void modificar(){
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        String[] args = new String[]{id};

        ContentValues valores = new ContentValues();
        valores.put("nombre",nombre.getText().toString());
        valores.put("direccion",direccion.getText().toString());
        valores.put("poblacion",poblacion.getText().toString());
        valores.put("cif",cif.getText().toString());
        valores.put("telefono",telefono.getText().toString());
        valores.put("email",email.getText().toString());
        db.update("Partners",valores,"idPartner=?", args);
        db.close();
        finish();
    }


    public boolean validarDNIsinLetra(String dni){
        boolean valido=false;
        String dniNumeros;

        dniNumeros = dni.substring(0,8);

        if(dni.length() != 9 || !isNumeric(dniNumeros) || !Character.isLetter(dni.charAt(8))) {
            valido = false;
        }else{
            valido = true;
        }

        return valido;
    }

    public static boolean validarTelefono(String telefono){
        boolean valido=false;

        if(telefono.length() != 9 || !isNumeric(telefono)) {
            valido = false;
        }else{
            valido = true;
        }

        return valido;
    }

    private static boolean isNumeric(String cadena){
        boolean valido = false;

        try {
            Integer.parseInt(cadena);
            valido = true;
        } catch (NumberFormatException nfe){
            valido = false;
        }

        return valido;
    }
}