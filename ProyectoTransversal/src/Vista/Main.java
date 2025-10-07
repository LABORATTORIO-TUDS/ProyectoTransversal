/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Vista;

import Modelo.Alumno;
import Modelo.Conexion;
import Persistencia.AlumnoData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author crist
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AlumnoData al = new AlumnoData();
        List<Alumno> als = new ArrayList();
        als = al.getAll();
        for (Alumno al1 : als) {
            System.out.println("a");
            System.out.println(al1.getApellido());
        }
    }
    
}
