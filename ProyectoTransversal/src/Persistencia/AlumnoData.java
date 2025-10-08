/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Alumno;
import Modelo.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author crist
 */
public class alumnoData {

    private Connection con = null;
    private String tabla = "alumno";

    public alumnoData() {

        con = Conexion.conectar();
    }

    public List<Alumno> getAll() {
        List<Alumno> als = new ArrayList<>();
        String consulta = "SELECT * FROM " + this.tabla;
        try {

            PreparedStatement ps = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alumno al = new Alumno(rs.getInt("idAlumno"),
                        rs.getString("nombre"), rs.getString("apellido"),
                        rs.getInt("dni"), rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getBoolean("estado"));
                als.add(al);
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los alumnos: " + e.getMessage());
        }
        return als;
    }

    public List<Alumno> getAllActivos() {
        List<Alumno> als = new ArrayList<>();
        String consulta = "SELECT * FROM " + this.tabla + " WHERE estado=true";
        try {

            PreparedStatement ps = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alumno al = new Alumno(rs.getInt("idAlumno"),
                        rs.getString("nombre"), rs.getString("apellido"),
                        rs.getInt("dni"), rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getBoolean("estado"));
                als.add(al);
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los alumnos: " + e.getMessage());
        }
        return als;
    }

    public Alumno buscarPorId(int id) {
        Alumno al = null;
        String sql = "SELECT * FROM " + this.tabla + " WHERE idAlumno = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                al = new Alumno(rs.getInt("idAlumno"),
                        rs.getString("nombre"), rs.getString("apellido"), rs.getInt("dni"), rs.getDate("fechaNacimiento").toLocalDate(), rs.getBoolean("estado"));
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar alumno: " + e.getMessage());
        
        }
        return al;
    }

    public void actualizar(Alumno al) {

        String consulta = "UPDATE " + this.tabla + " SET dni=?, apellido=?,nombre=?,"
                + " fechaNacimiento=? WHERE idAlumno=?";
        try {

            PreparedStatement ps = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, al.getDni());
            ps.setString(2, al.getApellido());
            ps.setString(3, al.getNombre());
            ps.setDate(4, java.sql.Date.valueOf(al.getFechaNacimiento()));
            ps.setInt(5, al.getLegajo());

            int res = ps.executeUpdate();

            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Modificacion exitosa.");
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no existe");
            }

            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumnos: " + e.getMessage());
        }
    }

    public void delete(Alumno al) {
        String consulta = "DELETE FROM " + this.tabla + " WHERE idAlumno = ?";

        try {
            PreparedStatement ps = con.prepareStatement(consulta);

            ps.setInt(1, al.getLegajo());
            int res = ps.executeUpdate();

            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Se elimino el alumno");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro al alumno");
            }

            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumnos: " + e.getMessage());
        }

    }

    public void bajaLogica(int id) {
        try {
            String sql = "UPDATE alumno SET estado = 0 WHERE idAlumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int res = ps.executeUpdate();

            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Se eliminó el alumno.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro al alumno");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno");
        }
    }

    public void altaLogica(int id) {
        try {
            String sql = "UPDATE alumno SET estado = 1 WHERE idAlumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int res = ps.executeUpdate();

            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Se dio de alta el alumno.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro al alumno");
            }
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno");
        }
    }

    public void nuevoAlumno(Alumno al) {

        String consulta = "INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado) VALUES (?,?,?,?, 1)";

        try {
            PreparedStatement ps = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, al.getDni());
            ps.setString(2, al.getApellido());
            ps.setString(3, al.getNombre());
            ps.setDate(4, java.sql.Date.valueOf(al.getFechaNacimiento()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                al.setLegajo(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Alumno añadido correctamente");
            }

            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno");
        }

    }

}
