/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.concurrent;

import com.cruz.mx.monitoreo.business.BitsoBusiness;
import com.cruz.mx.monitoreo.enums.BITSO_CURRENCY;
import com.cruz.mx.monitoreo.view.Principal;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;

/**
 *
 * @author acruzb
 */
public class ThreadXRP implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ThreadXRP.class);
    private final Principal principal;
    private final BitsoBusiness bitsoBusiness;
    private int tiempo;

    public ThreadXRP(int tiempo, Principal principal) {
        bitsoBusiness = Principal.getObject(BitsoBusiness.class);
        this.tiempo = tiempo;
        this.principal = principal;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                JsonNode node = bitsoBusiness.consultarCambio(BITSO_CURRENCY.XRP_MXN);
                if(null != node){
                    node = node.get("payload");
                    principal.setXRPMXN(node.get("low").asDouble(), node.get("last").asDouble(), node.get("high").asDouble());
                }
                else{
                    LOGGER.info("No se pudo obtener la información de Bitso.");
                }
                Thread.sleep(tiempo * 1000);
            } catch (Exception ex) {
                LOGGER.info("Ocrurrió un error.", ex);
            }
        }
    }

}
