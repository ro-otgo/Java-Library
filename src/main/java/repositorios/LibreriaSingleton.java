package repositorios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.universidadeuropea.dao.LibrosDao;
import com.universidadeuropea.entities.Libros;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Patron sigleton para almacenar todos los libros.
 * Esta clase permite almacenar todos los libros en memoria que estan disponibles para la aplicacion.
 * 
 * @author Rodrigo
 *
 */
public class LibreriaSingleton {
	
	// Unica instancia disponible de la libreria
	private static LibreriaSingleton libreria;

	// Listado de libros
	private List<Libros> libros;
	
	/**
	 * Constructor privado para que no pueda ser instanciado por ninguna otra clase.
	 */
	private LibreriaSingleton() {
		libros = cargarLibrosDB();
	}
	
	// Metodo para cargar libros desde DB
	private List<Libros> cargarLibrosDB() {
		LibrosDao libroDao = new LibrosDao();
		List<com.universidadeuropea.entities.Libros> libros = new ArrayList<>();
		try {
			libros = libroDao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return libros;
	}

	/**
	 * Metodo para obtener la libreria, en caso de que no se haya creado aun llamara al constructor y esta a su vez se encargara de cargar los libros.
	 * @return
	 */
	public static LibreriaSingleton getLibreria() {
		if (libreria==null) {
			libreria = new LibreriaSingleton();
		}
		return libreria;
	}
	
	public List<Libros> getLibrosSinBorrar(){
		return libros.stream().filter(l->!l.getBorrado()).collect(Collectors.toList());
	}

	/**
	 * Se devuelven los libros
	 * @return
	 */
	public List<Libros> getLibros() {
		return libros;
	}
	
	public void addLibro(Libros libro) {
		LibrosDao librosDao = new LibrosDao();
		try {
			libros.add(librosDao.save(libro));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacion");
		alert.setHeaderText("Crear libro");
		alert.setContentText("Se ha registrado el libro en la base de datos.");
		alert.showAndWait();
	}
	
	public void removeLibro(Libros libro) {
		libro.setBorrado(true);
		actualizarLibroDB(libro);
	}
	
	public Optional<Libros> buscarLibroPorId(long idLibro) {
		return libros.stream().filter(l->l.getIdLibro()==idLibro).findFirst();
	}

	public static void actualizarLibroDB (Libros libro) {
		LibrosDao librosDao = new LibrosDao();
		try {
			librosDao.update(libro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void actualizarEstadoLibro (Libros libro) {
		LibrosDao librosDao = new LibrosDao();
		try {
			librosDao.actualizarEstadoLibro(libro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void devolverLibroDB(Long id) {
		LibrosDao librosDao = new LibrosDao ();
		librosDao.devolverLibro(id);
	}
	

}
