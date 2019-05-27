package com.almundo.callcenter.service;

import com.almundo.callcenter.model.Llamada;

public interface IDispatcher {
	
	public void dispatchCall(Llamada llamada) throws Exception ;

}
