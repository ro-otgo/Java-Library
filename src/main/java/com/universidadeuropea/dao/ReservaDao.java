package com.universidadeuropea.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.universidadeuropea.entities.Libros;
import com.universidadeuropea.entities.Reserva;
import com.universidadeuropea.entities.Usuario;
import com.universidadeuropea.idao.IReservaDao;

public class ReservaDao extends Dao<Reserva, Long> implements IReservaDao {

	@Override
	protected String selectById() {
		return "SELECT * FROM reserva WHERE id_reserva =?";
	}

	@Override
	protected String deleteById() {
		return "DELETE FROM reserva WHERE id_reserva =?";
	}
	
	@Override
	protected String getAllQuery() {
		return "SELECT * FROM reserva";
	}
	
	@Override
	protected Reserva mapear(ResultSet rs) throws SQLException {
		Reserva reserva = new Reserva();
		reserva.setIdReserva(rs.getLong("id_reserva"));
		reserva.setIdLibro(rs.getLong("id_libro"));
		reserva.setIdUsuario(rs.getLong("id_usuario"));
		reserva.setActiva(rs.getBoolean("activa"));
		reserva.setFechaReserva(FechaUtils.recuperarFechaYHora(rs.getString("fecha_reserva")));
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
		PreparedStatement ps =getConnection().prepareStatement("INSERT INTO reserva (id_libro, id_usuario,activa,fecha_reserva) VALUES(?,?,?,?)",  Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, objeto.getIdLibro());
		ps.setLong(2, objeto.getIdUsuario());
		ps.setBoolean(3, objeto.getActiva());
		ps.setString(4, FechaUtils.convertirFecha(objeto.getFechaReserva()));
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		long pk =0; 
		while (rs.next()) {
			pk = rs.getLong(1);
		}
		objeto.setIdReserva(pk);
		cerrarConexion();
		return objeto;
	}

	@Override
	public boolean delete(Reserva objeto) throws Exception {
		return super.deleteById(objeto.getIdReserva());
	}

	@Override
	public Reserva update(Reserva objeto) throws Exception {
		obtenerConexionDB();
		PreparedStatement ps =getConnection().prepareStatement("UPDATE reserva SET id_libro=?, id_usuario=?, activa =?, fecha_reserva=? WHERE id_reserva=?");
		ps.setLong(1, objeto.getIdLibro());
		ps.setLong(2, objeto.getIdUsuario());
		ps.setBoolean(3, objeto.getActiva());
		ps.setString(4, FechaUtils.convertirFecha(objeto.getFechaReserva()));
		ps.setLong(5, objeto.getIdReserva());
		ps.executeUpdate();
		cerrarConexion();
		return objeto;
	}
	
	public Optional<Reserva> buscarReservaActivaPorUsuarioLibro(Usuario usuario, Libros libro) throws Exception {
		obtenerConexionDB();
		Reserva reserva  = null;
		
		PreparedStatement ps  =getConnection().prepareStatement("SELECT * FROM reserva where id_usuario=? and id_libro=? and activa=1");
		ps.setLong(1, usuario.getIdUsuario());
		ps.setLong(2, libro.getIdLibro());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			reserva = mapear(rs);
			// Solo deberia existir una
			break;
		}
		cerrarConexion();
		Optional<Reserva> optional = Optional.ofNullable(reserva);
		return optional;
	}
	
	public List<Reserva> buscarReservasUsuario(Usuario usuario) throws Exception {
		obtenerConexionDB();
		List<Reserva> reservas = new ArrayList<>();
		PreparedStatement ps  =getConnection().prepareStatement("SELECT * FROM reserva where id_usuario=?");
		ps.setLong(1, usuario.getIdUsuario());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Reserva reserva = mapear(rs);
			reservas.add(reserva);
		}
		cerrarConexion();
		return reservas;
	}
	
	public List<Reserva> buscarReservasUsuarioActiva(Usuario usuario) throws Exception {
		obtenerConexionDB();
		List<Reserva> reservas = new ArrayList<>();
		PreparedStatement ps  =getConnection().prepareStatement("SELECT * FROM reserva where id_usuario=? and activa=1");
		ps.setLong(1, usuario.getIdUsuario());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Reserva reserva = mapear(rs);
			reservas.add(reserva);
		}
		cerrarConexion();
		return reservas;
	}
	
	public List<Reserva> buscarReservaPorLibro(Libros libro) throws Exception {
		obtenerConexionDB();
		List<Reserva> reservas = new ArrayList<>();
		PreparedStatement ps  =getConnection().prepareStatement("SELECT * FROM reserva where id_libro=?");
		ps.setLong(1, libro.getIdLibro());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Reserva reserva = mapear(rs);
			reservas.add(reserva);
			break;
		}
		cerrarConexion();
		return reservas;
	}
	
	
	public List<Reserva> buscarReservaActivaPorLibro(Libros libro) throws Exception {
		obtenerConexionDB();
		List<Reserva> reservas = new ArrayList<>();
		PreparedStatement ps  =getConnection().prepareStatement("SELECT * FROM reserva where id_libro=? and activa=1");
		ps.setLong(1, libro.getIdLibro());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Reserva reserva = mapear(rs);
			reservas.add(reserva);
			break;
		}
		cerrarConexion();
		return reservas;
	}
	
	public List<Reserva> buscarReservasActiva() throws Exception {
		obtenerConexionDB();
		List<Reserva> reservas = new ArrayList<>();
		PreparedStatement ps  =getConnection().prepareStatement("SELECT * FROM reserva where activa=1");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Reserva reserva = mapear(rs);
			reservas.add(reserva);
			break;
		}
		cerrarConexion();
		return reservas;
	}
	

	
	
	
}
