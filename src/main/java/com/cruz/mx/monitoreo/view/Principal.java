/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.view;

import com.cruz.mx.monitoreo.beans.ListServidorError;
import com.cruz.mx.monitoreo.beans.ListThreadsProcesos;
import com.cruz.mx.monitoreo.beans.Proceso;
import com.cruz.mx.monitoreo.beans.ServidorError;
import com.cruz.mx.monitoreo.business.AnalizadorMonitoreoBusiness;
import com.cruz.mx.monitoreo.business.FileSerializerComponent;
import com.cruz.mx.monitoreo.concurrent.PreferenceRunnable;
import com.cruz.mx.monitoreo.concurrent.ThreatChecarProceso;
import com.cruz.mx.monitoreo.enums.DIALOG_STATE;
import com.cruz.mx.monitoreo.enums.LOADING_MODE;
import com.cruz.mx.monitoreo.listener.PrincipalEventsAdapter;
import com.cruz.mx.monitoreo.models.AbstractModelProceso;
import com.cruz.mx.monitoreo.models.AbstractModelServidor;
import com.cruz.mx.monitoreo.models.AbstractModelSistema;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author acruzb
 */
public class Principal extends javax.swing.JFrame {

    private static final Logger LOGGER = Logger.getLogger(Principal.class);
    private final static String NEW_LINE = "\n";
    
    public static DialogLoading dialogLoading;
    public static DialogProceso dialogProceso;
    public static ApplicationContext applicationContext;
    public static String version;
    public static ImageIcon iconoSistema;

    private AbstractModelSistema modeloSistema;
    private AbstractModelServidor modeloServidor;
    private AbstractModelProceso modeloProceso;

    private final AnalizadorMonitoreoBusiness analizadorBusiness;
    private final FileSerializerComponent serializer;
//    private final ThreatPoolPreference threadPool;
    private final TrayIconBusiness trayIconBusiness;
    
