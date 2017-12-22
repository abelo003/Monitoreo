/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.beans;

import com.cruz.mx.monitoreo.view.DialogProceso;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author acruzb
 */
public class Proceso implements Comparable<Proceso>, Serializable{
    
    private SimpleDateFormat SDF;
    private static final long serialVersionUID = 1L;
    
    private String sistema;
    private int frecuencia;
    private String rangoTiempo;
    private int numAlerta;
    private String textoBusqueda;
    private String mensaje;
    private transient boolean running;

    public Proceso() {
        init();
    }

    public void setRunning(boolean running){
        this.running = running;
    }
    
    public boolean isRunning(){
        return running;
    }
    
    public Proceso(String sistema, int frecuencia, String rangoTiempo, int numAlerta, String textoBusqueda, String mensaje) {
        this();
        this.sistema = sistema;
        this.frecuencia = frecuencia;
        this.rangoTiempo = rangoTiempo;
        this.numAlerta = numAlerta;
        this.textoBusqueda = textoBusqueda;
        this.mensaje = mensaje;
    }

    private void init(){
        if(SDF == null){
            SDF = new SimpleDateFormat("HH:mm");
        }
    }
    
    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public int getNumAlerta() {
        return numAlerta;
    }

    public void setNumAlerta(int numAlerta) {
        this.numAlerta = numAlerta;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getRangoTiempo() {
        return rangoTiempo;
    }

    public void setRangoTiempo(String rangoTiempo) {
        this.rangoTiempo = rangoTiempo;
    }
    
    public synchronized boolean isActive(){
        String rang[] = rangoTiempo.split(DialogProceso.RANGE_SEPARATOR);
        try {
            init();
            String fecha = SDF.format(new Date());
            Date horaDesde = SDF.parse(rang[0]);
            Date horaHasta = SDF.parse(rang[1]);
            Date horaActual = SDF.parse(fecha);
            return horaActual.after(horaDesde) && horaActual.before(horaHasta);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public int compareTo(Proceso o) {
        return this.getSistema().compareTo(o.getSistema());
    }
    
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException jpe) {}
        return "";
    }
    
}
