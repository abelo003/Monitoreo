/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.view;

import java.awt.AWTException;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author acruzb
 */
public class TrayIconBusiness {

    private Principal principal;
    private static TrayIcon trayIcon;

    public TrayIconBusiness(String tooltip, PopupMenu popup) {
        trayIcon = new TrayIcon(Principal.iconoSistema.getImage(), tooltip, popup);
        trayIcon.setImageAutoSize(true);
    }

    public void init(final Principal principal) {
        this.principal = principal;
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principal.setVisible(true);
                removeIcon();
            }
        });
    }

    public void addIcon() {
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException es) {}
    }

    public void removeIcon() {
        SystemTray.getSystemTray().remove(trayIcon);
    }

    public Principal getPrincipal(){
        return principal;
    }
    
    public static void mostrarNotificacion(String mensaje, TrayIcon.MessageType type){
        trayIcon.displayMessage("Banca Digital - Errores", mensaje, type);
    }
    
}
