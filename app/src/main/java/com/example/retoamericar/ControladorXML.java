package com.example.retoamericar;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

            NodeList articulos = document.getElementsByTagName(etiqueta);
            for (int i = 0; i < articulos.getLength(); i++) {
                Node arti = articulos.item(i);
                if (arti.getNodeType() == Node.ELEMENT_NODE){
                    Element elemento = (Element) arti;
                    String[] objetos = new String[etiquetas.length];
                    for (int j = 0; j < etiquetas.length; j++) {
                        objetos[j] = elemento.getElementsByTagName(etiquetas[j]).item(0).getTextContent();
                    }
                    /*
                    objeto.setId(Integer.parseInt(elemento.getElementsByTagName("ARTICULOID").item(0).getTextContent()));
                    objeto.setDesc(elemento.getElementsByTagName("DESCRIPCION").item(0).getTextContent());
                    objeto.setPrCost(Integer.parseInt(elemento.getElementsByTagName("PR_COST").item(0).getTextContent()));
                    objeto.setPrVent(Integer.parseInt(elemento.getElementsByTagName("PR_VENT").item(0).getTextContent()));
                    objeto.setExistencias(Integer.parseInt(elemento.getElementsByTagName("EXISTENCIAS").item(0).getTextContent()));
                    objeto.setBajoMinimo(Integer.parseInt(elemento.getElementsByTagName("BAJO_MINIMO").item(0).getTextContent()));
                    objeto.setSobreMaximo(Integer.parseInt(elemento.getElementsByTagName("SOBRE_MAXIMO").item(0).getTextContent()));
                    objeto.setFecUltEnt(elemento.getElementsByTagName("FEC_ULT_ENT").item(0).getTextContent());
                    objeto.setFecUltSal(elemento.getElementsByTagName("FEC_ULT_SAL").item(0).getTextContent());
                    */
                    lista.add(objetos);
                }
            }
        }catch (Exception e){
            Log.e("info","Error al leer el XML: " + e);
        }

        return lista;
    }

}
