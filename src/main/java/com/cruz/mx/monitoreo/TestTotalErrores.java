/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author acruzb
 */
public class TestTotalErrores {
    public static void main(String[] args) throws IOException {
        
        Map parametros = new HashMap();
//        parametros.put("sistema", "Crédito y Cobranza (BAZ Digital)");
        parametros.put("sistema", "Core Banca Digital");
        parametros.put("so", "UNIX");
        parametros.put("pais", "BAZDigital");
        Document doc = Jsoup.connect("http://10.63.50.73/Publico/totalerrores").data(parametros).userAgent("Mozilla/5.0").timeout(100*1000).post();
        Elements newsHeadlines = (Elements) doc.select("div.mws-panel-body tbody tr");
        int count = 0;
        for (Element newsHeadline : newsHeadlines) {
            count ++;
            String error = newsHeadline.child(3).text();
            if(error.contains("No se ha podido generar el HASH")){
                String servidor = newsHeadline.child(0).text();
                System.out.println(newsHeadline.child(3).text());
            }
//            System.out.println(error);
        }
        System.out.println(String.format("Hay %d de errores", count));
    }
}
