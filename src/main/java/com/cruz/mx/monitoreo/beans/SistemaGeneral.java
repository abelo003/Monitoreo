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
public class SistemaGeneral implements Comparable<SistemaGeneral>{
 
    private String nombre;
    private String totalTransacciones;
    private String totalTiemposAltos;
    private String errores;
    private String ultimoError;
    private String sigma;

    public SistemaGeneral() {
    }

    public SistemaGeneral(String nombre, String totalTransacciones, String totalTiemposAltos, String errores, String ultimoError, String sigma) {
        this.nombre = nombre;
        this.totalTransacciones = totalTransacciones;
        this.totalTiemposAltos = totalTiemposAltos;
        this.errores = errores;
        this.ultimoError = ultimoError;
        this.sigma = sigma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTotalTransacciones() {
        return totalTransacciones;
    }

    public void setTotalTransacciones(String totalTransacciones) {
        this.totalTransacciones = totalTransacciones;
    }

    public String getTotalTiemposAltos() {
        return totalTiemposAltos;
    }

    public void setTotalTiemposAltos(String totalTiemposAltos) {
        this.totalTiemposAltos = totalTiemposAltos;
    }

    public String getErrores() {
        return errores;
    }

    public void setErrores(String errores) {
        this.errores = errores;
    }

    public String getUltimoError() {
        return ultimoError;
    }

    public void setUltimoError(String ultimoError) {
        this.ultimoError = ultimoError;
    }

    public String getSigma() {
        return sigma;
    }

    public void setSigma(String sigma) {
        this.sigma = sigma;
    }

    @Override
    public int compareTo(SistemaGeneral o) {
        return this.nombre.compareTo(o.getNombre());
    }
    
}
