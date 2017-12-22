/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.business;

import com.cruz.mx.monitoreo.PreferenceConfig;
import com.cruz.mx.monitoreo.beans.ListProceso;
import com.cruz.mx.monitoreo.beans.Proceso;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.springframework.stereotype.Component;

/**
 *
 * @author acruzb
 */
@Component
public class FileSerializerComponent {

    /**
     * Se agrega un objecto al archivo.
     *
     * @param proceso
     * @return 
     */
    public ListProceso addProceso(Proceso proceso) {
        ListProceso procesos = readData();
        procesos.addProceso(proceso);
        procesos.sort();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PreferenceConfig.RUTA_ARCHIVO))) {
            oos.writeObject(procesos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return procesos;
    }
    
    public ListProceso removeProceso(Proceso proceso){
        ListProceso procesos = readData();
        procesos.removeProceso(proceso);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PreferenceConfig.RUTA_ARCHIVO))) {
            oos.writeObject(procesos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return procesos;
    }
    
    public void writeData(ListProceso lista){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PreferenceConfig.RUTA_ARCHIVO))) {
            oos.writeObject(lista);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ListProceso readData() {
        ListProceso procesos = new ListProceso();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PreferenceConfig.RUTA_ARCHIVO))) {
            procesos = (ListProceso) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return procesos;
    }

}
