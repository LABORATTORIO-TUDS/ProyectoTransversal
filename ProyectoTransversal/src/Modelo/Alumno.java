/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;

/**
 *
 * @author crist
 */
public class Alumno {
    private int legajo;
    private String nombre;
    private String apellido;
    private int dni;
    private LocalDate FechaNacimiento;
    private boolean estado;

    public Alumno() {
    }

    public Alumno(String nombre, String apellido, int dni, LocalDate FechaNacimiento, boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.FechaNacimiento = FechaNacimiento;
        this.estado = estado;
    }
    
    

    public Alumno(int legajo, String nombre, String apellido, int dni, LocalDate FechaNacimiento, boolean estado) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.FechaNacimiento = FechaNacimiento;
        this.estado = estado;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
    this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return  nombre + " " + apellido;
    }

    
    
}
