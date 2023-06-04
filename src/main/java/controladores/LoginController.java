package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Sample Skeleton for 'app.fxml' Controller Class
 */

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.universidadeuropea.dao.BibliotecarioDao;
import com.universidadeuropea.entities.Bibliotecario;
import com.universidadeuropea.entities.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import repositorios.BibliotecariosSingleton;
import repositorios.SesionSingleton;
import repositorios.UsuariosSingleton;

public class LoginController {
	
	public static void mostrarLogin(Stage primaryStage, List<Bibliotecario> list, List<Usuario> usuarios) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/vistas/LoginView.fxml"));
		LoginController mainController = new LoginController();
		fxmlLoader.setController(mainController);
		mainController.setBibliotecarios(list);
		mainController.setUsuarios(usuarios);

		Parent root = fxmlLoader.load();
		
		Scene scene = new Scene(root,873,600);
		scene.getStylesheets().add(LoginController.class.getResource("/vistas/styles/application.css").toExternalForm());

		primaryStage.setTitle("Java Library Management");
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("/img/logo.jpg"));
		primaryStage.show();
	}

	@FXML
	private JFXTextField username;
	@FXML
	private JFXPasswordField password;
	@FXML
	private Label wrongLogIn;	
	
	    
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="detallesLibroButton"
	private JFXButton detallesLibroButton; // Value injected by FXMLLoader
	
	@FXML
    private JFXButton signInButton;
	
    @FXML // fx:id="signUpButton"
    private JFXButton signUpButton; // Value injected by FXMLLoader
	
	private List<Bibliotecario> bibliotecarios;
	
	private List<Usuario> usuarios;
	

    @FXML
    void signUp(ActionEvent event) {
		try {
			Node source = (Node) event.getSource();
	    	Scene scene = (Scene) source.getScene();
	    	CrearUsuarioController.mostrarVistaCrearUsuario(scene, usuarios, bibliotecarios);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void signIn(ActionEvent event) throws IOException {
		Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	
        //Campo vacio:
    	if(username.getText().isEmpty() || password.getText().isEmpty()) 
            wrongLogIn.setText("Porfavor introduce tus datos.");
    	// Acceso como bibliotecario:
    	else if(validarBibliotecarioDB(username.getText(),password.getText())) {
    		wrongLogIn.setText("Acceso bibliotecario!");
            mostrarVistaPantallaBibliotecario(stage,username.getText().toString());
            return;
    	}
    	//Acceso como usuario:
    	Usuario user = validarUsuario(username.getText(),password.getText());
    	if(user != null) {
    		wrongLogIn.setText("Acceso usuario!");
    		SesionSingleton.getSesionSingleton().actualizarUsuario(user);
    		mostrarVistaPantallaUsuario(stage, user);
            return;
    	}
    	else {
        	// Datos incorrectos:
    		wrongLogIn.setText("Usuario o contrasena incorrectos!");
    	}
    }
            
          	
    // Valida si id usuario y contrasena coinciden con un bibliotecario
    private boolean validarBibliotecario(String username, String password) throws Exception {
    	throw new Exception("Deprecated");
    	/*
    	String usuarioPwd = bibliotecarios.getContrasena();
    	if (usuarioPwd!=null) {
    		return usuarioPwd.equals(password);
    	}
    	return false;
    	*/
    }
    
    // Valida si id usuario y contrasena coinciden con un bibliotecario de la DB
    private boolean validarBibliotecarioDB(String username, String password) {
    	System.out.println("VALIDANDO POR BBDD-Update");
    	return BibliotecariosSingleton.validarBibliotecario(username,password);
    }
    
    // Valida si id usuario y contrasena coinciden con un usuario
    private Usuario validarUsuario(String username, String password) {
    	System.out.println("VALIDANDO POR BBDD");
    	if (UsuariosSingleton.getRepoUsuarios().validarUsuario(username,password)) {
    		// Recuperar usuario
    		return UsuariosSingleton.getRepoUsuarios().recuperarUsuarioPorUsername(username);
    	}
		System.out.println("VALIDADO POR BBDD: NO ENCONTRADO");
    	return null;
    }
    
	private void mostrarVistaPantallaBibliotecario(Stage stage, String nombreBibliotecario) throws IOException{
		BibliotecarioController.mostrarVistaPantallaBibliotecario(nombreBibliotecario);
    	stage.close();
	}
	
	private void mostrarVistaPantallaUsuario(Stage stage, Usuario usuario) throws IOException{
		UsuarioController.mostrarVistaPantallaUsuario(usuario);
    	stage.close();
	}
	
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert detallesLibroButton != null
				: "fx:id=\"detallesLibroButton\" was not injected: check your FXML file 'app.fxml'.";

	}
	
	public void setBibliotecarios(List<Bibliotecario> loadbibliotecarios) {
		this.bibliotecarios = loadbibliotecarios;
	}
	
	public void setUsuarios(List<Usuario> loadUsers) {
		this.usuarios = loadUsers;
	}
	
}
