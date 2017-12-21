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
import javax.swing.JOptionPane;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author acruzb
 */
@Component
@Scope("singleton")
public class TrayIconBusiness {
    
    private static final String TITLE = "Banca Digital - Errores";

    private Principal principal;
    private TrayIcon trayIcon;

    public TrayIconBusiness() {}

    public void init(final Principal principal, String tooltip, PopupMenu popup) {
        trayIcon = new TrayIcon(Principal.iconoSistema.getImage(), tooltip, popup);
        trayIcon.setImageAutoSize(true);
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
    
    public void mostrarNotificacion(String mensaje, TrayIcon.MessageType type, boolean ... showGUI){
        TrayIcon[] lista = SystemTray.getSystemTray().getTrayIcons();
        boolean exist = false;
        for (TrayIcon tray : lista) {
            if(tray.equals(trayIcon)){
                exist = true;
                break;
            }
        }
        if(exist){
            trayIcon.displayMessage(TITLE, mensaje, type);
            if(showGUI.length > 0 && showGUI[0]){
                principal.setVisible(exist);
                principal.mostrarAlerta(mensaje, TITLE, JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            principal.mostrarAlerta(mensaje, TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
