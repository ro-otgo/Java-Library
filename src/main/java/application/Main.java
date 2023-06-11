package application;
	

import java.util.List;

import com.universidadeuropea.entities.Usuario;

import config.AppConfiguration;
import controladores.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import repositorios.BibliotecariosSingleton;
import repositorios.UsuariosSingleton;


public class Main extends Application {

	@Override
	public void stop() throws Exception {
		System.out.println("Cerrando la aplicacion");
		almacenarFicheros();
		System.out.println("Ficheros almacenados.");
		super.stop();
	}

	private void almacenarFicheros() {
		System.out.println("Almacenar archivos de configuracion de la aplicacion");
		AppConfiguration configuration = AppConfiguration.getConfiguration();
		configuration.almacenarAjustes();
		System.out.println("-----------------------------------------------------");
	}
	
	private void cargarAjustes() {
		System.out.println("Se estan cargando los ajustes de la aplicacion.");
//		AppConfiguration configuration = AppConfiguration.getConfiguration();
		System.out.println("Se han cargado los ajustes.");
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			BibliotecariosSingleton repoBibliotecarios = BibliotecariosSingleton.getRepoUsuarios();
			UsuariosSingleton repoUsuarios = UsuariosSingleton.getRepoUsuarios();
			List<Usuario> usuarios = repoUsuarios.getUsuarios();
			cargarAjustes();
			LoginController.mostrarLogin(primaryStage, repoBibliotecarios.getBibliotecarios(), usuarios);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
				
	public static void main(String[] args) {
		launch(args);
	}
}
