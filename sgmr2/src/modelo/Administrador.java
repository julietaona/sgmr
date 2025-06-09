package modelo;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Administrador {
    public int administradorId;
    public String nombre;
    public long cuit;
    public String direccion;
    public String email;
    public long telefono;
    public boolean activo;
    public Date fechaCreacion;

    public static List<Administrador> obtenerTodos() {
        List<Administrador> lista = new ArrayList<>();
        String query = "SELECT * FROM administradores";
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Administrador a = new Administrador();
                a.administradorId = rs.getInt("administrador_id");
                a.nombre = rs.getString("nombre");
                a.cuit = rs.getLong("cuit");
                a.direccion = rs.getString("direccion");
                a.email = rs.getString("email");
                a.telefono = rs.getLong("telefono");
                a.activo = rs.getBoolean("activo");
                a.fechaCreacion = rs.getDate("fecha_creacion");
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
 
    public boolean crear() {
        String query = "INSERT INTO administradores (nombre, cuit, direccion, email, telefono, activo, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombre);
            stmt.setLong(2, cuit);
            stmt.setString(3, direccion);
            stmt.setString(4, email);
            stmt.setLong(5, telefono);
            stmt.setBoolean(6, activo);
            stmt.setDate(7, new java.sql.Date(fechaCreacion.getTime()));
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        administradorId = rs.getInt(1);
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizar() {
        String query = "UPDATE administradores SET nombre=?, cuit=?, direccion=?, email=?, telefono=?, activo=?, fecha_creacion=? WHERE administrador_id=?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setLong(2, cuit);
            stmt.setString(3, direccion);
            stmt.setString(4, email);
            stmt.setLong(5, telefono);
            stmt.setBoolean(6, activo);
            stmt.setDate(7, new java.sql.Date(fechaCreacion.getTime()));
            stmt.setInt(8, administradorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}