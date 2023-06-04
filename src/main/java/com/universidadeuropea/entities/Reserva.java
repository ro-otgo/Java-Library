package com.universidadeuropea.entities;

import java.time.LocalDate;


public class Reserva {

	private long idReserva;
	
	private long idLibro;
	
	private String idUsuario;
	
	private boolean activa;
	
	private LocalDate fechaReserva;

	public long getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(long idReserva) {
		this.idReserva = idReserva;
	}

	public long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(long idLibro) {
		this.idLibro = idLibro;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean getActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", idLibro=" + idLibro + ", idUsuario=" + idUsuario + ", activa="
				+ activa + ", fechaReserva=" + fechaReserva + "]";
	}

}
