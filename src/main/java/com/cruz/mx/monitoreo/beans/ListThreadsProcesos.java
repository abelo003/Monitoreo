/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.beans;

import com.cruz.mx.monitoreo.concurrent.PreferenceRunnable;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author acruzb
 */
public class ListThreadsProcesos {
    
    private final ArrayList<PreferenceRunnable> listaThreads;

    public ListThreadsProcesos() {
        listaThreads = new ArrayList<>();
    }
    
    public void addHilo(PreferenceRunnable hilo){
        listaThreads.add(hilo);
    }

    public ArrayList<PreferenceRunnable> getListaThreads() {
        return listaThreads;
    }
    
    public synchronized void checkRunningThreads(){
        for (Iterator<PreferenceRunnable> iterator = listaThreads.iterator(); iterator.hasNext();) {
            PreferenceRunnable hilo = iterator.next();
            if(!hilo.getProceso().isRunning()){
                hilo.interruptThread();//Se despierta y termina el hilo
                iterator.remove();
            }
        }
    }
    
}
