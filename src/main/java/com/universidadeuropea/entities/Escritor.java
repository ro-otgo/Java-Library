package com.universidadeuropea.entities;

public class Escritor {

	private long idAutor;
	
	private long idLibro;

	public long getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(long idAutor) {
		this.idAutor = idAutor;
	}

	public long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(long idLibro) {
		this.idLibro = idLibro;
	}

	@Override
	public String toString() {
		return "Escritor [idAutor=" + idAutor + ", idLibro=" + idLibro + "]";
	}
	
	
}
