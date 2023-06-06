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

	// anade un nuevo usuario en la base de datos
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
