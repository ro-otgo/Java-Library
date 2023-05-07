package com.universidadeuropea.entities;

public class Bibliotecario {
	
	private long idBibliotecario;
	
	private String nombreBibliotecario;
	
	private String contrasena;

	public long getIdBibliotecario() {
		return idBibliotecario;
	}

	public void setIdBibliotecario(long idBibliotecario) {
		this.idBibliotecario = idBibliotecario;
	}

	public String getNombreBibliotecario() {
		return nombreBibliotecario;
	}

	public void setNombreBibliotecario(String nombreBibliotecario) {
		this.nombreBibliotecario = nombreBibliotecario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public String toString() {
		return "Bibliotecario [idBibliotecario=" + idBibliotecario + ", nombreBibliotecario=" + nombreBibliotecario
				+ ", contrasena=" + contrasena + "]";
	}

}
