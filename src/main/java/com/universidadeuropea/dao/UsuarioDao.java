package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.Usuario;
import com.universidadeuropea.idao.IUsuarioDao;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UsuarioDao extends Dao<Usuario, Long> implements IUsuarioDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM usuario WHERE id_usuario =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM usuario WHERE id_usuario =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM usuario";
	}
	
	@Override
	protected Usuario mapear(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(rs.getLong("id_usuario"));
		usuario.setNombreUsuario(rs.getString("nombre_usuario"));
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
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO usuario (nombre_usuario,nombre,apellido1,contrasena,email) VALUES(?,?,?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getNombreUsuario());
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
		objeto.setIdUsuario(pk);
		rs.close();
		ps.close();
		cerrarConexion();
		return objeto;
	}

	@Override
	public boolean delete(Usuario objeto) throws Exception {
		return super.deleteById(objeto.getIdUsuario());
	}

	@Override
	public Usuario update(Usuario objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE usuario SET nombre_usuario =? , nombre =? , apellido1 =? , contrasena =? , email =?  WHERE id_usuario=?");
		ps.setString(1, objeto.getNombreUsuario());
		ps.setString(2, objeto.getNombre());
		ps.setString(3, objeto.getApellido1());
		ps.setString(4, objeto.getContrasena());
		ps.setString(5, objeto.getEmail());
		ps.setLong(6, objeto.getIdUsuario());
		ps.executeUpdate();
		ps.close();
		cerrarConexion();
		return objeto;
	}
	
	
	public boolean validarUsuario(String username,String password) {
		obtenerConexionDB();
		PreparedStatement ps;
		BCrypt.Result verify = null;
		boolean result = false;
		try {
			ps = getConnection()
					.prepareStatement("SELECT * from usuario where nombre_usuario=?");

			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rs.getInt("id_usuario");
				String bcryptHashString = rs.getString("contrasena");
				verify = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
				result = verify.verified;
				break;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			errorHandler(e);
		}
		cerrarConexion();
		return result;
	}
	
	
	public Usuario buscarPorUsername(String username) {
		obtenerConexionDB();
		PreparedStatement ps;
		Usuario usuario = null;		
		try {
			ps = getConnection()
					.prepareStatement("SELECT * from usuario where nombre_usuario=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();			
			while (rs.next()) {
				usuario = mapear(rs);
				break;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			errorHandler(e);
		}
		cerrarConexion();
		return usuario;
	}
	
}
