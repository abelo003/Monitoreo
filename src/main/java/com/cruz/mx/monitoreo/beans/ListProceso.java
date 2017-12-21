/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author acruzb
 */
public class ListProceso implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private ArrayList<Proceso> procesos;

    public ListProceso() {
        procesos = new ArrayList<>();
    }

    public ListProceso(ArrayList<Proceso> procesos) {
        this.procesos = procesos;
    }

    public ArrayList<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(ArrayList<Proceso> procesos) {
        this.procesos = procesos;
    }
    
    public void removeProceso(Proceso proceso){
        this.procesos.remove(proceso);
    }
    
    public void addProceso(Proceso proceso){
        procesos.add(proceso);
    }
    
    public void sort(){
        Collections.sort(procesos);
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
