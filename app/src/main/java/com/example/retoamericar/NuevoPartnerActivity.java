package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.Format;

public class NuevoPartnerActivity extends AppCompatActivity {
    EditText apellido1;
    EditText apellido2;
    EditText nombre;
    EditText direccion;
    EditText poblacion;
    EditText provincia;
    EditText formPago;
    EditText telefono;
    Button crear;
    Button cancelar;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_partner);

        apellido1 = findViewById(R.id.edtPartnerApellido1);
        apellido2 = findViewById(R.id.edtPartnerApellido2);
        nombre = findViewById(R.id.edtPartnerNombre);
        direccion = findViewById(R.id.edtPartnerDireccion);
        poblacion = findViewById(R.id.edtPartnerPoblacion);
        provincia = findViewById(R.id.edtPartnerProvincia);
        formPago = findViewById(R.id.edtPartnerFrompago);
        telefono = findViewById(R.id.edtPartnerTelefono);

        crear = findViewById(R.id.btnPartnersCrear);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean p1 = false, p2 = false, p3 = false, p4 = false, p5 = false, p6 = false, p7 = false, p8 = false;
                if(apellido1.getText().length() == 0){
                    apellido1.setError("El campo debe completarse");
                }else{
                    p1 = true;
                }
                if(apellido2.getText().length() == 0){
                    apellido2.setError("El campo debe completarse");
                }else{
                    p2 = true;
                }
                if(nombre.getText().length() == 0){
                    nombre.setError("El campo debe completarse");
                }else{
                    p3 = true;
                }
                if(direccion.getText().length() == 0){
                    direccion.setError("El campo debe completarse");
                }else{
                    p4 = true;
                }
                if(poblacion.getText().length() == 0){
                    poblacion.setError("El campo debe completarse");
                }else{
                    p5 = true;
                }
                if(provincia.getText().length() == 0){
                    provincia.setError("El campo debe completarse");
                }else{
                    p6 = true;
                }
                if(formPago.getText().length() == 0){
                    formPago.setError("El campo debe completarse");
                }else{
                    p7 = true;
                }
                if(telefono.getText().length() == 0){
                    telefono.setError("El campo debe completarse");
                }else{
                    p8 = true;
                }
                if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8){
                    intent.putExtra("Apellido1", apellido1.getText().toString());
                    intent.putExtra("Apellido2", apellido2.getText().toString());
                    intent.putExtra("Nombre", nombre.getText().toString());
                    intent.putExtra("Direccion", direccion.getText().toString());
                    intent.putExtra("Poblacion", poblacion.getText().toString());
                    intent.putExtra("Provincia", provincia.getText().toString());
                    intent.putExtra("FormPago", formPago.getText().toString());
                    intent.putExtra("Telefono", telefono.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        cancelar = findViewById(R.id.btnPartnerCancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}