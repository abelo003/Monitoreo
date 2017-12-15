/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author acruzb
 */
public class ListServidorError {

    private List<ServidorError> listaErrores;

    public ListServidorError() {
        listaErrores = new ArrayList<>();
    }

    public List<ServidorError> getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(List<ServidorError> listaErrores) {
        this.listaErrores = listaErrores;
    }
    
    public ListServidorError addServidorError(ServidorError error){
        listaErrores.add(error);
        return this;
    }
    
    public void sort(){
        Collections.sort(listaErrores);
    }
    
    public int size(){
        return listaErrores.size();
    }

}
