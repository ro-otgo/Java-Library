package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.Escritor;
import com.universidadeuropea.idao.IEscritorDao;

public class EscritorDao extends Dao<Escritor, Escritor> implements IEscritorDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM escritor WHERE id_autor =? and id_libro =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM escritor WHERE id_autor =? and id_libro =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM escritor";
	}
	
	@Override
	protected Escritor mapear(ResultSet rs) throws SQLException {
		Escritor escritor = new Escritor();
		while(rs.next()) {
			escritor.setIdAutor(rs.getLong("id_autor"));
			escritor.setIdLibro(rs.getLong("id_libro"));
		}
		return escritor;
	}
	
	/*-
	 * =========================================================
	 * Clase Abstracta: Clave compuesta 
	 * ========================================================= 
	 */
	
	@Override
	public Escritor getById(Escritor id) throws Exception {
		Escritor data = null;
		obtenerConexionDB();
		PreparedStatement ps = getConnection().prepareStatement(selectById());
		ps.setObject(1, id.getIdAutor());
		ps.setObject(2, id.getIdLibro());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			data = mapear(rs);
		}
		return data;
	}

	@Override
	public boolean deleteById(Escritor id) throws Exception {
		try {
			obtenerConexionDB();
			PreparedStatement ps = getConnection().prepareStatement(deleteById());
			ps.setObject(1, id.getIdAutor());
			ps.setObject(2, id.getIdLibro());
			boolean execute = ps.execute();
			return execute;
		} finally {
			cerrarConexion();
		}
	}
	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	


	@Override
	public Escritor save(Escritor objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO escritor (id_autor, id_libro) VALUES(?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, objeto.getIdAutor());
		ps.setLong(2, objeto.getIdLibro());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}

	@Override
	public boolean delete(Escritor objeto) throws Exception {
		return super.deleteById(objeto);
	}

	@Override
	public Escritor update(Escritor objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE escritor SET id_autor =?, id_libro=? WHERE id_autor=?, id_libro=?");
		ps.setLong(1, objeto.getIdAutor());
		ps.setLong(2, objeto.getIdLibro());
		ps.setLong(3, objeto.getIdAutor());
		ps.setLong(4, objeto.getIdLibro());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	
}
