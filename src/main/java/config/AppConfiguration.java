package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuracion de la aplicacion.
 * Permite almacenar ficheros de carga
 * @author Rodrigo
 *
 */
public class AppConfiguration {
	
	private static final String PROPERTIES_PATH = "src/main/resources/config/app.properties";
	private static final String TIEMPO_RESERVA_KEY = "tiempo_reserva";
	
	private static AppConfiguration configuration;

	private int tiempoReserva = 15;
	
	private AppConfiguration(){
		try {
			FileInputStream in = new FileInputStream(PROPERTIES_PATH);
			Properties applicationProps = new Properties();
	
			applicationProps.load(in);
			in.close();
			tiempoReserva = Integer.valueOf(applicationProps.getProperty(TIEMPO_RESERVA_KEY));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static AppConfiguration getConfiguration(){
		if (configuration == null) {
			configuration = new AppConfiguration();
		}
		return configuration;
	}


	public int getTiempoReserva() {
		return tiempoReserva;
	}

	public void setTiempoReserva(int tiempoReserva) {
		this.tiempoReserva = tiempoReserva;
	}

	public void almacenarAjustes() {
		try {
			FileOutputStream out = new FileOutputStream(PROPERTIES_PATH);
			Properties properties = new Properties();
			properties.put(TIEMPO_RESERVA_KEY, String.valueOf(tiempoReserva));
			properties.store(out, null);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
