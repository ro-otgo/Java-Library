package repositorios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	public static UsuariosSingleton getRepoUsuarios() {
		if (usuariosRepo==null) {
			usuariosRepo = new UsuariosSingleton();
		}
		return usuariosRepo;
	}
	
	public Optional<Usuario> findUsuarioById(String usuarioId){
		return usuarios.stream().filter(u->u.getNombreUsuario().equals(usuarioId)).findFirst();
	}
	

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	// obsoleto, a�adia nuevo usuario al JSON
	public void addUsuario(Usuario nuevoUsuario) throws Exception {
		throw new Exception("Deprecated");
		/*
		usuarios.add(nuevoUsuario);
		escribirUsuarios();
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacion");
		alert.setHeaderText("Crear usuario");
		alert.setContentText("Se ha registrado el usuario en la base de datos.");
		alert.showAndWait();
		*/
	}

	// a�ade un nuevo usuario en la base de datos
	public void addUsuarioBD (Usuario nuevoUsuario, List<Usuario> usuarios) {
		UsuarioDao usuarioDao = new UsuarioDao();
		try {
			usuarios.add(usuarioDao.save(nuevoUsuario));
	    	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informacion");
			alert.setHeaderText("Crear usuario");
			alert.setContentText("Se ha registrado el usuario en la base de datos.");
			alert.showAndWait();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public boolean validarUsuario(String username, String password) {
		UsuarioDao usuarioDao = new UsuarioDao();
		return usuarioDao.validarUsuario(username,password);
	}
	
	public Usuario recuperarUsuarioPorUsername(String username) {
		UsuarioDao usuarioDao = new UsuarioDao();
		return usuarioDao.buscarPorUsername(username);
	}
}
