package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.TipoUsuario;
import com.universidadeuropea.idao.ITipoUsuarioDao;

public class TipoUsuarioDao extends Dao<TipoUsuario, Long> implements ITipoUsuarioDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM tipo_usuario WHERE id =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM tipo_usuario WHERE id =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM tipo_usuario";
	}
	
	@Override
	protected TipoUsuario mapear(ResultSet rs) throws SQLException {
		TipoUsuario tipoUsuario = new TipoUsuario();
		while(rs.next()) {
			tipoUsuario.setId(rs.getLong("id"));
			tipoUsuario.setRol(rs.getString("rol"));
			tipoUsuario.setDescripcion(rs.getString("descripcion"));
		}
		return tipoUsuario;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public TipoUsuario save(TipoUsuario objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO tipo_usuario (rol,descripcion) VALUES(?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getRol());
		ps.setString(2, objeto.getDescripcion());
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
	public boolean delete(TipoUsuario objeto) throws Exception {
		return super.deleteById(objeto.getId());
	}

	@Override
	public TipoUsuario update(TipoUsuario objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE tipo_usuario SET rol =? , descripcion =? WHERE id=?");
		ps.setString(1, objeto.getRol());
		ps.setString(2, objeto.getDescripcion());
		ps.setLong(3, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	
}
