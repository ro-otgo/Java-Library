package com.universidadeuropea.entities;

import java.time.LocalDate;

public class CatalogoLibros {
	
	private long id;
	
	private String isbn;
	
	private String nombreLibro; 

	private LocalDate fechaPublicacion;
	
	private int formato;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}
	
	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public int getFormato() {
		return formato;
	}

	public void setFormato(int formato) {
		this.formato = formato;
	}

	@Override
	public String toString() {
		return "CatalogoLibros [id=" + id + ", isbn=" + isbn + ", nombreLibro=" + nombreLibro + ", fechaPublicacion="
				+ fechaPublicacion + ", formato=" + formato + "]";
	}
	
}
