package modelo;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import tipos.TipoPersona;

public class Cliente {
    public int clienteId;
    public String nombre;
    public long cu;
    public TipoPersona tipoPersona;
    public String direccion;
    public String email;
    public long telefono;
    public boolean activo;
    public Date fechaCreacion;
    public int administradorId;

    public static List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String query = "SELECT * FROM clientes";
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.clienteId = rs.getInt("cliente_id");
                c.nombre = rs.getString("nombre");
                c.cu = rs.getLong("cu");
                c.tipoPersona = TipoPersona.valueOf(rs.getString("tipo_persona").toUpperCase());
                c.direccion = rs.getString("direccion");
                c.email = rs.getString("email");
                c.telefono = rs.getLong("telefono");
                c.activo = rs.getBoolean("activo");
                c.fechaCreacion = rs.getDate("fecha_creacion");
                c.administradorId = rs.getInt("administrador_id");
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean crear() {
        String sql = "INSERT INTO clientes (nombre, cu, tipo_persona, direccion, email, telefono, activo, fecha_creacion, administrador_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setLong(2, cu);
            stmt.setString(3, tipoPersona.name().toLowerCase());
            stmt.setString(4, direccion);
            stmt.setString(5, email);
            stmt.setLong(6, telefono);
            stmt.setBoolean(7, activo);
            stmt.setDate(8, new java.sql.Date(fechaCreacion.getTime()));
            stmt.setInt(9, administradorId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar() {
        String sql = "UPDATE clientes SET nombre = ?, cu = ?, tipo_persona = ?, direccion = ?, email = ?, telefono = ?, " +
                     "activo = ?, fecha_creacion = ?, administrador_id = ? WHERE cliente_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setLong(2, cu);
            stmt.setString(3, tipoPersona.name().toLowerCase());
            stmt.setString(4, direccion);
            stmt.setString(5, email);
            stmt.setLong(6, telefono);
            stmt.setBoolean(7, activo);
            stmt.setDate(8, new java.sql.Date(fechaCreacion.getTime()));
            stmt.setInt(9, administradorId);
            stmt.setInt(10, clienteId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
