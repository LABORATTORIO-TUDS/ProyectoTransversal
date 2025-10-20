/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vista;

import Modelo.*;
import Persistencia.*;
import java.util.*;
import javax.swing.*;


/**
 *
 * @author andre
 */
public class vistaInscripcion extends javax.swing.JInternalFrame {

    private AlumnoData aD;
    private InscripcionData iD;
    private materiaData mD;
    
    public vistaInscripcion(){
        initComponents();
        aD = new AlumnoData();
        iD = new inscripcionData();
        mD = new materiaData();
        
        ButtonGroup grupoFiltros = new ButtonGroup();
        grupoFiltros.add(jRBInscriptas);
        grupoFiltros.add(jRBNoInscriptas);
        grupoFiltros.add(jRBTodas);
        //grupoFiltros.clearSelection();
        
        cargarAlumnos();   
        configurarEventos();
    }
    
   //Metodo para meterle los alumnos existentes en la base de datasos en el JCombo_Alumno 
    private void cargarAlumnos(){
        List<Alumno> alumnos = aD.getAllActivos();
        DefaultComboBoxModel<Alumno> modeloAlumnos = new DefaultComboBoxModel<>();
        
        for(Alumno a : alumnos){
            modeloAlumnos.addElement(a);
        }
        
        jCombo_Alumno.setModel(modeloAlumnos);
        if (!alumnos.isEmpty()){
            jCombo_Alumno.setSelectedIndex(0);
            actualizarMaterias();                
        }       
    }
    //Metodo para que se actualizen las materias en el Jcombo_Materias segun el filtro, pero tambien
    //Segun si el alumno esta inscripto en ellas o no
    private void actualizarMaterias(){
         Alumno alumno = (Alumno) jCombo_Alumno.getSelectedItem();
         if(alumno == null) return;
         
         List<Materia> materias;
        
         if(jRBInscriptas.isSelected()){          
            materias = iD.obtenerMateriasInscriptas(alumno.getLegajo());
         }else if(jRBNoInscriptas.isSelected()){
            materias = iD.obtenerMateriasNoInscriptas(alumno.getLegajo());
         }else {
            materias = mD.getAllActivos();
         }
        
            DefaultComboBoxModel<Materia> modeloMaterias = new DefaultComboBoxModel<>();
            for(Materia m : materias){
                modeloMaterias.addElement(m);
         }
            
         jCombo_Materias.setModel(modeloMaterias);
    }
    //Inscribir segun el alumno y materia elegida
    private void inscribirAlumno(){
        Alumno alumno = (Alumno) jCombo_Alumno.getSelectedItem();
        Materia materia = (Materia) jCombo_Materias.getSelectedItem();
        if(alumno == null || materia == null){
            JOptionPane.showMessageDialog(this, "Debe seleccionar un alumno y una materia para inscribirse");
            return;
        }
        
        Inscripcion ins = new Inscripcion(0, 0, alumno, materia);
        iD.inscribirAlumno(ins);
        actualizarMaterias();
    }
    
