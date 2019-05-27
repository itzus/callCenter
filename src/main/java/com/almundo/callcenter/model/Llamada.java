package com.almundo.callcenter.model;

import com.almundo.callcenter.model.cons.EstadoLlamada;

/**
 * Permite controlar la operacion de una llamada quien la realiza quien la
 * atiende y el estado actual de la misma.
 * 
 * @author stilaguy
 *
 */
public class Llamada {

	private Empleado receptor;
	private Persona emisor;
	private EstadoLlamada estado;

	/**
	 * La llamada debe crearse con un emisor, se coloca la misma en un estado
	 * inicial.
	 * 
	 * @param emisor
	 * @throws Exception en caso de que se intente crear una llamada con un emisor
	 *                   que no existe.
	 */
	public Llamada(Persona emisor) throws Exception {
		if (emisor == null)
			throw new Exception("No se puede generar la llamada sin identificar quien la genero");
		this.emisor = emisor;
		estado = EstadoLlamada.INICIALIZADA;
	}

	/**
	 * Una vez una llamada tiene un receptor que atiende al cliente que emite la
	 * llamada la misma cambia a en estado de ejecucion. Si el receptor no existe se
	 * determina que la llamada finalizo.
	 * 
	 * @param receptor
	 */
	public void setReceptor(Empleado receptor) {
		this.receptor = receptor;
		estado = receptor != null ? EstadoLlamada.EN_EJECUCION : EstadoLlamada.FINALIZADA;
	}

	public Empleado getReceptor() {
		return receptor;
	}

	public Persona getEmisor() {
		return emisor;
	}

	public void setEmisor(Persona emisor) {
		this.emisor = emisor;
	}

	public EstadoLlamada getEstado() {
		return estado;
	}

}
