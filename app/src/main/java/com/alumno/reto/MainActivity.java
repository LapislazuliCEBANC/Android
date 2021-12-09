package com.alumno.reto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AMD
//        telefono.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String numero_telefono=telefono.getText().toString();
//                String url="tel:+34"+numero_telefono;
//
//                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            }
//        });
//        //hola asiddbaosnfd que tal eo eo eo
//        //Cambio 2
//        //Listener Gmail
//        gmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String txt_gmail=gmail.getText().toString();
//                Toast toast = Toast.makeText(getApplicationContext(), ""+txt_gmail, Toast.LENGTH_LONG);
//                toast.show();
//                String url="mailto:"+txt_gmail;
//                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            }
//        });
    }
}