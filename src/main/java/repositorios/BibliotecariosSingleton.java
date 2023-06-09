package repositorios;

import java.util.ArrayList;
import java.util.List;

import com.universidadeuropea.dao.BibliotecarioDao;
import com.universidadeuropea.entities.Bibliotecario;

public class BibliotecariosSingleton {
	
	private static BibliotecariosSingleton usuariosRepo;
	
	private List<Bibliotecario> bibliotecarios = new ArrayList<>();
	
	public boolean validarBibliotecario (String username,String password) {
		BibliotecarioDao bibliotecarioDao = new BibliotecarioDao();
		return bibliotecarioDao.validarUsuario(username,password);
	}
	
	private BibliotecariosSingleton() {
		bibliotecarios = loadUsersDB();
	}
	
	private List<Bibliotecario> loadUsersDB() {
		BibliotecarioDao bibliotecarioDao = new BibliotecarioDao();
		List<com.universidadeuropea.entities.Bibliotecario> b = new ArrayList<>();
		try {
			b = bibliotecarioDao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public static BibliotecariosSingleton getRepoUsuarios() {
		if (usuariosRepo==null) {
			usuariosRepo = new BibliotecariosSingleton();
		}
		return usuariosRepo;
	}
	
	public List<Bibliotecario> getBibliotecarios(){
		return bibliotecarios;
	}
	
}
