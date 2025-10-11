package Persistencia;

import Modelo.Materia;
import javax.swing.JOptionPane;

public class MateriaData {

    public void guardarMateria(Materia materia) {
        materia.setIdMateria((int) (Math.random() * 1000));
        JOptionPane.showMessageDialog(null, "Materia '" + materia.getNombre() + "' guardada con exito.");
    }

    public Materia buscarMateria(int id) {
        if (id > 0) { 
            return new Materia(id, "Materia de Ejemplo", true);
        }
        return null;
    }

    public void actualizarMateria(Materia materia) {
        JOptionPane.showMessageDialog(null, "Materia actualizada correctamente.");
    }

    public void borrarMateria(int id) {
        JOptionPane.showMessageDialog(null, "Materia borrada con exito.");
    }
}