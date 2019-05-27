package com.almundo.callcenter.model;

public class Persona {
	
	private String nombre;
	
	public Persona(String nombre) throws Exception {
		if (nombre == null)
			throw new Exception("El Nombre es obligatorio");
		this.nombre=nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
