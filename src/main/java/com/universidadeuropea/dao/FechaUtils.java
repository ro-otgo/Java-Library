package com.universidadeuropea.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FechaUtils {
	
	public static String convertirFecha(LocalDate fecha) {
		return fecha.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	public static String convertirFecha(LocalDateTime fecha) {
		return fecha.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
	
	public static LocalDate recuperarFecha(String fecha) {
		return LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	public static LocalDateTime recuperarFechaYHora(String fecha) {
		return LocalDateTime.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
	
	private FechaUtils () {
		
	}

}
