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
		usuario.setNombreUsuario(rs.getString("nombre_usuario"));
		usuario.setNombre(rs.getString("nombre"));
		usuario.setApellido1(rs.getString("apellido1"));
		usuario.setApellido2(rs.getString("apellido2"));
		usuario.setDni(rs.getString("dni"));
		usuario.setFechaCreacion(FechaUtils.recuperarFechaYHora(rs.getString("fecha_creacion")));
		usuario.setContrasena(rs.getString("contrasena"));
		usuario.setEmail(rs.getString("email"));
		usuario.setActivo(rs.getBoolean("activo"));
		usuario.setTipoUsuario(rs.getInt("tipo_usuario"));
		usuario.setTelefono(rs.getString("telefono"));
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
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO usuario (nombre_usuario,nombre,apellido1,apellido2,dni,fecha_creacion,contrasena,email,activo,tipo_usuario,telefono) VALUES(?,?,?,?,?,?,?,?,?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getNombreUsuario());
		ps.setString(2, objeto.getNombre());
		ps.setString(3, objeto.getApellido1());
		ps.setString(4, objeto.getApellido2());
		ps.setString(5, objeto.getDni());
		ps.setString(6, FechaUtils.convertirFecha(objeto.getFechaCreacion()));
		ps.setString(7, objeto.getContrasena());
		ps.setString(8, objeto.getEmail());
		ps.setBoolean(9, objeto.isActivo());
		ps.setInt(10, objeto.getTipoUsuario());
		ps.setString(11, objeto.getTelefono());
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
		PreparedStatement ps =getConnection().prepareStatement("UPDATE usuario SET nombre_usuario =? , nombre =? , apellido1 =? , apellido2 =? , dni =? , fecha_creacion =? , contrasena =? , email =? , activo =? , tipo_usuario =? , telefono =? WHERE id=?");
		ps.setString(1, objeto.getNombreUsuario());
		ps.setString(2, objeto.getNombre());
		ps.setString(3, objeto.getApellido1());
		ps.setString(4, objeto.getApellido2());
		ps.setString(5, objeto.getDni());
		ps.setString(6, FechaUtils.convertirFecha(objeto.getFechaCreacion()));
		ps.setString(7, objeto.getContrasena());
		ps.setString(8, objeto.getEmail());
		ps.setBoolean(9, objeto.isActivo());
		ps.setInt(10, objeto.getTipoUsuario());
		ps.setString(11, objeto.getTelefono());
		ps.setLong(12, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	
}
