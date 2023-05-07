package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.Editor;
import com.universidadeuropea.idao.IEditorDao;

public class EditorDao extends Dao<Editor, Editor> implements IEditorDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM editor WHERE id_editorial =? and id_libro =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM editor WHERE id_editorial =? and id_libro =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM editor";
	}
	
	@Override
	protected Editor mapear(ResultSet rs) throws SQLException {
		Editor editor = new Editor();
		editor.setIdEditorial(rs.getLong("id_editorial"));
		editor.setIdLibro(rs.getLong("id_libro"));
		return editor;
	}
	
	/*-
	 * =========================================================
	 * Clase Abstracta: Clave compuesta 
	 * ========================================================= 
	 */
	
	@Override
	public Editor getById(Editor id) throws Exception {
		Editor data = null;
		obtenerConexionDB();
		PreparedStatement ps = getConnection().prepareStatement(selectById());
		ps.setObject(1, id.getIdEditorial());
		ps.setObject(2, id.getIdLibro());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			data = mapear(rs);
		}
		return data;
	}

	@Override
	public boolean deleteById(Editor id) throws Exception {
		try {
			obtenerConexionDB();
			PreparedStatement ps = getConnection().prepareStatement(deleteById());
			ps.setObject(1, id.getIdEditorial());
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
	public Editor save(Editor objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO editor (id_editorial, id_libro) VALUES(?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, objeto.getIdEditorial());
		ps.setLong(2, objeto.getIdLibro());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}

	@Override
	public boolean delete(Editor objeto) throws Exception {
		return super.deleteById(objeto);
	}

	@Override
	public Editor update(Editor objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE editor SET id_editorial =?, id_libro=? WHERE id_editorial=?, id_libro=?");
		ps.setLong(1, objeto.getIdEditorial());
		ps.setLong(2, objeto.getIdLibro());
		ps.setLong(3, objeto.getIdEditorial());
		ps.setLong(4, objeto.getIdLibro());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	
}
