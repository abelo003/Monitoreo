/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.models;

import com.cruz.mx.monitoreo.beans.ListProceso;
import com.cruz.mx.monitoreo.beans.Proceso;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author acruzb
 */
public class AbstractModelProceso extends AbstractTableModel{
    
    private final String[] columnNames = { "Sistema", "Frecuencia", "Rango de tiempo", "Incidencias", "Alerta en", "Texto de búsqueda", "Mensaje", "En horario", "Ejecutandose"};

    private ListProceso procesos;

    public AbstractModelProceso() {}

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return procesos.getProcesos().size();
    }

    public Proceso getObjectAt(int row){
        return procesos.getProcesos().get(row);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        Proceso staff = procesos.getProcesos().get(row);
        switch (col){
            case 0:
                return staff.getSistema();
            case 1:
                return staff.getFrecuencia();
            case 2:
                return staff.getRangoTiempo();
            case 3:
                return staff.getNumIncidencias();
            case 4:
                return staff.getNumAlerta();
            case 5:
                return staff.getTextoBusqueda();
            case 6:
                return staff.getMensaje();
            case 7:
                return staff.isActive();
            case 8:
                return staff.isRunning();
        }
        return "";
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {}

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    public void addAllData(ListProceso data){
        procesos = data;
    }
    
    public void addData(Proceso data){
        procesos.getProcesos().add(data);
    }
    
    public ListProceso getData(){
        return procesos;
    }
    
    public void emptyData(){
        this.procesos = new ListProceso();
    }
    
    public void deteleData(Proceso proceso){
        procesos.removeProceso(proceso);
    }
    
    public void sort(){
        Collections.sort(procesos.getProcesos());
    }
}
