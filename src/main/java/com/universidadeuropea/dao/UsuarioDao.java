package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.Usuario;
import com.universidadeuropea.idao.IUsuarioDao;

public class UsuarioDao extends Dao<Usuario, Long> implements IUsuarioDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM usuario WHERE id =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM usuario WHERE id =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM usuario";
	}
	
	@Override
	protected Usuario mapear(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getLong("id"));
		usuario.setIdUsuario(rs.getString("id_usuario"));
		usuario.setNombre(rs.getString("nombre"));
		usuario.setApellido1(rs.getString("apellido1"));
		usuario.setContrasena(rs.getString("contrasena"));
		usuario.setEmail(rs.getString("email"));
		return usuario;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public Usuario save(Usuario objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO usuario (id_usuario,nombre,apellido1,contrasena,email) VALUES(?,?,?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getIdUsuario());
		ps.setString(2, objeto.getNombre());
		ps.setString(3, objeto.getApellido1());
		ps.setString(4, objeto.getContrasena());
		ps.setString(5, objeto.getEmail());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		long pk =0; 
		while (rs.next()) {
			pk = rs.getLong(1);
		}
		objeto.setId(pk);
		cerrarConexion();
		return objeto;
	}

	@Override
	public boolean delete(Usuario objeto) throws Exception {
		return super.deleteById(objeto.getId());
	}

	@Override
	public Usuario update(Usuario objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE usuario SET id_usuario =? , nombre =? , apellido1 =? , contrasena =? , email =?  WHERE id=?");
		ps.setString(1, objeto.getIdUsuario());
		ps.setString(2, objeto.getNombre());
		ps.setString(3, objeto.getApellido1());
		ps.setString(4, objeto.getContrasena());
		ps.setString(5, objeto.getEmail());
		ps.setLong(6, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	
}
