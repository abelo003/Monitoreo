/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.listener;

import com.cruz.mx.monitoreo.view.DialogLoading;
import com.cruz.mx.monitoreo.view.Principal;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 *
 * @author acruzb
 */
public class PrincipalEventsAdapter implements ComponentListener{
    
    private Principal principal;
    private DialogLoading loading;

    public PrincipalEventsAdapter(Principal principal, DialogLoading loading) {
        this.principal = principal;
        this.loading = loading;
    }    
    

    @Override
    public void componentResized(ComponentEvent e) {
        loading.setLocationRelativeTo(principal);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        loading.setLocationRelativeTo(principal);
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }
    
}
