/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.beans;

/**
 *
 * @author acruzb
 */
public class ServidorError implements Comparable<ServidorError>{
    
    private String ip;
    private String fecha;
    private String error;

    public ServidorError() {
    }

    public ServidorError(String ip, String fecha, String error) {
        this.ip = ip;
        this.fecha = fecha;
        this.error = error;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public int compareTo(ServidorError o) {
        return this.fecha.compareTo(o.getFecha());
    }
    
}
