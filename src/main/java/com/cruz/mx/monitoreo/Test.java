/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 *
 * @author acruzb
 */
public class Test {
    private static String BAZ_DIGIAL = "Core Banca Digital";
    private static String JVC = "JVC";

    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("http://10.63.50.73/Publico/sigmalogunixDigital").get();
//        System.out.println(doc.title());
        Elements newsHeadlines = (Elements) doc.select("body .mws-table tbody tr");
        Element elementBAZ = null;
        Element elementJVC = null;
        for (Element headline : newsHeadlines) {
            System.out.println(headline.child(0).text());
        }
//        for (Element headline : newsHeadlines) {
//            if(headline.toString().contains(BAZ_DIGIAL)){
//                elementBAZ = headline;
//            }
//            else if(headline.toString().contains(JVC)){
//                elementJVC = headline;
//            }
//        }
//        System.out.println("GOOOO\n\n");
//        if(elementBAZ != null && elementJVC != null){
//            String totalTR = elementBAZ.getElementsByIndexEquals(1).text();
//            String totalTA = elementBAZ.getElementsByIndexEquals(1).text();
//            String totalER = elementBAZ.getElementsByIndexEquals(1).text();
//            String totalUE = elementBAZ.getElementsByIndexEquals(1).text();
//            String totalSI = elementBAZ.getElementsByIndexEquals(1).text();
//            System.out.println(totalTR);
//        }
    }
}
