package es.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * * @author Jesus Hernandez
 */
public class ArticuloDAO {

    private DataSource ds;

    
    public ArticuloDAO() {
        try {
            Context ctx = new InitialContext();
            
            ds = (DataSource) ctx.lookup("jdbc/articdbp"); 
            
            if (ds == null) {
                throw new RuntimeException("DataSource no encontrado: jdbc/articdbp");
            }
            
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar el DataSource", e);
        }
    }

    /**
     * Conexión al pool.
     */
    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    
    public List<Articulo> getTodos() {
        
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT IDPROD, CATEGORIA, DESCRIPCION, PRECIO FROM ARTICULOS";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Articulo art = new Articulo();
                art.setId(rs.getInt("IDPROD"));
                art.setCategoria(rs.getString("CATEGORIA"));
                art.setDescripcion(rs.getString("DESCRIPCION"));
                art.setPrecio(rs.getDouble("PRECIO"));
                
                articulos.add(art);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return articulos;
    }

    
    public boolean insertar(Articulo art) {
        String sql = "INSERT INTO ARTICULOS (IDPROD, CATEGORIA, DESCRIPCION, PRECIO) VALUES (?, ?, ?, ?)";
        boolean exito = false;
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, art.getId());
            ps.setString(2, art.getCategoria());
            ps.setString(3, art.getDescripcion());
            ps.setDouble(4, art.getPrecio());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                exito = true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return exito;
    }


    
}