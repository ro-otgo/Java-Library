package repositorios;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.universidadeuropea.dao.LibrosDao;
import com.universidadeuropea.dao.ReservaDao;
import com.universidadeuropea.entities.Libros;
import com.universidadeuropea.entities.Reserva;
import com.universidadeuropea.entities.Usuario;

import config.AppConfiguration;

/**
 * Repositorio para almacenar las reservas.
 * 
 * @author Rodrigo
 *
 */
public class ReservaSingleton {

	// Unica instancia disponible
	private static ReservaSingleton reservaSingleton;

	private AppConfiguration appConfiguracion;

	/**
	 * Constructor privado para que no pueda ser instanciado por ninguna otra clase.
	 */
	private ReservaSingleton() {
		try {
			appConfiguracion = AppConfiguration.getConfiguration();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	/**
	 * Metodo para cargar las reservas.
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<Reserva> cargarReservas() throws Exception {
		ReservaDao reservaDao = new ReservaDao();
		List<Reserva> reservas = reservaDao.getAll();
		return reservas;
	}

	/**
	 * Metodo para obtener la libreria, en caso de que no se haya creado aun llamara
	 * al constructor y esta a su vez se encargara de cargar los libros.
	 * 
	 * @return
	 */
	public static ReservaSingleton getReservaSingleton() {
		if (reservaSingleton == null) {
			reservaSingleton = new ReservaSingleton();
		}
		return reservaSingleton;
	}

	
	public void crearReserva(Libros libro, Usuario usuario) {
		Reserva reserva = new Reserva(libro.getIdLibro(),usuario.getIdUsuario(), true, LocalDateTime.now());
		ReservaDao reservaDao = new ReservaDao();
		try {
			reservaDao.save(reserva);
			libro.setReservado(true);
			LibreriaSingleton.actualizarLibroDB(libro);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void eliminarReserva(Long idReserva) {
		ReservaDao daoReserva = new ReservaDao();
		try {
			daoReserva.deleteById(idReserva);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean usuarioTieneReservaActivaLibro(Usuario usuario, Libros libro) {
		Optional<Reserva> reserva = buscarReservaActivaPorUsuarioLibro(usuario,libro);
		if(reserva.isPresent()) {
			return true;
		}
		return false;
	}

	/**
	 * Obtener reservas activas
	 * 
	 * @return
	 */
	public List<Reserva> getReservasActivas() {
		ReservaDao reservaDao = new ReservaDao();
		List<Reserva> reservas = new ArrayList<>();
		try {
			reservas.addAll(reservaDao.buscarReservasActiva());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservas;
	}

	/**
	 * Obtener reserva activa por libro, deberia existir solamente una.
	 * 
	 * @param libro
	 * @return
	 */
	public List<Reserva> buscarReservaActivaPorLibro(Libros libro) {
		ReservaDao reservaDao = new ReservaDao();
		List<Reserva> reservas = new ArrayList<>();
		try {
			reservas.addAll(reservaDao.buscarReservaActivaPorLibro(libro));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservas;
	}

	/**
	 * Buscar reservas asociadas a un libro.
	 * 
	 * @param libro
	 * @return
	 */
	public List<Reserva> buscarReservaPorLibro(Libros libro) {
		ReservaDao reservaDao = new ReservaDao();
		List<Reserva> reservas = new ArrayList<>();
		try {
			reservas.addAll(reservaDao.buscarReservaPorLibro(libro));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservas;
	}

	/**
	 * Buscar reservas activas de un usuario
	 * 
	 * @param usuario
	 * @return
	 */
	public List<Reserva> buscarReservaActivaPorUsuario(Usuario usuario) {
		ReservaDao reservaDao = new ReservaDao();
		List<Reserva> reservas = new ArrayList<>();
		try {
			reservas.addAll(reservaDao.buscarReservasUsuarioActiva(usuario));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservas;
	}

	/**
	 * Buscar todas las reservas de un usuario
	 * 
	 * @param usuario
	 * @return
	 */
	public List<Reserva> buscarReservaPorUsuario(Usuario usuario) {
		ReservaDao reservaDao = new ReservaDao();
		List<Reserva> reservas = new ArrayList<>();
		try {
			reservas.addAll(reservaDao.buscarReservasUsuario(usuario));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservas;
	}

	/**
	 * Buscar la reserva activa de un usuario y de un libro. Trata de averiguar si
	 * un usuario y un libro estan asociados mediatne una reserva activa.
	 * 
	 * @param usuario
	 * @param libro
	 * @return
	 */
	public Optional<Reserva> buscarReservaActivaPorUsuarioLibro(Usuario usuario, Libros libro) {
		ReservaDao reservaDao = new ReservaDao();
		try {
			return reservaDao.buscarReservaActivaPorUsuarioLibro(usuario,libro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean libroReservado(Libros libro) {
		// TODO
		LibrosDao libroDao = new LibrosDao();
		try {
			return libroDao.estaReservado(libro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public List<Reserva> getReservas() throws Exception {
		ReservaDao reservaDao = new ReservaDao();
		List<Reserva> reservas = reservaDao.getAll();
		return reservas;
	}

}
