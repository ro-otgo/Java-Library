package repositorios;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.universidadeuropea.dao.UsuarioDao;
import com.universidadeuropea.entities.Usuario;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UsuariosSingleton {
	
	private static UsuariosSingleton usuariosRepo;
	
	private  List<Usuario> usuarios = new ArrayList<>();
	
	private UsuariosSingleton() {
		usuarios = loadUsersDB();
	}
	
	// Devuelve la lista de usuarios guardados en archivo DB
		private List<Usuario> loadUsersDB() {
			UsuarioDao usuarioDao = new UsuarioDao();
			List<com.universidadeuropea.entities.Usuario> usuarios = new ArrayList<>();
			try {
				usuarios = usuarioDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return usuarios;
		}
	
	// Devuelve la lista de usuarios guardados en archivo JSON
	private List<Usuario> loadUsers () throws Exception {
		throw new Exception("Deprecated");
//		List<Usuario> usuarios = new ArrayList<Usuario>(); //objeto vacio donde guardar la informacion
//		try(Reader reader = new FileReader("listaUsuarios.json")){
//			Gson gson = new Gson();
//			Type tipoListaUsuarios = new TypeToken<List<Usuario>>() {}.getType();
//			usuarios = gson.fromJson(reader, tipoListaUsuarios);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return usuarios;
	}
	
	// anade un nuevo usuario al listado guardado en archivo JSON
	public void escribirUsuarios () {
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
		try(FileWriter writer = new FileWriter("listaUsuarios.json")){
			gson.toJson(usuarios, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static UsuariosSingleton getRepoUsuarios() {
		if (usuariosRepo==null) {
			usuariosRepo = new UsuariosSingleton();
		}
		return usuariosRepo;
	}
	
	public Optional<Usuario> findUsuarioById(String usuarioId){
		return usuarios.stream().filter(u->u.getIdUsuario().equals(usuarioId)).findFirst();
	}
	

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void addUsuario(Usuario nuevoUsuario) {
		usuarios.add(nuevoUsuario);
		escribirUsuarios();
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacion");
		alert.setHeaderText("Crear usuario");
		alert.setContentText("Se ha registrado el usuario en la base de datos.");
		alert.showAndWait();
	}

}
