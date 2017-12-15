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
public class Servidor implements Comparable<Servidor>{
    
    private String ip;
    private String errores;
    private String tiemposAltos;

    public Servidor() {
    }

    public Servidor(String ip, String errores, String tiemposAltos) {
        this.ip = ip;
        this.errores = errores;
        this.tiemposAltos = tiemposAltos;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getErrores() {
        return errores;
    }

    public void setErrores(String errores) {
        this.errores = errores;
    }

    public String getTiemposAltos() {
        return tiemposAltos;
    }

    public void setTiemposAltos(String tiemposAltos) {
        this.tiemposAltos = tiemposAltos;
    }

    @Override
    public int compareTo(Servidor o) {
        return 1;
//        return this.ip.compareTo(o.getIp());
    }
        
}
