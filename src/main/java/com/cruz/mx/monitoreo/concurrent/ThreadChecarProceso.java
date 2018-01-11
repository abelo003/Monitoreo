/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cruz.mx.monitoreo.concurrent;

import com.cruz.mx.monitoreo.view.Principal;
import static com.cruz.mx.monitoreo.view.Principal.hideLoading;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Temporal
 */
public class ThreadChecarProceso extends Thread{
    
    private final Thread hiloSuperior;

    public ThreadChecarProceso(Thread hiloSuperior) {
        this.hiloSuperior = hiloSuperior;
    }
    
    @Override
    public void run(){
        while(hiloSuperior.isAlive()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hideLoading();
    }
            
}
