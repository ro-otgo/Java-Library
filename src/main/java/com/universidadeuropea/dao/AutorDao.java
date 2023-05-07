package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.Autor;
import com.universidadeuropea.idao.IAutorDao;

public class AutorDao extends Dao <Autor, Long> implements IAutorDao{

	@Override
	protected String selectById() {
		return "SELECT * FROM autor WHERE id =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM autor WHERE id =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM autor";
	}
	
	@Override
	protected Autor mapear(ResultSet rs) throws SQLException {
		Autor autor = new Autor();
		autor.setId(rs.getLong("id"));
		autor.setNombre(rs.getString("nombre"));
		autor.setApellido1(rs.getString("apellido1"));
		autor.setApellido2(rs.getString("apellido2"));
		return autor;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public Autor save(Autor objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO autor (nommbre,apellido1,apellido2) VALUES(?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getNombre());
		ps.setString(2, objeto.getApellido1());
		ps.setString(3, objeto.getApellido2());
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
	public boolean delete(Autor objeto) throws Exception {
		return super.deleteById(objeto.getId());
	}

	@Override
	public Autor update(Autor objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE autor SET nombre =? , apellido1 =? , apellido2 =? WHERE id=?");
		ps.setString(1, objeto.getNombre());
		ps.setString(2, objeto.getApellido1());
		ps.setString(3, objeto.getApellido2());
		ps.setLong(4, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
}
