package com.almundo.callcenter.model;

import com.almundo.callcenter.model.cons.EstadoLlamada;

public class Llamada {

	private Empleado receptor;
	private Persona emisor;
	private EstadoLlamada estado;

	public Llamada(Persona emisor) throws Exception {
		if (emisor == null)
			throw new Exception("No se puede generar la llamada sin identificar quien la genero");
		this.emisor = emisor;
		estado=EstadoLlamada.INICIALIZADA;
	}

	public void setReceptor(Empleado receptor) {
		this.receptor = receptor;
		estado=receptor!=null ? EstadoLlamada.EN_EJECUCION: EstadoLlamada.FINALIZADA;
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
