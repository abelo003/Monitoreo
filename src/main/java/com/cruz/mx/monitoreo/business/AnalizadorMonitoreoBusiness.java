/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.business;

import com.cruz.mx.monitoreo.beans.ListServidorError;
import com.cruz.mx.monitoreo.beans.Servidor;
import com.cruz.mx.monitoreo.beans.ServidorError;
import com.cruz.mx.monitoreo.beans.SistemaGeneral;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author acruzb
 */
public class AnalizadorMonitoreoBusiness {
    
    private static final Logger logger = Logger.getLogger(AnalizadorMonitoreoBusiness.class);

    public static ArrayList<SistemaGeneral> getErroresGenerales() {
        Document doc;
        ArrayList<SistemaGeneral> lista = new ArrayList<>();
        try {
            doc = Jsoup.connect("http://10.63.50.73/Publico/sigmalogunixDigital").get();
            Elements elements = (Elements) doc.select("body .mws-table tbody tr");
            for (Element child : elements) {
                lista.add(new SistemaGeneral(child.child(0).text(), child.child(1).text(), child.child(2).text(), child.child(3).text(), child.child(4).text(), child.child(5).text()));
            }
        } catch (IOException ex) {
        }
        return lista;
    }

    public static ArrayList<Servidor> getErroresServidores(String sistema) {
        Document doc;
        ArrayList<Servidor> lista = new ArrayList<>();
        try {
            Map parametros = new HashMap();
            parametros.put("sistema", sistema);
            parametros.put("so", "UNIX");
            parametros.put("pais", "BAZDigital");
            doc = Jsoup.connect("http://10.63.50.73/Publico/totalerrores").data(parametros).userAgent("Mozilla/5.0").timeout(100 * 1000).post();
            Elements newsHeadlines = (Elements) doc.select("div.mws-panel-body tbody tr");
            Map servidores = new HashMap();
            String servidor = null;
            for (Element newsHeadline : newsHeadlines) {
                servidor = newsHeadline.child(0).text();
                servidores.put(servidor, (servidores.get(servidor)) == null ? 1 : (Integer.parseInt((servidores.get(servidor).toString())) + 1));
            }
            for (Iterator iterator = servidores.entrySet().iterator(); iterator.hasNext();) {
                Object next = iterator.next();
                Map.Entry entry = (Map.Entry) next;
                lista.add(new Servidor(entry.getKey().toString(), entry.getValue().toString(), "0"));
            }
        } catch (IOException ex) {}
        return lista;
    }
    
    public static Map<String, ListServidorError> buscarErrores(String textoError, String sistema){
        logger.info("Se inicia la busqueda de errores.");
        Document doc;
        Map<String, ListServidorError> lista = new HashMap<>();
        try {
            Map parametros = new HashMap();
            parametros.put("sistema", sistema);
            parametros.put("so", "UNIX");
            parametros.put("pais", "BAZDigital");
            doc = Jsoup.connect("http://10.63.50.73/Publico/totalerrores").data(parametros).userAgent("Mozilla/5.0").timeout(100 * 1000).post();
            Elements newsHeadlines = (Elements) doc.select("div.mws-panel-body tbody tr");
            int count =0;
            for (Element newsHeadline : newsHeadlines) {
                count++;
                String error = newsHeadline.child(3).text();
                if(error.contains(textoError)){
                    String servidor = newsHeadline.child(0).text();
                    String fecha = newsHeadline.child(2).text();
                    lista.put(servidor, ((lista.get(servidor) != null)? (lista.get(servidor).addServidorError(new ServidorError(servidor, fecha, error))): new ListServidorError()));
                }
            }
            logger.info("Se encontrato un total de: " + count + " errores.");
            logger.info("Se encontrato un total de: " + count + " errores.", new Exception("jajaja"));
            
        } catch (IOException ex) {
            logger.info("", ex);
        }
        logger.info("Se encontraron: " + lista.size() + " incidencias con la palabra: " + textoError);
        return lista;
    }

}
