/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Materia;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author elias
 */
public class materiaData {
  private Connection con = null;
    private String tabla = "materia";

    public materiaData() {

        con = Conexion.conectar();
    }

    public List<Materia> getAll() {
        List<Materia> mats = new ArrayList<>();
        String consulta = "SELECT * FROM " + this.tabla;
        try {

            PreparedStatement ps = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Materia mat = new Materia(rs.getInt("idMateria"),
                        rs.getString("nombre"),
                        rs.getInt("Año"),
                        rs.getBoolean("estado")
                );
                        
                mats.add(mat);
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las materias: " + e.getMessage());
        }
        return mats;
    }

    public List<Materia> getAllActivos() {
        List<Materia> mats = new ArrayList<>();
        String consulta = "SELECT * FROM " + this.tabla + " WHERE estado=true";
        try {

            PreparedStatement ps = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Materia mat = new Materia(rs.getInt("idMateria"),
                        rs.getString("nombre"),
                        rs.getInt("Año"),
                        rs.getBoolean("estado")
                );
                        
                mats.add(mat);
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las materias: " + e.getMessage());
        }
        return mats;
    }

    public Materia buscarPorId(int id) {
        Materia mat = null;
        
        String sql = "SELECT * FROM " + this.tabla + " WHERE idMateria = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                mat = new Materia(rs.getInt("idMateria"),
                        rs.getString("nombre"),
                        rs.getInt("Año"),
                        rs.getBoolean("estado")
                );
                        
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la materia: " + e.getMessage());
        
        }
        return mat;
    }

    public void actualizar(Materia mat) {

        String consulta = "UPDATE " + this.tabla + " SET nombre=?, año=?, estado=? "
                + " WHERE idMateria=?";;
        try {

            PreparedStatement ps = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, mat.getNombre());
            ps.setInt(2, mat.getAnio());
            ps.setBoolean(3, mat.isEstado());
            ps.setInt(4, mat.getIdMateria());
            
   

            int res = ps.executeUpdate();

            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Modificacion exitosa.");
            } else {
                JOptionPane.showMessageDialog(null, "La materia no existe");
            }

            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materias: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String consulta = "DELETE FROM " + this.tabla + " WHERE idMateria = ?";

        try {
            PreparedStatement ps = con.prepareStatement(consulta);

            ps.setInt(1, id);
            int res = ps.executeUpdate();

            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Se elimino la materia");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro la materia");
            }

            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materias: " + e.getMessage());
        }

    }

    public void bajaLogica(int id) {
        try {
            String sql = "UPDATE materia SET estado = 0 WHERE idMateria = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int res = ps.executeUpdate();

            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Se eliminó la materia.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro la materia");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materia");
        }
    }

    public void altaLogica(int id) {
        try {
            String sql = "UPDATE materia SET estado = 1 WHERE idMateria = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int res = ps.executeUpdate();

            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Se dio de alta la materia.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro la materia.");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materia");
        }
    }

    public void nuevaMateria(Materia mat) {

        String consulta = "INSERT INTO materia (nombre, año, estado) VALUES (?,?, 1)";

        try {
            PreparedStatement ps = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, mat.getNombre());
            ps.setInt(2, mat.getAnio());
            

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                mat.setIdMateria(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Materia añadida correctamente");
            }

            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materia" + e.getMessage());
        }

    }
    
    
}
