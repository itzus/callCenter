package com.almundo.callcenter.model;

import java.io.Serializable;

import com.almundo.callcenter.model.cons.Cargo;

public class Empleado extends Persona implements Serializable {

	private static final long serialVersionUID = -4628558970953536682L;
	private Cargo cargo;
	private Llamada llamada;

	public Empleado(String nombre, Cargo cargo) throws Exception {
		super(nombre);
		if (cargo == null)
			throw new Exception("El cargo es obligatorio");
		this.cargo = cargo;
	}

	public boolean isOcupado() {
		return llamada != null;
	}

	public void colgarLlamada() {
		llamada = null;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Llamada getLlamada() {
		return llamada;
	}

	public void setLlamada(Llamada llamada) {
		this.llamada = llamada;
	}

}
