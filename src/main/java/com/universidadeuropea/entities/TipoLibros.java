package com.universidadeuropea.entities;

public class TipoLibros {

	private long id;
	
	private String tipo;
	
	private String descripcion;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "TipoLibros [id=" + id + ", tipo=" + tipo + ", descripcion=" + descripcion + "]";
	}
	
}
