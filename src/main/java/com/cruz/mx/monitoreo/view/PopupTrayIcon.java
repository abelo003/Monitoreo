/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.view;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author acruzb
 */
public class PopupTrayIcon extends PopupMenu{
    
    private MenuItem itemMostrar;
    private MenuItem exitItem;

    public PopupTrayIcon() {
        init();
    }
    
    public void init(){
        itemMostrar = new MenuItem("Mostrar GUI");
        exitItem = new MenuItem("Salir");
        //Add components to popup menu
        add(itemMostrar);
        addSeparator();
        add(exitItem);
    }
    
    public void addListeners(final TrayIconBusiness trayIcon){
        itemMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trayIcon.getPrincipal().setVisible(true);
            }
        });
        
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trayIcon.removeIcon();
                System.exit(0);
            }
        });
    }
}
