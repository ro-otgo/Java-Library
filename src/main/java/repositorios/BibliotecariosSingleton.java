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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.universidadeuropea.dao.BibliotecarioDao;
import com.universidadeuropea.entities.Bibliotecario;

public class BibliotecariosSingleton {
	
	private static BibliotecariosSingleton usuariosRepo;
	
	private List<Bibliotecario> bibliotecarios = new ArrayList();
	
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

	// Devuelve el Mapa de bibliotecarios guardados en archivo JSON
	private Map<String, String> loadUsers () throws Exception {
		throw new Exception("Deprecated");
		/*
		Map<String, String> usuarios = new HashMap<String, String>(); //objeto vacio donde guardar la informacion
		try(Reader reader = new FileReader("listaBibliotecarios.json")){
			Gson gson = new Gson();
			Type tipoListaUsuarios = new TypeToken<Map<String, String>>() {}.getType();
			usuarios = gson.fromJson(reader, tipoListaUsuarios);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usuarios;
		*/
	}
	
	/* obsoleto y no se utilizaba
	// anade un nuevo bibliotecario al listado guardado en archivo JSON
	public void escribirUsuarios () {
		// guardamos en la variable usuarios la informacion del json
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
		try(FileWriter writer = new FileWriter("listaBibliotecarios.json")){
			gson.toJson(usuarios, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public static BibliotecariosSingleton getRepoUsuarios() {
		if (usuariosRepo==null) {
			usuariosRepo = new BibliotecariosSingleton();
		}
		return usuariosRepo;
	}
	
	public List<Bibliotecario> getBibliotecarios(){
		return bibliotecarios;
	}
	
	/*obsoleto y no se utilizaba
	public void addUsuario(Map<String, String> nuevoUsuario) {
		usuarios.putAll(nuevoUsuario);
	}
	*/
}
