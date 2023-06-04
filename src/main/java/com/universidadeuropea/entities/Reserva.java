package com.universidadeuropea.entities;

import java.time.LocalDateTime;

public class Reserva {

	private long idReserva;
	
	private long idLibro;
	
	private long idUsuario;
	
	private boolean activa;
	
	
	
	public Reserva() {
		super();
	}

	public Reserva(long idLibro, long idUsuario, boolean activa, LocalDateTime fechaReserva) {
		super();
		this.idLibro = idLibro;
		this.idUsuario = idUsuario;
		this.activa = activa;
		this.fechaReserva = fechaReserva;
	}

	private LocalDateTime fechaReserva;

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

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean getActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDateTime fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", idLibro=" + idLibro + ", idUsuario=" + idUsuario + ", activa="
				+ activa + ", fechaReserva=" + fechaReserva + "]";
	}

}