    //El metodo para anular la inscripcion segun la materia y el alumno elegido
    private void anularInscripcion(){
        Alumno alumno = (Alumno) jCombo_Alumno.getSelectedItem();
        Materia materia = (Materia) jCombo_Materias.getSelectedItem();
        if(alumno == null || materia == null ){
            JOptionPane.showMessageDialog(this, "Debe seleccionar una materia y un alimno validos para anular la inscripcion");
            return;
        }
        
        iD.anularInscripcion(alumno.getLegajo(), materia.getIdMateria());
        actualizarMaterias();        
    }
    //Buena wachin recatate (Le asignamos los eventos a cada boton y quese yo)
    private void configurarEventos(){
        jCombo_Alumno.addActionListener(e -> actualizarMaterias() );
        jRBInscriptas.addActionListener(e -> actualizarMaterias());
        jRBNoInscriptas.addActionListener(e -> actualizarMaterias());
        jRBTodas.addActionListener(e -> actualizarMaterias());
        
        jBInscribirse.addActionListener(e -> inscribirAlumno());
        jBAnularInscripcion.addActionListener(e -> anularInscripcion());       
    }
    
    
    
    
   /* 
    private alumnoData aluData;
    private inscripcionData insData;
    private materiaData matData;

    public vistaInscripcion() {
        initComponents();
        aluData = new alumnoData();
        insData = new inscripcionData();
        matData = new materiaData();

        cargarAlumnos();
        configurarEventos();
    }

    // CARGA INICIAL DE ALUMNOS AL COMBOBOX
    private void cargarAlumnos() {
        List<Alumno> alumnos = aluData.getAllActivos();
        DefaultComboBoxModel<Alumno> modeloAlumnos = new DefaultComboBoxModel<>();

        for (Alumno a : alumnos) {
            modeloAlumnos.addElement(a);
        }

        jCombo_Alumno.setModel(modeloAlumnos);
        if (!alumnos.isEmpty()) {
            jCombo_Alumno.setSelectedIndex(0);
            actualizarMaterias();
        }
    }

    // ASIGNAR EVENTOS A RADIOBUTTONS Y COMBOBOX
    private void configurarEventos() {
        jCombo_Alumno.addActionListener(e -> actualizarMaterias());
        jRBInscriptas.addActionListener(e -> actualizarMaterias());
        jRBNoInscriptas.addActionListener(e -> actualizarMaterias());

        jBInscribirse.addActionListener(e -> inscribirAlumno());
        jBAnularInscripcion.addActionListener(e -> anularInscripcion());
        jBSalir.addActionListener(e -> dispose());
    }

    // ACTUALIZA LAS MATERIAS SEGÚN EL FILTRO
    private void actualizarMaterias() {
        Alumno alumno = (Alumno) jCombo_Alumno.getSelectedItem();
        if (alumno == null) return;

        List<Materia> materias;

        if (jRBInscriptas.isSelected()) {
            materias = insData.obtenerMateriasInscriptas(alumno.getLegajo());
        } else if (jRBNoInscriptas.isSelected()) {
            materias = insData.obtenerMateriasNoInscriptas(alumno.getLegajo());
        } else {
            materias = matData.getAllActivos();
        }

        DefaultComboBoxModel<Materia> modeloMaterias = new DefaultComboBoxModel<>();
        for (Materia m : materias) {
            modeloMaterias.addElement(m);
        }

        jCombo_Materias.setModel(modeloMaterias);
    }

    // ACCIÓN DE BOTÓN INSCRIBIR
    private void inscribirAlumno() {
        Alumno alumno = (Alumno) jCombo_Alumno.getSelectedItem();
        Materia materia = (Materia) jCombo_Materias.getSelectedItem();

        if (alumno == null || materia == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un alumno y una materia.");
            return;
        }

        Inscripcion ins = new Inscripcion(0, 0, alumno, materia);
        insData.inscribirAlumno(ins);
        actualizarMaterias();
    }

    // ACCIÓN DE BOTÓN ANULAR INSCRIPCIÓN
    private void anularInscripcion() {
        Alumno alumno = (Alumno) jCombo_Alumno.getSelectedItem();
        Materia materia = (Materia) jCombo_Materias.getSelectedItem();

        if (alumno == null || materia == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un alumno y una materia para anular.");
            return;
        }

        insData.anularInscripcion(alumno.getLegajo(), materia.getIdMateria());
        actualizarMaterias();
    }
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jCombo_Materias = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jRBInscriptas = new javax.swing.JRadioButton();
        jRBNoInscriptas = new javax.swing.JRadioButton();
        jRBTodas = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jBInscribirse = new javax.swing.JButton();
        jBAnularInscripcion = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jCombo_Alumno = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 24)); // NOI18N
        jLabel1.setText("INSCRIPCION");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Filtros:");

        jRBInscriptas.setText("Inscriptas");

        jRBNoInscriptas.setText("No inscriptas");

        jRBTodas.setText("Mostrar todas");
        jRBTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBTodasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRBInscriptas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRBNoInscriptas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRBTodas)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRBInscriptas)
                    .addComponent(jRBNoInscriptas)
                    .addComponent(jRBTodas))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabel3.setText("Materias:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 94, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(33, 33, 33)
                .addComponent(jCombo_Materias, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCombo_Materias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jBInscribirse.setText("Inscribirse");

        jBAnularInscripcion.setText("Anular inscripcion");

        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });

        jLabel4.setText("Alumno:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(244, Short.MAX_VALUE)
                .addComponent(jBInscribirse)
                .addGap(18, 18, 18)
                .addComponent(jBAnularInscripcion)
                .addGap(18, 18, 18)
                .addComponent(jBSalir)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jCombo_Alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jCombo_Alumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBInscribirse)
                    .addComponent(jBAnularInscripcion)
                    .addComponent(jBSalir))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBSalirActionPerformed

    private void jRBTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBTodasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRBTodasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAnularInscripcion;
    private javax.swing.JButton jBInscribirse;
    private javax.swing.JButton jBSalir;
    private javax.swing.JComboBox<Alumno> jCombo_Alumno;
    private javax.swing.JComboBox<Materia> jCombo_Materias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRBInscriptas;
    private javax.swing.JRadioButton jRBNoInscriptas;
    private javax.swing.JRadioButton jRBTodas;
    // End of variables declaration//GEN-END:variables
}
