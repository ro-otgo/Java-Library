package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.universidadeuropea.entities.TipoReserva;
import com.universidadeuropea.idao.ITipoReservaDao;

public class TipoReservaDao extends Dao<TipoReserva, Long> implements ITipoReservaDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM tipo_reserva WHERE id =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM tipo_reserva WHERE id =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM tipo_reserva";
	}
	

	@Override
	protected TipoReserva mapear(ResultSet rs) throws SQLException {
		TipoReserva reeserva = new TipoReserva();
		reeserva.setId(rs.getLong("id"));
		reeserva.setTipo(rs.getString("tipo"));
		reeserva.setPlazo(rs.getInt("plazo"));
		reeserva.setDescripcion(rs.getString("descripcion"));
		return reeserva;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public TipoReserva save(TipoReserva objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO tipo_reserva (tipo, plazo,descripcion ) VALUES(?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, objeto.getTipo());
		ps.setInt(2, objeto.getPlazo());
		ps.setString(3, objeto.getDescripcion());
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
	public boolean delete(TipoReserva objeto) throws Exception {
		return super.deleteById(objeto.getId());
	}

	@Override
	public TipoReserva update(TipoReserva objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE tipo_reserva SET tipo=?, plazo =?,descripcion =? WHERE id=?");
		ps.setString(1, objeto.getTipo());
		ps.setInt(2, objeto.getPlazo());
		ps.setString(3, objeto.getDescripcion());
		ps.setLong(4, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	
}
