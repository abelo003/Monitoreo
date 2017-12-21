/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.business;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Component;

/**
 *
 * @author acruzb
 */
@Component
public class ThreatPoolPreference {
    
    private final ExecutorService executor;
    private static final int SIZE = 15;

    public ThreatPoolPreference() {
        executor = Executors.newFixedThreadPool(SIZE);
    }
    
    public void excecute(Runnable command){
        executor.execute(command);
    }
    
    public void shutdown(){
        executor.shutdown();
    }
            
}
