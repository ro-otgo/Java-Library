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
		return "SELECT * FROM libros WHERE id_libro =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM libros WHERE id_libro =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM libros";
	}
	
	@Override
	protected Libros mapear(ResultSet rs) throws SQLException {
		Libros libro = new Libros();
		libro.setIdLibro(rs.getLong("id_libro"));
		libro.setTitulo(rs.getString("id"));
		libro.setIsbn(rs.getLong("isbn"));
		libro.setAutor(rs.getString("autor"));
		libro.setBorrado(rs.getBoolean("borrado"));
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
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO libros (titulo,isbn,autor,borrado) VALUES(?,?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getTitulo());
		ps.setLong(2, objeto.getIsbn());
		ps.setString(3, objeto.getAutor());
		ps.setBoolean(4, objeto.getBorrado());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		long pk =0; 
		while (rs.next()) {
			pk = rs.getLong(1);
		}
		objeto.setIdLibro(pk);
		cerrarConexion();
		return objeto;
	}

	@Override
	public boolean delete(Libros objeto) throws Exception {
		return super.deleteById(objeto.getIdLibro());
	}

	@Override
	public Libros update(Libros objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE libros SET titulo =? , isbn =? , autor =? , borrado =? WHERE id_libro=?");
		ps.setString(1, objeto.getTitulo());
		ps.setLong(2, objeto.getIsbn());
		ps.setString(3, objeto.getAutor());
		ps.setBoolean(4, objeto.getBorrado());
		ps.setLong(5, objeto.getIdLibro());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
}
