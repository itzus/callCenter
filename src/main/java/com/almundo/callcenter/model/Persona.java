package com.almundo.callcenter.model;

/**
 * Representa las personas que hacen parte del sistema, estas posteriormente
 * pueden determinarse como clientes o empleados.
 * 
 * @author stilaguy
 *
 */
public class Persona {

	private String nombre;

	public Persona(String nombre) throws Exception {
		if (nombre == null)
			throw new Exception("El Nombre es obligatorio");
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
