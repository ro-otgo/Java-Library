package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.Bibliotecario;
import com.universidadeuropea.idao.IBibliotecarioDao;

public class BibliotecarioDao extends Dao<Bibliotecario, Long> implements IBibliotecarioDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM bibliotecario WHERE id_bibliotecario =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM bibliotecario WHERE id_bibliotecario =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM bibliotecario";
	}
	
	@Override
	protected Bibliotecario mapear(ResultSet rs) throws SQLException {
		Bibliotecario bibliotecario = new Bibliotecario();
		bibliotecario.setIdBibliotecario(rs.getLong("id_bibliotecario"));
		bibliotecario.setNombreBibliotecario(rs.getString("nombre_bibliotecario"));
		bibliotecario.setContrasena(rs.getString("contrasena"));
		return bibliotecario;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public Bibliotecario save(Bibliotecario objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO bibliotecario (nombre_bibliotecario,contrasena) VALUES(?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getNombreBibliotecario());
		ps.setString(2, objeto.getContrasena());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		long pk =0; 
		while (rs.next()) {
			pk = rs.getLong(1);
		}
		objeto.setIdBibliotecario(pk);
		cerrarConexion();
		return objeto;
	}

	@Override
	public boolean delete(Bibliotecario objeto) throws Exception {
		return super.deleteById(objeto.getIdBibliotecario());
	}

	@Override
	public Bibliotecario update(Bibliotecario objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE bibliotecario SET nombre_bibliotecario =? , contrasena =?  WHERE id_bibliotecario=?");
		ps.setString(1, objeto.getNombreBibliotecario());
		ps.setString(2, objeto.getContrasena());
		ps.setLong(3, objeto.getIdBibliotecario());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	
}