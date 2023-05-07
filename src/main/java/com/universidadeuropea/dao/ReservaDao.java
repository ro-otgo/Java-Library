package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.universidadeuropea.entities.Reserva;
import com.universidadeuropea.idao.IReservaDao;

public class ReservaDao extends Dao<Reserva, Long> implements IReservaDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM reserva WHERE id =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM reserva WHERE id =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM reserva";
	}
	
	@Override
	protected Reserva mapear(ResultSet rs) throws SQLException {
		Reserva reserva = new Reserva();
		reserva.setId(rs.getLong("id"));
		reserva.setIdLibro(rs.getLong("id_libro"));
		reserva.setIdUsuario(rs.getLong("id_usuario"));
		reserva.setFechaReserva(FechaUtils.recuperarFechaYHora(rs.getString("fecha_reserva")));
		reserva.setFechaDevolucion(FechaUtils.recuperarFechaYHora(rs.getString("fecha_devolucion")));
		reserva.setTipoReserva(rs.getLong("tipo_reserva"));
		return reserva;
	}

	
	/*-
	 * =========================================================
	 * Intefaz 
	 * ========================================================= 
	 */
	
	@Override
	public Reserva save(Reserva objeto) throws SQLException {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO reserva (id_libro, id_usuario,fecha_reserva,fecha_devolucion,tipo_reserva) VALUES(?,?,?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, objeto.getIdLibro());
		ps.setLong(2, objeto.getIdUsuario());
		ps.setString(3, FechaUtils.convertirFecha(objeto.getFechaReserva()));
		ps.setString(4, FechaUtils.convertirFecha(objeto.getFechaDevolucion()));
		ps.setLong(5, objeto.getTipoReserva());
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
	public boolean delete(Reserva objeto) throws Exception {
		return super.deleteById(objeto.getId());
	}

	@Override
	public Reserva update(Reserva objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE reserva SET id_libro=?, id_usuario=?,fecha_reserva=?,fecha_devolucion=?,tipo_reserva=? WHERE id=?");
		ps.setLong(1, objeto.getIdLibro());
		ps.setLong(2, objeto.getIdUsuario());
		ps.setTimestamp(3, Timestamp.valueOf(objeto.getFechaReserva()));
		ps.setTimestamp(4, Timestamp.valueOf(objeto.getFechaDevolucion()));
		ps.setLong(5, objeto.getTipoReserva());
		ps.setLong(6, objeto.getId());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	
}
