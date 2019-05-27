package com.almundo.callcenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.model.Empleado;
import com.almundo.callcenter.model.Llamada;
import com.almundo.callcenter.model.cons.Cargo;
import com.almundo.callcenter.model.cons.EstadoLlamada;

@Service
public class Dispatcher implements IDispatcher {

	private static List<Empleado> empleados;

	private static final int MAX_JERARQUIA = 3;
	private static final String MSG_NO_DISPONIBLE = " no existe personas que puedan atender su llamada. por favor marque mas tarde ";
	private static final String MSG_NO_ACTIVA = " La llamda no se encuentra activa";
	
	static {
		try {
			empleados = new ArrayList<Empleado>();
			empleados.add(new Empleado("Miguel Martinez", Cargo.DIRECTOR));
			empleados.add(new Empleado("Andres Suarez", Cargo.SUPERVISOR));
			empleados.add(new Empleado("Mario Goicochea", Cargo.SUPERVISOR));
			empleados.add(new Empleado("Said Tilaguy", Cargo.SUPERVISOR));
			empleados.add(new Empleado("Andres Hernandez", Cargo.OPERADOR));
			empleados.add(new Empleado("Linda Carter", Cargo.OPERADOR));
			empleados.add(new Empleado("Scarlet Perez", Cargo.OPERADOR));
			empleados.add(new Empleado("Jazmin Prada", Cargo.OPERADOR));
			empleados.add(new Empleado("Wendolyn Garcia", Cargo.OPERADOR));
			empleados.add(new Empleado("Wilson Iturralve", Cargo.OPERADOR));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	@Override
	public void dispatchCall(Llamada llamada) throws Exception {
		if (llamada == null || EstadoLlamada.INICIALIZADA != llamada.getEstado())
			throw new Exception(MSG_NO_ACTIVA);
		if (empleados == null || empleados.isEmpty())
			throw new Exception("Señor " + llamada.getEmisor().getNombre() + MSG_NO_DISPONIBLE);
		Empleado atiendeLlamada = null;
		int jerarquia = MAX_JERARQUIA;
		while (jerarquia > 0) {
			atiendeLlamada = buscarEmpleadoDisponible(jerarquia--);
			if (atiendeLlamada != null)
				break;
		}
		if (atiendeLlamada == null)
			throw new Exception("Señor " + llamada.getEmisor().getNombre() + MSG_NO_DISPONIBLE);
		llamada.setReceptor(atiendeLlamada);
		atiendeLlamada.setLlamada(llamada);
		Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1001));
		atiendeLlamada.colgarLlamada();
		llamada.setReceptor(null);
	}

	public Empleado buscarEmpleadoDisponible(int jerarquia) {
		for (Empleado empleado : empleados) {
			if (!empleado.isOcupado() && empleado.getCargo().getJerarquia() == jerarquia)
				return empleado;
		}
		return null;
	}

}
