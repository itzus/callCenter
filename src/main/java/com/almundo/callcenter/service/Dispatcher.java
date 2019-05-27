package com.almundo.callcenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.model.Empleado;
import com.almundo.callcenter.model.Llamada;
import com.almundo.callcenter.model.cons.Cargo;
import com.almundo.callcenter.model.cons.EstadoLlamada;

@Service
public class Dispatcher implements IDispatcher {

	private static final Logger LOG = LogManager.getLogger(Dispatcher.class);
	private static List<Empleado> empleados;
	private static List<Llamada> llamadas = new ArrayList<Llamada>();

	private static final int MAX_JERARQUIA = 3;
	private static final int MAX_LLAMADAS_ACTIVAS = 10;
	private static final int MIN_TIEMPO = 5;
	private static final int MAX_TIEMPO = 11;
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
		if (llamadas.size() >= MAX_LLAMADAS_ACTIVAS)
			throw new Exception("Señor " + llamada.getEmisor().getNombre() + MSG_NO_DISPONIBLE);
		llamadas.add(llamada);
		Optional<Empleado> atiendeLlamada = null;
		int jerarquia = MAX_JERARQUIA;
		while (jerarquia > 0) {
			atiendeLlamada = buscarEmpleadoDisponible(jerarquia--);
			if (atiendeLlamada.isPresent())
				break;
		}
		if (!atiendeLlamada.isPresent())
			throw new Exception("Señor " + llamada.getEmisor().getNombre() + MSG_NO_DISPONIBLE);
		procesarLlamada(llamada, atiendeLlamada.get());
	}

	private synchronized void procesarLlamada(Llamada llamada, Empleado atiendeLlamada) throws InterruptedException {
		LOG.info("Se atiende la llamada del Señor " + llamada.getEmisor().getNombre());
		llamada.setReceptor(atiendeLlamada);
		atiendeLlamada.setLlamada(llamada);
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_TIEMPO, MAX_TIEMPO));
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		atiendeLlamada.colgarLlamada();
		llamada.setReceptor(null);
		llamadas.remove(llamada);
		LOG.info("llamada finalizada del Señor " + llamada.getEmisor().getNombre());
	}

	public Optional<Empleado> buscarEmpleadoDisponible(int jerarquia) {
		return empleados.stream()
				.filter(empleado -> !empleado.isOcupado() && empleado.getCargo().getJerarquia() == jerarquia)
				.findFirst();
	}

}
