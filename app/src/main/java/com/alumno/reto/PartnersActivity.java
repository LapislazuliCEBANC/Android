package com.alumno.reto;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PartnersActivity extends AppCompatActivity {
    ListView lst;
    Button crear;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ArrayList<Partner> datos = new ArrayList<>();
    File directorio = new File("/data/data/com.alumno.reto/files");
    File ficheroXML = new File(directorio,"partners.xml");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        //Cunando se reciva la informacion de la otra actividad se añadira al arraylist
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Partner partner = new Partner();
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Intent data = result.getData();
                            //TODO: Añadir los codigos de vuelta
                            datos.add(partner);
                        }
                    }
                }
        );

        if(!directorio.exists()){
            directorio.mkdir();
        }
        if(ficheroXML.exists()){
            //TODO: cargar();
        }else{
            try {
                ficheroXML.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        crear = findViewById(R.id.btnCrear);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartnersActivity.this, NuevoPartnerActivity.class);
                activityResultLauncher.launch(i);
            }
        });
    }

    protected void cargar(){
        Partner partner;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(ficheroXML);
            document.getDocumentElement().normalize();

            NodeList personas = document.getElementsByTagName("Persona");
            for (int i = 0; i < personas.getLength(); i++) {
                partner = new Partner();
                Node pers = personas.item(i);
                if (pers.getNodeType() == Node.ELEMENT_NODE){
                    Element elemento = (Element) pers;
                    //TODO: crear el partner
                    datos.add(partner);
                }
            }
        }catch (Exception e){
            Log.e("info","Error al cargar el array: " + e);
        }
        MiAdapter adatador = new MiAdapter(this);
        lst = findViewById(R.id.lsvPartners);
        lst.setAdapter(adatador);
    }

    class MiAdapter extends ArrayAdapter {
        Activity context;

        public MiAdapter(Activity context){
            super(context,R.layout.partner, datos);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = context.getLayoutInflater();
            View item =inflater.inflate(R.layout.partner, null);

            TextView nombre = item.findViewById(R.id.txvPartnerNombre);
            nombre.setText(datos.get(position).getNombre());

            TextView apellido1 = item.findViewById(R.id.txvPartnerApellido1);
            apellido1.setText(datos.get(position).getAppellido1());

            TextView apellido2 = item.findViewById(R.id.txvPartnerApellido2);
            apellido2.setText(datos.get(position).getApellido2());

            TextView direccion = item.findViewById(R.id.txvPartnerDireccion);
            direccion.setText(datos.get(position).getDireccion());

            TextView poblacion = item.findViewById(R.id.txvPartnerPoblacion);
            poblacion.setText(datos.get(position).getPoblacion());

            TextView telefono = item.findViewById(R.id.txvPartnerTelefono);
            telefono.setText(datos.get(position).getTelefono());

            return(item);
        }
    }

    protected void escribir(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "Personas", null);
            document.setXmlVersion("1.0");
            Partner partner;
            for (int i = 0; i < datos.size(); i++) {
                partner = datos.get(i);
                Element raiz = document.createElement("Persona");
                document.getDocumentElement().appendChild(raiz);
                //TODO: Cosas con lo de crear elemento
            }
            Source source = new DOMSource(document);
            Result result = new StreamResult();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        }catch (Exception e){

        }
    }

    static void crearElemento(String dato, String valor, Element raiz, Document document) {
        Element elem = document.createElement(dato);
        Text text = document.createTextNode(valor);
        raiz.appendChild(elem);
        elem.appendChild(text);
    }
}