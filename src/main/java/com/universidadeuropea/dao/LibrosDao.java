package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.universidadeuropea.entities.Libros;
import com.universidadeuropea.idao.ILibrosDao;

public class LibrosDao extends Dao <Libros, Long> implements ILibrosDao{

	@Override
	protected String selectById() {
		return "SELECT * FROM libros WHERE id =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM libros WHERE id =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM libros";
	}
	
	@Override
	protected Libros mapear(ResultSet rs) throws SQLException {
		Libros libro = new Libros();
		while(rs.next()) {
			libro.setId(rs.getLong("id"));
			libro.setIdLibro(rs.getLong("id_libro"));
		}
		return libro;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public Libros save(Libros objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO libros (id_libro) VALUES(?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, objeto.getIdLibro());
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
	public boolean delete(Libros objeto) throws Exception {
		return super.deleteById(objeto.getId());
	}

	@Override
	public Libros update(Libros objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE libros SET id_libro =? WHERE id=?");
		ps.setLong(1, objeto.getIdLibro());;
		ps.setLong(2, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
}
