package com.alumno.reto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button bCorreo, bTelefono, bLocalizacion, bNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AMD
        bCorreo = (Button) findViewById(R.id.ButtonCorreo);
        bTelefono = (Button) findViewById(R.id.ButtonTelefono);
        bLocalizacion = (Button) findViewById(R.id.buttonUbicacion);
        bNext = (Button) findViewById(R.id.buttonNext);

        //Llamada telefono
        bTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero_telefono = bTelefono.getText().toString();
                String url = "tel:+34" + numero_telefono;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //Listener Gmail
        bCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_gmail = bCorreo.getText().toString();
                String url = "mailto:"+txt_gmail;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //Listener ubicacion

        bLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}