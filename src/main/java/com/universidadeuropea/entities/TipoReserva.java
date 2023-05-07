package com.universidadeuropea.entities;

public class TipoReserva {
	
	private long id;
	
	private String tipo;
	
	private int plazo;
	
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

	public int getPlazo() {
		return plazo;
	}

	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "TipoReserva [id=" + id + ", tipo=" + tipo + ", plazo=" + plazo + ", descripcion=" + descripcion + "]";
	}

}