    private ListThreadsProcesos listaHilosProcesos;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        init();
        analizadorBusiness = getObject(AnalizadorMonitoreoBusiness.class);
//        threadPool = getObject(ThreatPoolPreference.class);
        final PopupTrayIcon popup = new PopupTrayIcon();
        trayIconBusiness = getObject(TrayIconBusiness.class);
        trayIconBusiness.init(this, "Monitoreo Banca Digital", popup);
        popup.addListeners(trayIconBusiness);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);//SE QUITA
        addWindowsListeners();//trayIcon Listener for windows
        serializer = getObject(FileSerializerComponent.class);
        modeloProceso.addAllData(serializer.readData());
        listaHilosProcesos = new ListThreadsProcesos();
        //Se cargan los hilos de las preferencias
        for (Proceso proceso : modeloProceso.getData().getProcesos()) {
            if(proceso.isActive()){
                proceso.setRunning(true);
                addThreadProceso(proceso);
            }
        }
    }

    public void init() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setTitle("Monitoreo " + version);
        this.setIconImage(iconoSistema.getImage());

        dialogLoading = new DialogLoading(this);
        dialogLoading.setLocationRelativeTo(this);
        dialogLoading.getRootPane().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.addComponentListener(new PrincipalEventsAdapter(this, dialogLoading));

        dialogProceso = new DialogProceso(this, false);
        
        modeloSistema = new AbstractModelSistema();
        tablaGenerales.setModel(modeloSistema);
        tablaGenerales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        modeloServidor = new AbstractModelServidor();
        tablaServidores.setModel(modeloServidor);
        tablaServidores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        modeloProceso = new AbstractModelProceso();
        tablaProcesos.setModel(modeloProceso);
        tablaProcesos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaProcesos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                    Point p = e.getPoint();
                    JTable table = (JTable) e.getSource();
                    int row = table.rowAtPoint(p);
                    dialogProceso.setData(modeloProceso.getObjectAt(row));
                    dialogProceso.setMode(DIALOG_STATE.UPDATE);
                }
            }
        });
    }
    
    public TrayIconBusiness getTrayIconBusiness(){
        return trayIconBusiness;
    }

    public void addWindowsListeners() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                trayIconBusiness.addIcon();//se anade el icono a la barra de tareas
                trayIconBusiness.mostrarNotificacion("El analizador se va al background", TrayIcon.MessageType.INFO);
            }

            @Override
            public void windowActivated(java.awt.event.WindowEvent windowEvent) {
                trayIconBusiness.removeIcon();
            }
        });
    }

    public static void showLoading(LOADING_MODE mode) {
        if (mode.equals(LOADING_MODE.INDETERMINATE)) {
            dialogLoading.setIndeterminateMode();
        }
        if (mode.equals(LOADING_MODE.SCALE)) {
            dialogLoading.setScaleMode();
        }
        dialogLoading.setVisible(true);
    }

    public static void hideLoading() {
        dialogLoading.dispose();
        LOGGER.info("Se manda a ocultar el loading");
    }

    public void addThreadProceso(Proceso proceso){
        PreferenceRunnable hilo = new PreferenceRunnable(proceso);
        listaHilosProcesos.addHilo(hilo);
        hilo.start();
    }
    
    public void addProcesoTabla(Proceso proceso) {
        modeloProceso.addData(proceso);
        modeloProceso.sort();
        if(proceso.isActive()){
            proceso.setRunning(true);
            addThreadProceso(proceso);
        }
        updateOnlyTablaProceso();
        serializer.writeData(modeloProceso.getData());
    }
    
    public void updateProcesoTabla(Proceso proceso){
        checkThreadRunning();
        if(proceso.isActive()){
            proceso.setRunning(true);
            addThreadProceso(proceso);
        }
        updateOnlyTablaProceso();
        serializer.writeData(modeloProceso.getData());
    }
    
    public void deleteProcesoTabla(Proceso proceso){
        checkThreadRunning();
        modeloProceso.deteleData(proceso);
        updateOnlyTablaProceso();
        serializer.writeData(modeloProceso.getData());
    }
    
    public void updateOnlyTablaProceso(){
        modeloProceso.fireTableDataChanged();
        tablaProcesos.repaint();
    }
    
    public void checkThreadRunning(){
        listaHilosProcesos.checkRunningThreads();
    }
    
    public void mostrarAlerta(String mensaje, String titulo, int option){
        JOptionPane.showMessageDialog(this, mensaje, titulo, option);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        comboSistema = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnRefrescarServidor = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaServidores = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnRefrescarGeneral = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaGenerales = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        comboSistemaBusqueda = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        textFielTexto = new javax.swing.JTextField();
        btnBuscarError = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaErrores = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaProcesos = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        textPaneBusquedas = new javax.swing.JTextPane();
        jPanel8 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Analisis sistema", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        comboSistema.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Core Banca Digital", "JVC" }));

        jLabel1.setText("Sistema");

        btnRefrescarServidor.setText("Refrescar");
        btnRefrescarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarServidorActionPerformed(evt);
            }
        });

        tablaServidores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane2.setViewportView(tablaServidores);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(comboSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(btnRefrescarServidor)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefrescarServidor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Generales", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        btnRefrescarGeneral.setText("Refrescar");
        btnRefrescarGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarGeneralActionPerformed(evt);
            }
        });

        tablaGenerales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane1.setViewportView(tablaGenerales);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnRefrescarGeneral)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnRefrescarGeneral)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Generales", jPanel1);

        jLabel2.setText("Sistema");

        comboSistemaBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Core Banca Digital", "JVC" }));

        jLabel3.setText("Texto a buscar");

        textFielTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFielTextoActionPerformed(evt);
            }
        });

        btnBuscarError.setText("Buscar");
        btnBuscarError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarErrorActionPerformed(evt);
            }
        });

        textAreaErrores.setEditable(false);
        textAreaErrores.setColumns(20);
        textAreaErrores.setRows(5);
        jScrollPane3.setViewportView(textAreaErrores);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboSistemaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFielTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnBuscarError))
                        .addGap(0, 542, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboSistemaBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textFielTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Búsquedas", jPanel4);

        tablaProcesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tablaProcesos);

        btnAgregar.setText("Agregar preferencia");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnAgregar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Preferencias", jPanel7);

        jScrollPane5.setViewportView(textPaneBusquedas);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Búsquedas realizadas", jPanel5);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 349, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("BITSO", jPanel8);

        jTabbedPane1.setSelectedIndex(2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefrescarGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarGeneralActionPerformed
        showLoading(LOADING_MODE.INDETERMINATE);
        Thread miHilo = new Thread(new Runnable() {
            @Override
            public void run() {
                modeloSistema.addAllData(analizadorBusiness.getErroresGenerales());
                modeloSistema.sort();
                modeloSistema.fireTableDataChanged();
                tablaGenerales.repaint();
            }
        });
        miHilo.start();
        new ThreatChecarProceso(miHilo).start();
    }//GEN-LAST:event_btnRefrescarGeneralActionPerformed

    private void btnRefrescarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarServidorActionPerformed
        showLoading(LOADING_MODE.INDETERMINATE);
        Thread miHilo = new Thread(new Runnable() {
            @Override
            public void run() {
                modeloServidor.addAllData(analizadorBusiness.getErroresServidores(comboSistema.getSelectedItem().toString()));
                modeloServidor.sort();
                modeloServidor.fireTableDataChanged();
                tablaServidores.repaint();
            }
        });
        miHilo.start();
        new ThreatChecarProceso(miHilo).start();
    }//GEN-LAST:event_btnRefrescarServidorActionPerformed

    private void btnBuscarErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarErrorActionPerformed
        showLoading(LOADING_MODE.INDETERMINATE);
        final String texto = textFielTexto.getText();
        textAreaErrores.setText("");
        if (null != texto && !"".equals(texto)) {
            Thread miHilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, ListServidorError> mapa = analizadorBusiness.buscarErrores(texto.trim(), comboSistemaBusqueda.getSelectedItem().toString());
                    int count = 0;
                    for (Map.Entry<String, ListServidorError> entry : mapa.entrySet()) {
                        String servidor = entry.getKey();
                        ListServidorError lista = entry.getValue();
                        lista.sort();
                        textAreaErrores.append(NEW_LINE.concat(NEW_LINE).concat("SERVIDOR " + servidor).concat(NEW_LINE));
                        for (ServidorError error : lista.getListaErrores()) {
                            textAreaErrores.append(error.getError().concat(NEW_LINE));
                        }
                        count += lista.size();
                    }
                    textAreaErrores.append(NEW_LINE.concat("SE ENCONTRARON " + count + " ERRORES."));
                }
            });
            miHilo.start();
            new ThreatChecarProceso(miHilo).start();
        }
    }//GEN-LAST:event_btnBuscarErrorActionPerformed

    private void textFielTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFielTextoActionPerformed
        btnBuscarError.doClick();
    }//GEN-LAST:event_textFielTextoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        dialogProceso.setMode(DIALOG_STATE.NEW);
    }//GEN-LAST:event_btnAgregarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //look&feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            return;
        }
//        final Principal principal = new Principal();
        //TryIcon
        if (!SystemTray.isSupported()) {
            LOGGER.info("SystemTray is not supported");
            return;
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    public static <R extends Object> R getObject(Class<? extends Object> clazz) {
        return (R) applicationContext.getBean(clazz);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscarError;
    private javax.swing.JButton btnRefrescarGeneral;
    private javax.swing.JButton btnRefrescarServidor;
    private javax.swing.JComboBox<String> comboSistema;
    private javax.swing.JComboBox<String> comboSistemaBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablaGenerales;
    private javax.swing.JTable tablaProcesos;
    private javax.swing.JTable tablaServidores;
    private javax.swing.JTextArea textAreaErrores;
    private javax.swing.JTextField textFielTexto;
    private javax.swing.JTextPane textPaneBusquedas;
    // End of variables declaration//GEN-END:variables
}
