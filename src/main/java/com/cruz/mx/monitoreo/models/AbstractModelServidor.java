/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.models;

import com.cruz.mx.monitoreo.beans.Servidor;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author acruzb
 */
public class AbstractModelServidor extends AbstractTableModel{
    
    private final String[] columnNames = { "Servidor", "Total errores", "Total Tiempos Altos"};

    private ArrayList<Servidor> archivos;

    public AbstractModelServidor() {
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
        Servidor staff = archivos.get(row);
        switch (col){
            case 0:
                return staff.getIp();
            case 1:
                return staff.getErrores();
            case 2:
                return staff.getTiemposAltos();
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
    
    public void addAllData(ArrayList<Servidor> data){
        archivos = data;
    }
    
    public void addData(Servidor data){
        archivos.add(data);
    }
    
    public void emptyData(){
        this.archivos = new ArrayList<>();
    }
    
    public void sort(){
        Collections.sort(archivos);
    }
}
