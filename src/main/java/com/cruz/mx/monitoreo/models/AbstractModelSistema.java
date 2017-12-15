/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.models;

import com.cruz.mx.monitoreo.beans.SistemaGeneral;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author acruzb
 */
public class AbstractModelSistema extends AbstractTableModel{
    
    private final String[] columnNames = { "Sistema", "Total Transacciones", "Tiempos Altos", "Errores", "Sigma"};

    private ArrayList<SistemaGeneral> archivos;

    public AbstractModelSistema() {
        archivos = new ArrayList<>();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return archivos.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        SistemaGeneral staff = archivos.get(row);
        switch (col){
            case 0:
                return staff.getNombre();
            case 1:
                return staff.getTotalTransacciones();
            case 2:
                return staff.getTotalTiemposAltos();
            case 3:
                return staff.getErrores();
            case 4:
                return staff.getSigma();
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
    
    public void addAllData(ArrayList<SistemaGeneral> data){
        archivos = data;
    }
    
    public void addData(SistemaGeneral data){
        archivos.add(data);
    }
    
    public void emptyData(){
        this.archivos = new ArrayList<>();
    }
    
    public void sort(){
        Collections.sort(archivos);
    }
    
}
