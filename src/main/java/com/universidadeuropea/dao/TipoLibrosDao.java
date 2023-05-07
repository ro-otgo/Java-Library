package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.TipoLibros;
import com.universidadeuropea.idao.ITipoLibrosDao;

public class TipoLibrosDao extends Dao<TipoLibros, Long> implements ITipoLibrosDao{

	@Override
	protected String selectById() {
		return "SELECT * FROM tipo_libros WHERE id =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM tipo_libros WHERE id =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM tipo_libros";
	}
	
	@Override
	protected TipoLibros mapear(ResultSet rs) throws SQLException {
		TipoLibros tipolibro = new TipoLibros();
		while(rs.next()) {
			tipolibro.setId(rs.getLong("id"));
			tipolibro.setTipo(rs.getString("tipo"));
			tipolibro.setDescripcion(rs.getString("descripcion"));
		}
		return tipolibro;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public TipoLibros save(TipoLibros objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO tipo_libros (tipo,descripcion) VALUES(?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getTipo());
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
	public boolean delete(TipoLibros objeto) throws Exception {
		return super.deleteById(objeto.getId());
	}

	@Override
	public TipoLibros update(TipoLibros objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE tipo_libros SET tipo =? , descripcion =? WHERE id=?");
		ps.setString(1, objeto.getTipo());
		ps.setString(2, objeto.getDescripcion());
		ps.setLong(3, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
		
}
