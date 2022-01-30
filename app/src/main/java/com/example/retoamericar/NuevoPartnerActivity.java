package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
                //Opción 1
                //Pro: Indica el campo erroneo de arriba abajo
                //Contra: Uno por uno
//                boolean camposValidos = false;
//
//                if(nombre.getText().length() == 0){
//                    nombre.setError("El campo debe completarse");
//                    nombre.requestFocus();
//                    camposValidos = false;
//                }else if(direccion.getText().length() == 0){
//                    direccion.setError("El campo debe completarse");
//                    direccion.requestFocus();
//                    camposValidos = false;
//                }else if(poblacion.getText().length() == 0){
//                    poblacion.setError("El campo debe completarse");
//                    poblacion.requestFocus();
//                    camposValidos = false;
//                }else if(cif.getText().length() == 0){ //Estoy utilizando el método sin letra
//                    cif.setError("El campo debe completarse");
//                    cif.requestFocus();
//                    camposValidos = false;
//                }else if(!validarDNIsinLetra(cif.getText().toString())){
//                    cif.setError("El CIF ha de ser valido");
//                    cif.requestFocus();
//                    camposValidos = false;
//                }else if(telefono.getText().length() == 0){
//                    telefono.setError("El campo debe completarse");
//                    telefono.requestFocus();
//                    camposValidos = false;
//                }else if(!validarTelefono(telefono.getText().toString())) {
//                    telefono.setError("El telefono ha de ser valido");
//                    telefono.requestFocus();
//                    camposValidos = false;
//                }else if(email.getText().length() == 0){
//                    email.setError("El campo debe completarse");
//                    email.requestFocus();
//                    camposValidos = false;
//                }else{
//                    camposValidos = true;
//                }
//
//                if (camposValidos){
//                    escribir();
//                    finish();
//                }

                //Opción 2
                //Pro: Indica todos los campos erroneos a la vez
                //Contra: Si hay correctos y erroneos indica el primero de abajo arriba
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

    //TODO: Injección De Veneno
    //Validaciones
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

    //Método completo con verificación de letra
    public static boolean validarDNI(String dni){
        boolean valido=false;
        String letraMayuscula = "";
        String dniNumeros;

        dniNumeros = dni.substring(0,8);
        letraMayuscula = (dni.substring(8).toUpperCase());

        if(dni.length() != 9 || !isNumeric(dniNumeros) || !Character.isLetter(dni.charAt(8)) || !letraDNI(dni).equals(letraMayuscula)) {
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

    public static String letraDNI(String dni){
        String letra = "";
        int miDNI = Integer.parseInt(dni.substring(0,8));
        int resto = 0;
        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        resto = miDNI % 23;

        letra = asignacionLetra[resto];

        return letra;
    }
}