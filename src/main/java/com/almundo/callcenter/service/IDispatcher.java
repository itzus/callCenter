package com.almundo.callcenter.service;

import com.almundo.callcenter.model.Llamada;

/**
 * Esta clase se encarga de administrar las peticiones de llamadas para el call
 * center.
 * 
 * @author stilaguy
 *
 */
public interface IDispatcher {

	/***
	 * Permite tomar una llamada realizada al call center y gestionarla.
	 * 
	 * @param llamada
	 * @throws Exception en caso de que no se pueda realizar la gestion de la
	 *                   llamada de manera correcta.
	 */
	public void dispatchCall(Llamada llamada) throws Exception;

}
