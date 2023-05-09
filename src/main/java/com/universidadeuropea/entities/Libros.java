package com.universidadeuropea.entities;

public class Libros {
	
	private long idLibro;
	
	private String titulo;
	
	private long isbn;
	
	private String autor;
	
	private boolean borrado;
	
	private boolean reservado;

	public long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(long idLibro) {
		this.idLibro = idLibro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public boolean getBorrado() {
		return borrado;
	}

	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}

	public boolean getReservado() {
		return reservado;
	}

	public void setReservado(boolean reservado) {
		this.reservado = reservado;
	}

	@Override
	public String toString() {
		return "Libros [idLibro=" + idLibro + ", titulo=" + titulo + ", isbn=" + isbn + ", autor=" + autor
				+ ", borrado=" + borrado + ", reservado=" + reservado + "]";
	}

	
}
