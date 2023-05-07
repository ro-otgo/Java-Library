package com.universidadeuropea.entities;

import java.time.LocalDateTime;

public class Reserva {

	private long id;
	
	private long idLibro;
	
	private long idUsuario;
	
	private LocalDateTime fechaReserva;
	
	private LocalDateTime fechaDevolucion;
	
	private long tipoReserva;

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

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDateTime fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public LocalDateTime getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public long getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(long tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", idLibro=" + idLibro + ", idUsuario=" + idUsuario + ", fechaReserva="
				+ fechaReserva + ", fechaDevolucion=" + fechaDevolucion + ", tipoReserva=" + tipoReserva + "]";
	}

}
