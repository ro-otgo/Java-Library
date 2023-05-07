package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.CatalogoLibros;
import com.universidadeuropea.idao.ICatalogoLibrosDao;

public class CatalogoLibrosDao extends Dao <CatalogoLibros, Long> implements ICatalogoLibrosDao{

	@Override
	protected String selectById() {
		return "SELECT * FROM catalogo_libros WHERE id =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM catalogo_libros WHERE id =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM catalogo_libros";
	}
	
	@Override
	protected CatalogoLibros mapear(ResultSet rs) throws SQLException {
		CatalogoLibros catalogoLibros = new CatalogoLibros();
		catalogoLibros.setId(rs.getLong("id"));
		catalogoLibros.setIsbn(rs.getString("isbn"));
		catalogoLibros.setNombreLibro(rs.getString("nombre_libro"));
		catalogoLibros.setFechaPublicacion(FechaUtils.recuperarFecha(rs.getString("fecha_publicacion")));
		catalogoLibros.setFormato(rs.getInt("formato"));
		return catalogoLibros;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public CatalogoLibros save(CatalogoLibros objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO catalogo_libros (isbn,nombre_libro,fecha_publicacion,formato) VALUES(?,?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getIsbn());
		ps.setString(2, objeto.getNombreLibro());
		ps.setString(3, FechaUtils.convertirFecha(objeto.getFechaPublicacion()));
		ps.setInt(4, objeto.getFormato());
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
	public boolean delete(CatalogoLibros objeto) throws Exception {
		return super.deleteById(objeto.getId());
	}

	@Override
	public CatalogoLibros update(CatalogoLibros objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE catalogo_libros SET isbn =? , nombre_libro =? , fecha_publicacion =? , formato =? WHERE id=?");
		ps.setString(1, objeto.getIsbn());
		ps.setString(2, objeto.getNombreLibro());
		ps.setString(3, FechaUtils.convertirFecha(objeto.getFechaPublicacion()));
		ps.setInt(4, objeto.getFormato());
		ps.setLong(5, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}

}
