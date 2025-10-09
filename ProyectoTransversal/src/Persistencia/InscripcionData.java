/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

/**
 *
 * @author Victor
 */
import Modelo.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class InscripcionData {
    private Connection con = null;
    private AlumnoData alumnoData;
    private materiaData materiaData;

    public InscripcionData() {
        con = Conexion.conectar();
        alumnoData = new AlumnoData();
        materiaData = new materiaData();
    }
public void inscribirAlumno(Inscripcion inscripcion) {
        String sql = "INSERT INTO inscripcion (nota, idAlumno, idMateria) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inscripcion.getNota());
            ps.setInt(2, inscripcion.getAlumno().getLegajo());
            ps.setInt(3, inscripcion.getMateria().getIdMateria());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                inscripcion.setLegajo(rs.getInt(1));
            }
            JOptionPane.showMessageDialog(null, "Inscripción guardada exitosamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al inscribir alumno: " + ex.getMessage());
        }
    }


    public void anularInscripcion(int idAlumno, int idMateria) {
        String sql = "DELETE FROM inscripcion WHERE idAlumno = ? AND idMateria = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            int exito = ps.executeUpdate();
            if (exito > 0) {
                JOptionPane.showMessageDialog(null, "Inscripción anulada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la inscripción a eliminar.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al anular inscripción: " + ex.getMessage());
        }
    }

  
    public List<Materia> obtenerMateriasInscriptas(int idAlumno) {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT m.* FROM materia m, inscripcion i "
                   + "WHERE i.idMateria = m.idMateria AND i.idAlumno = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Materia m = new Materia();
                m.setIdMateria(rs.getInt("idMateria"));
                m.setNombre(rs.getString("nombre"));
                m.setAnio(rs.getInt("año"));
                m.setEstado(rs.getBoolean("estado"));
                materias.add(m);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener materias inscriptas: " + ex.getMessage());
        }
        return materias;
    }

   
    public List<Materia> obtenerMateriasNoInscriptas(int idAlumno) {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM materia WHERE estado = 1 AND idMateria NOT IN "
                   + "(SELECT idMateria FROM inscripcion WHERE idAlumno = ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Materia m = new Materia();
                m.setIdMateria(rs.getInt("idMateria"));
                m.setNombre(rs.getString("nombre"));
                m.setAnio(rs.getInt("año"));
                m.setEstado(rs.getBoolean("estado"));
                materias.add(m);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener materias no inscriptas: " + ex.getMessage());
        }
        return materias;
    }


    public void actualizarNota(int idAlumno, int idMateria, int nota) {
        String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno = ? AND idMateria = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nota);
            ps.setInt(2, idAlumno);
            ps.setInt(3, idMateria);
            int exito = ps.executeUpdate();

            if (exito > 0) {
                JOptionPane.showMessageDialog(null, "Nota actualizada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la inscripción.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar nota: " + ex.getMessage());
        }
    }


    public List<Inscripcion> obtenerInscripcionesPorAlumno(int idAlumno) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion WHERE idAlumno = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Inscripcion i = new Inscripcion();
                i.setLegajo(rs.getInt("idInscripto"));
                i.setNota(rs.getInt("nota"));
                Alumno a = alumnoData.buscarPorId(rs.getInt("idAlumno"));
                Materia m = materiaData.buscarPorId(rs.getInt("idMateria"));
                i.setAlumno(a);
                i.setMateria(m);
                inscripciones.add(i);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener inscripciones: " + ex.getMessage());
        }
        return inscripciones;
    }
}


