/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.concurrent;

import com.cruz.mx.monitoreo.beans.ListServidorError;
import com.cruz.mx.monitoreo.beans.Proceso;
import com.cruz.mx.monitoreo.business.AnalizadorMonitoreoBusiness;
import com.cruz.mx.monitoreo.view.Principal;
import static com.cruz.mx.monitoreo.view.Principal.getObject;
import com.cruz.mx.monitoreo.view.TrayIconBusiness;
import java.awt.TrayIcon;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

/**
 *
 * @author acruzb
 */
public class PreferenceRunnable extends Thread {

    private static final Logger LOGGER = Logger.getLogger(PreferenceRunnable.class);
    private final Proceso proceso;
    private static final AtomicInteger FREC = new AtomicInteger(1000 * 60);
    private final AnalizadorMonitoreoBusiness analizador;

    public PreferenceRunnable(Proceso proceso) {
        this.proceso = proceso;
        analizador = Principal.getObject(AnalizadorMonitoreoBusiness.class);
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void interruptThread() {
        LOGGER.info("Se interrumpe el hilo: " + this.getId());
        this.interrupt();
    }

    @Override
    public void run() {
        while (true) {
            try {
                LOGGER.info("Buscando: " + proceso.getTextoBusqueda());
                Map<String, ListServidorError> result = analizador.buscarErrores(proceso.getTextoBusqueda(), proceso.getSistema());
                int count = 0;
                for (Map.Entry<String, ListServidorError> entry : result.entrySet()) {
                    ListServidorError lista = entry.getValue();
                    count += lista.size();
                }
                LOGGER.info("Se encontraton " + count + " errores con el texto: " + proceso.getTextoBusqueda());
                if (count >= proceso.getNumAlerta()) {
                    TrayIconBusiness trayIconBusiness = getObject(TrayIconBusiness.class);
                    trayIconBusiness.mostrarNotificacion(proceso.getMensaje() + "\n\nSE ENCONTRARON " + count + " ERRORES", TrayIcon.MessageType.ERROR, true);
                    proceso.setRunning(false);
                    trayIconBusiness.refreshTableProcesos();
                    break;//Se termina el proceso cuando se encuentra la incidencia
                }
                if (!proceso.isActive()) {
                    TrayIconBusiness trayIconBusiness = getObject(TrayIconBusiness.class);
                    proceso.setRunning(false);
                    trayIconBusiness.refreshTableProcesos();
                    break;
                }
                Thread.sleep(FREC.intValue() * proceso.getFrecuencia());
            } catch (InterruptedException ex) {
                proceso.setRunning(false);
                break;
            }
        }
        LOGGER.info("Se termina el HILO " + Thread.currentThread());
    }

}
