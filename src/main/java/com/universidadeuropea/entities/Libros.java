package com.universidadeuropea.entities;

public class Libros {
	
	private long id;
	
	private long idLibro;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(long idLibro) {
		this.idLibro = idLibro;
	}

	@Override
	public String toString() {
		return "Libros [id=" + id + ", idLibro=" + idLibro + "]";
	}
	
}
