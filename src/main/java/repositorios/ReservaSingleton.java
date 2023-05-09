package repositorios;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.universidadeuropea.entities.Libros;
import com.universidadeuropea.entities.Usuario;

import config.AppConfiguration;
import modelos.Reserva;

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
		List<Reserva> reservas = new ArrayList(); // objeto vacio donde guardar la informaci�n
		try (Reader reader = new FileReader("reservas.json")) {
			Gson gson = new Gson();
			Type tipoReservas = new TypeToken<List<Reserva>>() {
			}.getType();
			reservas = gson.fromJson(reader, tipoReservas);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		Reserva reserva = new Reserva(usuario.getNombreUsuario(), libro.getIdLibro(),appConfiguracion.getTiempoReserva());
		reservas.add(reserva);
		libro.setReservado(true);
		LibreriaSingleton.actualizarLibroDB(libro);
	}

	public void eliminarReserva(Long idReserva) {
		ListIterator<Reserva> listIterator = reservas.listIterator();
		while(listIterator.hasNext()) {
			Reserva reserva = listIterator.next();
			if(reserva.getId() == idReserva) {
				listIterator.remove();
				break;
			}
		}
	}
	
	public boolean usuarioTieneReservaActivaLibro(Usuario usuario, Libros libro) {
		List<Reserva> reservasLibrosUsuario = reservas.stream().filter(r->usuario.getNombreUsuario().equals(r.getIdUsuario()) && libro.getIdLibro() == r.getIdLibro()).collect(Collectors.toList());
		for (Reserva reserva: reservasLibrosUsuario) {
			if (reserva.isActive()) {
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
		return reservas.stream().filter(Reserva::isActive).collect(Collectors.toList());
	}
	
	/**
	 * Obtener reserva activa por libro, deberia existir solamente una.
	 * @param libro
	 * @return
	 */
	public List<Reserva> buscarReservaActivaPorLibro(Libros libro) {
		return reservas.stream().filter(r -> libro.getIdLibro() == r.getIdLibro() && r.isActive()).collect(Collectors.toList());
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
		return reservas.stream().filter(r -> usuario.getNombreUsuario().equals(r.getIdUsuario()) && r.isActive()).collect(Collectors.toList());
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
		return reservas.stream().filter(r -> usuario.getNombreUsuario().equals(r.getIdUsuario()) && r.isActive() && libro.getIdLibro() == r.getIdLibro()).findFirst();
	}
	
	public boolean libroReservado(Libros libro) {
		return reservas.stream().anyMatch(r->libro.getIdLibro()==r.getIdLibro() && r.isActive());
	}

}
