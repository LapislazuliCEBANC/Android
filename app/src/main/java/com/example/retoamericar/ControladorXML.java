package com.example.retoamericar;

import android.database.Cursor;
import android.util.Log;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ControladorXML {

    public ControladorXML() {
    }

    public ArrayList<String[]> lector (File ficheroXML, String etiqueta, String[] etiquetas){
        ArrayList<String[]> lista = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(ficheroXML);
            document.getDocumentElement().normalize();

            NodeList nodulos = document.getElementsByTagName(etiqueta);
            for (int i = 0; i < nodulos.getLength(); i++) {
                Node node = nodulos.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element elemento = (Element) node;
                    String[] objetos = new String[etiquetas.length];
                    for (int j = 0; j < etiquetas.length; j++) {
                        objetos[j] = elemento.getElementsByTagName(etiquetas[j]).item(0).getTextContent();
                    }
                    // objeto.setId(Integer.parseInt(elemento.getElementsByTagName("ARTICULOID").item(0).getTextContent()));
                    // objeto.setDesc(elemento.getElementsByTagName("DESCRIPCION").item(0).getTextContent());
                    // objeto.setPrCost(Integer.parseInt(elemento.getElementsByTagName("PR_COST").item(0).getTextContent()));
                    // objeto.setPrVent(Integer.parseInt(elemento.getElementsByTagName("PR_VENT").item(0).getTextContent()));
                    // objeto.setExistencias(Integer.parseInt(elemento.getElementsByTagName("EXISTENCIAS").item(0).getTextContent()));
                    // objeto.setBajoMinimo(Integer.parseInt(elemento.getElementsByTagName("BAJO_MINIMO").item(0).getTextContent()));
                    // objeto.setSobreMaximo(Integer.parseInt(elemento.getElementsByTagName("SOBRE_MAXIMO").item(0).getTextContent()));
                    // objeto.setFecUltEnt(elemento.getElementsByTagName("FEC_ULT_ENT").item(0).getTextContent());
                    // objeto.setFecUltSal(elemento.getElementsByTagName("FEC_ULT_SAL").item(0).getTextContent());
                    lista.add(objetos);
                }
            }
        }catch (Exception e){
            Log.e("info","Error al leer el XML: " + e);
        }

        return lista;
    }

    public boolean escritor(File ficheroInicio, File ficheroFinal, String contenedor, String etiqueta, String[] etiquetas, Cursor cursor){
        ArrayList<String[]> creados;
        ArrayList<String[]> nuevos = new ArrayList<>();


        cursor.moveToFirst();
        if (ficheroInicio != null){
            creados = lector(ficheroInicio, etiqueta, etiquetas);
            //Se encarga de que solo esten los nuevos
            do{

                boolean nuevo = true;
                //Por cada registro del cursor mirara todos lo del xml
                for (int i = 0; i < creados.size(); i++) {

                    //Si el ID coincide terminara de mirar y pasara al siguiente registro
                    if (creados.get(i)[0].equals(cursor.getString(0))){
                        i=creados.size();
                        nuevo = false;
                    }
                }
                //Si no coincide creamos un array de strings donde guardar toda la informacion de ese registro del cursor
                if (nuevo){
                    String[] registro = new String[etiquetas.length];
                    for (int j = 0; j < registro.length; j++) {
                        registro[j]=cursor.getString(j);
                    }
                    nuevos.add(registro);
                }
            }while (cursor.moveToNext());
        }else{
            //Para ahorrarnos las etiquetas le decimpos que las etiquetas son el nombre de las columnas
            etiquetas = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                etiquetas[i] = cursor.getColumnName(i);
            }
            // Ya que a la hora de escribir utilizamos el array "nuevos" rellenamos el array con el cursor
            do{
                String[] registro = new String[etiquetas.length];
                for (int i = 0; i < registro.length; i++) {
                    registro[i] = cursor.getString(i);
                }
                nuevos.add(registro);
            }while (cursor.moveToNext());

        }

        int a = 0;
        //Aqui empieza la escritura
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, contenedor, null);
            document.setXmlVersion("1.0");
            //Por cada nuevo
            for (int i = 0; i < nuevos.size(); i++) {
                a++;
                Element raiz = document.createElement(etiqueta);
                document.getDocumentElement().appendChild(raiz);
                //por cada atributo del nuevo
                for (int j = 0; j < etiquetas.length; j++) {
                    crearElemento(etiquetas[j], nuevos.get(i)[j], raiz, document);
                }
            }
            Source source = new DOMSource(document);
            Result result = new StreamResult(ficheroFinal);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            Log.e("comp","He pasado unas "+ a);
            return true;
        }catch (Exception e){
            return false;
        }

    }




    private static void crearElemento(String dato, String valor, Element raiz, Document document) {
        Element elem = document.createElement(dato);
        Text text = document.createTextNode(valor);
        raiz.appendChild(elem);
        elem.appendChild(text);
    }


}
