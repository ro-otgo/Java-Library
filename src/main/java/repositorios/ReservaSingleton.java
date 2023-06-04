package repositorios;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;


import com.universidadeuropea.dao.ReservaDao;
import com.universidadeuropea.dao.UsuarioDao;
import com.universidadeuropea.entities.Libros;
import com.universidadeuropea.entities.Reserva;
import com.universidadeuropea.entities.Usuario;

import config.AppConfiguration;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Repositorio para almacenar las reservas.
 * @author Rodrigo
 *
 */
public class ReservaSingleton {

	// Unica instancia disponible
	private static ReservaSingleton reservaSingleton;

	// Listado de reservas
	private List<Reserva> reservas;
	
	private AppConfiguration appConfiguracion;

	/**
	 * Constructor privado para que no pueda ser instanciado por ninguna otra clase.
	 */
	private ReservaSingleton() {
		reservas = cargarReservas();
		appConfiguracion = AppConfiguration.getConfiguration();
	}

	/**
	 * Metodo para cargar las reservas.
	 * @return
	 */
	
	private List<Reserva> cargarReservas() {
		ReservaDao reservaDao = new ReservaDao();
		List<com.universidadeuropea.entities.Reserva> reservas = new ArrayList<>();
		try {
			reservas = reservaDao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		List<Reserva> reservas = new ArrayList(); // objeto vacio donde guardar la informaci�n
		try (Reader reader = new FileReader("reservas.json")) {
			Gson gson = new Gson();
			Type tipoReservas = new TypeToken<List<Reserva>>() {
			}.getType();
			reservas = gson.fromJson(reader, tipoReservas);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
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

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public void crearReserva(Libros libro, Usuario usuario) {
		Reserva nuevareserva = new Reserva();
		nuevareserva.setIdUsuario(usuario.getNombreUsuario());
		nuevareserva.setIdLibro(libro.getIdLibro());
		nuevareserva.setActiva(true);
		nuevareserva.setFechaReserva(LocalDate.now());
		
		ReservaDao reservaDao = new ReservaDao();
		try {
			reservas.add(reservaDao.save(nuevareserva));
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Informacion");
    		alert.setHeaderText("Actualizacion reserva");
    		alert.setContentText("Se ha realizado la reserva del libro.");
			alert.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		libro.setReservado(true);
		LibreriaSingleton.actualizarLibroDB(libro);
	}
	
	public void actualizarReservaBD (Reserva reserva, long idLibro) {
		ReservaDao reservaDao = new ReservaDao();
		try {
			if(!reserva.getActiva())
				LibreriaSingleton.devolverLibroDB(idLibro);
			reservaDao.update(reserva);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean usuarioTieneReservaActivaLibro(Usuario usuario, Libros libro) {
		List<Reserva> reservasLibrosUsuario = reservas.stream().filter(r->usuario.getNombreUsuario().equals(r.getIdUsuario()) && libro.getIdLibro() == r.getIdLibro()).collect(Collectors.toList());
		for (Reserva reserva: reservasLibrosUsuario) {
			if (reserva.getActiva()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Obtener reservas activas
	 * @return
	 */
	public List<Reserva> getReservasActivas(){
		return reservas.stream().filter(Reserva::getActiva).collect(Collectors.toList());
	}
	
	/**
	 * Obtener reserva activa por libro, deberia existir solamente una.
	 * @param libro
	 * @return
	 */
	public List<Reserva> buscarReservaActivaPorLibro(Libros libro) {
		return reservas.stream().filter(r -> libro.getIdLibro() == r.getIdLibro() && r.getActiva()).collect(Collectors.toList());
	}
	
	/**
	 * Buscar reservas asociadas a un libro.
	 * @param libro
	 * @return
	 */
	public List<Reserva> buscarReservaPorLibro(Libros libro) {
		return reservas.stream().filter(r -> libro.getIdLibro() == r.getIdLibro()).collect(Collectors.toList());
	}
	
	/**
	 * Buscar reservas activas de un usuario
	 * @param usuario
	 * @return
	 */
	public List<Reserva> buscarReservaActivaPorUsuario(Usuario usuario) {
		return reservas.stream().filter(r -> usuario.getNombreUsuario().equals(r.getIdUsuario()) && r.getActiva()).collect(Collectors.toList());
	}
	
	/**
	 * Buscar todas las reservas de un usuario
	 * @param usuario
	 * @return
	 */
	public List<Reserva> buscarReservaPorUsuario(Usuario usuario) {
		return reservas.stream().filter(r -> usuario.getNombreUsuario().equals(r.getIdUsuario())).collect(Collectors.toList());
	}
	
	/**
	 * Buscar la reserva activa de un usuario y de un libro.
	 * Trata de averiguar si un usuario y un libro estan asociados mediatne una reserva activa.
	 * @param usuario
	 * @param libro
	 * @return
	 */
	public Optional<Reserva> buscarReservaActivaPorUsuarioLibro(Usuario usuario, Libros libro) {
		return reservas.stream().filter(r -> usuario.getNombreUsuario().equals(r.getIdUsuario()) && r.getActiva() && libro.getIdLibro() == r.getIdLibro()).findFirst();
	}
	
	public boolean libroReservado(Libros libro) {
		return reservas.stream().anyMatch(r->libro.getIdLibro()==r.getIdLibro() && r.getActiva());
	}

}
