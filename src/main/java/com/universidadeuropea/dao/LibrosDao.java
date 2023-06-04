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
		libro.setTitulo(rs.getString("titulo"));
		libro.setIsbn(rs.getLong("isbn"));
		libro.setAutor(rs.getString("autor"));
		libro.setBorrado(rs.getBoolean("borrado"));
		libro.setReservado(rs.getBoolean("reservado"));
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
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO libros (titulo,isbn,autor,borrado,reservado) VALUES(?,?,?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getTitulo());
		ps.setLong(2, objeto.getIsbn());
		ps.setString(3, objeto.getAutor());
		ps.setBoolean(4, objeto.getBorrado());
		ps.setBoolean(5, objeto.getReservado());
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
		PreparedStatement ps =getConnection().prepareStatement("UPDATE libros SET titulo =? , isbn =? , autor =? , borrado =?, reservado=? WHERE id_libro=?");
		ps.setString(1, objeto.getTitulo());
		ps.setLong(2, objeto.getIsbn());
		ps.setString(3, objeto.getAutor());
		ps.setBoolean(4, objeto.getBorrado());
		ps.setBoolean(5, objeto.getReservado());
		ps.setLong(6, objeto.getIdLibro());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	// metodo que actualiza el atributo reservado de un libro a false
	public void devolverLibro(Long id) {
		obtenerConexionDB();
		PreparedStatement ps;
		try {
			ps = getConnection().prepareStatement("UPDATE Libros SET reservado = 0 WHERE id_libro = ?");
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			errorHandler(e);
		}
		cerrarConexion();

	}
	
	public Libros actualizarEstadoLibro(Libros libro) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE libros SET borrado =?, reservado=? WHERE id_libro=?");
		ps.setBoolean(1, libro.getBorrado());
		ps.setBoolean(2, libro.getReservado());
		ps.setLong(3, libro.getIdLibro());
		ps.executeUpdate();
		cerrarConexion();
		return libro;
	}
	
	public boolean estaReservado(Libros libro) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("SELECT * FROM libros where id_libro=?");
		ps.setLong(1, libro.getIdLibro());
		ResultSet rs = ps.executeQuery();
		Libros mapear = mapear(rs);
		cerrarConexion();
		return mapear.getReservado();
	}
}
