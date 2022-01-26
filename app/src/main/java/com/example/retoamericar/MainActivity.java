package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    CardView llamada, correo, localizacion, acercaDe;
    CardView agenda, partner, pedido, envio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llamada = (CardView) findViewById(R.id.bLlamada);
        correo = (CardView) findViewById(R.id.bGmail);
        localizacion = (CardView) findViewById(R.id.bUbicacion);
        acercaDe = (CardView) findViewById(R.id.bAcercaDe);

        //Llamada telefono
        llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero_telefono = "634428466";
                String url = "tel:+34" + numero_telefono;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //Listener Gmail
        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_gmail = "lapislazulireto@gmail.com";
                String url = "mailto:"+txt_gmail;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //Listener ubicacion

        localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        //Listener acerca de

        acercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoPersonalizado  dialogo = new DialogoPersonalizado ();
                dialogo.show(fragmentManager, "tagAlerta");
            }
        });
        agenda = (CardView) findViewById(R.id.bAgenda);
        partner = (CardView) findViewById(R.id.bPartners);
        pedido = (CardView) findViewById(R.id.bPedidos);
        envio = (CardView) findViewById(R.id.bMandar);


        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PartnersActivity.class);
                startActivity(intent);
            }
        });

        pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PedidosActivity.class);
                startActivity(intent);
            }
        });

        envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, Gmail_attachment.class);
                //startActivity(intent);
            }
        });
    }
}