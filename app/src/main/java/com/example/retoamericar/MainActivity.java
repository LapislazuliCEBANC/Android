package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    CardView llamada, correo, localizacion, acercaDe;
    CardView agenda, partner, pedido, envio, importar, exportar;


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

        //Correo empresa
        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_gmail = "lapislazulireto@gmail.com";
                String url = "mailto:"+txt_gmail;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //Ubicacion
        localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        //Acerca de AmeriCar
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
        importar = (CardView) findViewById(R.id.bImportar);
        exportar = (CardView) findViewById(R.id.bExportar);
        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Hacer la agenda
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
                //TODO: a saber que vamos a hacer con esto
                //Intent intent = new Intent(MainActivity.this, Gmail_attachment.class);
                //startActivity(intent);
            }
        });

        importar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarArticulos();
                insertarPartners();

                Toast toast = Toast.makeText(getApplicationContext(),"Archivos cargados con exito", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportarPartners();
                exportarAlbaranes();
                exportarLineas();
            }
        });


    }

    public void insertarArticulos() {
        ArrayList<String[]> listaString;
        ArrayList<Articulo> lista = new ArrayList<>();
        ControladorXML lector = new ControladorXML();

        listaString = lector.lector(new File("/data/data/com.example.lapislazulireto/AlmacenDelegacion.xml"), "Articulo",
                new String[]{"ARTICULOID","DESCRIPCION","PR_COST","PR_VENT","EXISTENCIAS","BAJO_MINIMO","SOBRE_MAXIMO","FEC_ULT_ENT","FEC_ULT_SAL"});
        Articulo art;
        for (int i = 0; i < listaString.size(); i++) {
            art = new Articulo(listaString.get(i));
            lista.add(art);
        }
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getWritableDatabase();

        for (int i = 0; i < lista.size(); i++) {
            ContentValues nuevo = new ContentValues();
            nuevo.put("idArticulo", lista.get(i).getId());
            nuevo.put("descripcion", lista.get(i).getDesc());
            nuevo.put("prCost", lista.get(i).getPrCost());
            nuevo.put("prVent", lista.get(i).getPrVent());
            nuevo.put("existencias", lista.get(i).getExistencias());
            nuevo.put("bajoMinimo", lista.get(i).getBajoMinimo());
            nuevo.put("sobreMaximo", lista.get(i).getSobreMaximo());
            nuevo.put("fecUltEnt", lista.get(i).getFecUltEnt());
            nuevo.put("fecUltSal", lista.get(i).getFecUltSal());

            db.insert("Articulos", null, nuevo);

        }
        db.close();
    }

    public void insertarPartners(){
        ArrayList<String[]> listaString;
        ArrayList<Partner> lista = new ArrayList<>();
        ControladorXML lector = new ControladorXML();

        listaString = lector.lector(new File("/data/data/com.example.lapislazulireto/Partners.xml"),"Partner",
                new String[]{"PARTNERID","COMERCIALESID","NOMBRE","DIRECCION","POBLACION","CIF","TELEFONO","EMAIL"});
        Partner part;
        for (int i = 0; i < listaString.size(); i++) {
            part = new Partner(listaString.get(i));
            lista.add(part);
        }
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getWritableDatabase();

        for (int i = 0; i < lista.size(); i++) {
            ContentValues nuevo = new ContentValues();
            nuevo.put("idPartner", lista.get(i).getIdPartner());
            nuevo.put("idComercial", lista.get(i).getIdComercial());
            nuevo.put("nombre", lista.get(i).getNombre());
            nuevo.put("direccion", lista.get(i).getDireccion());
            nuevo.put("poblacion", lista.get(i).getPoblacion());
            nuevo.put("cif", lista.get(i).getCif());
            nuevo.put("telefono", lista.get(i).getTelefono());
            nuevo.put("email",lista.get(i).getEmail());

            db.insert("Partners",null,nuevo);

        }

        db.close();
    }
    
    public void exportarPartners(){
        ControladorXML controladorXML = new ControladorXML();
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from Partners",null);
        if(cursor.moveToFirst()){
            File ficheroGuardar = new File("/data/data/com.example.lapislazulireto/NuevosPartners.xml");
            try {
                ficheroGuardar.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            controladorXML.escritor(new File("/data/data/com.example.lapislazulireto/Partners.xml"),
                    ficheroGuardar,
                    "Partners","Partner",
                    new String[]{"PARTNERID","COMERCIALESID","NOMBRE","DIRECCION","POBLACION","CIF","TELEFONO","EMAIL"},
                    cursor);
            Toast.makeText(this,"Se a exportado con exito los Partners",Toast.LENGTH_SHORT).show();
        }
    }

    public void exportarAlbaranes(){
        ControladorXML controladorXML = new ControladorXML();
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select idAlbaran, idPartner, fechaAlbaran from Albaranes",null);
        if(cursor.moveToFirst()){
            File fic = new File("/data/data/com.example.lapislazulireto/NuevosAlbaranes.xml");
            try {
                fic.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            controladorXML.escritor(null, fic,
                    "Albaranes","Albaran",
                    null, cursor);
            Toast.makeText(this,"Se a exportado con exito los Albaranes",Toast.LENGTH_SHORT).show();
        }
    }

    public void exportarLineas(){
        ControladorXML controladorXML = new ControladorXML();
        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from Lineas",null);
        if(cursor.moveToFirst()){
            File fic = new File("/data/data/com.example.lapislazulireto/NuevasLineas.xml");
            try {
                fic.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            controladorXML.escritor(null, fic,
                    "Lineas","Linea",
                    null, cursor);
            Toast.makeText(this,"Se a exportado con exito las lineas",Toast.LENGTH_SHORT).show();
        }
    }
}