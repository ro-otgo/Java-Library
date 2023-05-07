package com.universidadeuropea.entities;

public class Editor {
	
	private long idEditorial;
	
	private long idLibro;

	public long getIdEditorial() {
		return idEditorial;
	}

	public void setIdEditorial(long idEditorial) {
		this.idEditorial = idEditorial;
	}

	public long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(long idLibro) {
		this.idLibro = idLibro;
	}

	@Override
	public String toString() {
		return "Editor [idEditorial=" + idEditorial + ", idLibro=" + idLibro + "]";
	}

}
