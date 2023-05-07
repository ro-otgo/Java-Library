package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.Editorial;
import com.universidadeuropea.idao.IEditorialDao;

public class EditorialDao extends Dao<Editorial, Long> implements IEditorialDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM editorial WHERE id =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM editorial WHERE id =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM editorial";
	}
	
	
	@Override
	protected Editorial mapear(ResultSet rs) throws SQLException {
		Editorial reserva = new Editorial();
		while(rs.next()) {
			reserva.setId(rs.getLong("id"));
			reserva.setNombre(rs.getString("nombre"));
			reserva.setCorreoElectronico(rs.getString("correoElectronico"));
			reserva.setUrl(rs.getString("url"));
			reserva.setDireccion(rs.getString("direccion"));
		}
		return reserva;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public Editorial save(Editorial objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO editorial (nombre, correo_electronico,url,direccion) VALUES(?,?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getNombre());
		ps.setString(2, objeto.getCorreoElectronico());
		ps.setString(3, objeto.getUrl());
		ps.setString(4, objeto.getDireccion());
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
	public boolean delete(Editorial objeto) throws Exception {
		return super.deleteById(objeto.getId());
	}

	@Override
	public Editorial update(Editorial objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE editorial SET nombre=?, correo_electronico=?,url=?,direccion=? WHERE id=?");
		ps.setString(1, objeto.getNombre());
		ps.setString(2, objeto.getCorreoElectronico());
		ps.setString(3, objeto.getUrl());
		ps.setString(4, objeto.getDireccion());
		ps.setLong(5, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	
}
