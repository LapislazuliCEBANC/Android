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

public class ArticuloXML {


    public static ArrayList<Articulo> lector(File ficheroXML){
        ArrayList<Articulo> lista = new ArrayList<>();
        Articulo articulo;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(ficheroXML);
            document.getDocumentElement().normalize();

            NodeList articulos = document.getElementsByTagName("Articulo");
            for (int i = 0; i < articulos.getLength(); i++) {
                articulo = new Articulo();
                Node arti = articulos.item(i);
                if (arti.getNodeType() == Node.ELEMENT_NODE){
                    Element elemento = (Element) arti;
                    articulo.setId(Integer.parseInt(elemento.getElementsByTagName("ARTICULOID").item(0).getTextContent()));
                    articulo.setDesc(elemento.getElementsByTagName("DESCRIPCION").item(0).getTextContent());
                    articulo.setPrCost(Integer.parseInt(elemento.getElementsByTagName("PR_COST").item(0).getTextContent()));
                    articulo.setPrVent(Integer.parseInt(elemento.getElementsByTagName("PR_VENT").item(0).getTextContent()));
                    articulo.setExistencias(Integer.parseInt(elemento.getElementsByTagName("EXISTENCIAS").item(0).getTextContent()));
                    articulo.setBajoMinimo(Integer.parseInt(elemento.getElementsByTagName("BAJO_MINIMO").item(0).getTextContent()));
                    articulo.setSobreMaximo(Integer.parseInt(elemento.getElementsByTagName("SOBRE_MAXIMO").item(0).getTextContent()));
                    articulo.setFecUltEnt(elemento.getElementsByTagName("FEC_ULT_ENT").item(0).getTextContent());
                    articulo.setFecUltSal(elemento.getElementsByTagName("FEC_ULT_SAL").item(0).getTextContent());

                    lista.add(articulo);
                }
            }
        }catch (Exception e){
            Log.e("info","Error al cargar el array: " + e);
        }

        return lista;
    }

}
