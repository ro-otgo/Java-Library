package com.universidadeuropea.entities;

public class TipoUsuario {
	
    private long id;
    
    private String rol;
    
    private String descripcion;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return "TipoUsuario [id=" + id + ", rol=" + rol + ", descripcion=" + descripcion + "]";
	}
    
}
