package com.universidadeuropea.entities;

import java.time.LocalDateTime;

public class Usuario {
	
    private long id;
    
    private String idUsuario;
    
    private String nombre;
    
    private String apellido1;
    
    private String contrasena;
    
    private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido1=" + apellido1
				+ ", contrasena=" + contrasena + ", email=" + email + "]";
	}

    
}
