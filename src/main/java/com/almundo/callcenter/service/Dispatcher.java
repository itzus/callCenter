package com.almundo.callcenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.model.Empleado;
import com.almundo.callcenter.model.Llamada;
import com.almundo.callcenter.model.cons.Cargo;
import com.almundo.callcenter.model.cons.EstadoLlamada;

/**
 * Implementacion del servicio de gestion de llamadas del callcenter
 * 
 * @author stilaguy
 *
 */
@Service
public class Dispatcher implements IDispatcher {

	private static final Logger LOG = LogManager.getLogger(Dispatcher.class);

	private static List<Empleado> empleados; // empleados del call center, que podran atender las peticiones de
												// llamadas.
	private static List<Llamada> llamadas = new ArrayList<Llamada>(); // llamadas gestionadas al tiempo por el gestor.

	private static final int INICIAL_JERARQUIA = 3; // Nivel inicial de la jerarquia que empezara a gestionar las
													// llamdas
	private static final int MAX_LLAMADAS_ACTIVAS = 10; // limite de llamadas activas en un momento determinado.
	private static final int MIN_TIEMPO = 5; // tiempo minimo de una llamada.
	private static final int MAX_TIEMPO = 11; // limite maximo de tiempo de un llamada.

	// mensajes de error para la gestion de llamadas.
	private static final String MSG_NO_DISPONIBLE = " no existe personas que puedan atender su llamada. por favor marque mas tarde ";
	private static final String MSG_NO_ACTIVA = " La llamada no se encuentra activa";
	private static final String MSG_YA_REGISTRADA = "Esta llamada ya esta siendo gestionana";

	static {
		try {
			// carga inicial de empleaados del callcenter.
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
		// verificamos el estado inicial de la llamada.
		if (llamada == null || EstadoLlamada.INICIALIZADA != llamada.getEstado())
			throw new Exception(MSG_NO_ACTIVA);
		// verificamos si existen empleados para atender las llamadas (si verificar aun
		// si estan ocupados o no.)
		if (empleados == null || empleados.isEmpty())
			throw new Exception("Señor " + llamada.getEmisor().getNombre() + MSG_NO_DISPONIBLE);
		// determinamos que no se supere el numero el numero de llamadas gestionadas de
		// manera concurrente.
		if (llamadas.contains(llamada))
			throw new Exception(MSG_YA_REGISTRADA);
		if (llamadas.size() >= MAX_LLAMADAS_ACTIVAS) {
			LOG.info("Limite de llamadas concurrentes");
			throw new Exception("Señor " + llamada.getEmisor().getNombre() + MSG_NO_DISPONIBLE);
		}
		// en cuyo caso agregamos la llamada actual y la gestionamos.
		llamadas.add(llamada);
		Optional<Empleado> atiendeLlamada = null;
		int jerarquia = INICIAL_JERARQUIA;
		// buscamos un empleado disponible con el nivel inicial de jerarquia
		// correpondiente al más bajo en este caso operador, si no existe quien la
		// atienda aplicamos al siguiente nivel en la jerarquia.
		while (jerarquia > 0) {
			atiendeLlamada = buscarEmpleadoDisponible(jerarquia--);
			if (atiendeLlamada.isPresent())
				break;
		}
		// si incluso así no se encontro al guien quien atienda la llamada generamos la
		// alerta de que no es posible procesar la misma.
		if (!atiendeLlamada.isPresent()) {
			LOG.info(MSG_NO_DISPONIBLE);
			throw new Exception("Señor " + llamada.getEmisor().getNombre() + MSG_NO_DISPONIBLE);
		}
		// si identificamos alguien que atiende la llamada pasamos a procesarla
		procesarLlamada(llamada, atiendeLlamada.get());
	}

	/**
	 * Permite determinar un empleado que no este ocupado y que pertenezca al nivel
	 * de jerarquia solicitado.
	 * 
	 * @param jerarquia
	 * @return un empleado disponible que cumple con las condiciones para atender la
	 *         llamada.
	 */
	private Optional<Empleado> buscarEmpleadoDisponible(int jerarquia) {
		return empleados.stream()
				.filter(empleado -> !empleado.isOcupado() && empleado.getCargo().getJerarquia() == jerarquia)
				.findFirst();
	}

	/**
	 * Permite procesar una llamada cuando se cuenta con un empleado disponible que
	 * la pueda atender.
	 * 
	 * @param llamada        a atender.
	 * @param atiendeLlamada empleado que puede procesar la misma.
	 * @throws InterruptedException en caso de error por la espera de la llamada.
	 */
	private synchronized void procesarLlamada(Llamada llamada, Empleado atiendeLlamada) throws InterruptedException {
		LOG.info("Se atiende la llamada del Señor " + llamada.getEmisor().getNombre() + " por "
				+ atiendeLlamada.getNombre() + ":" + atiendeLlamada.getCargo().name());
		// cargamos la llamada al empleado.
		llamada.setReceptor(atiendeLlamada);
		atiendeLlamada.setLlamada(llamada);
		// generamos un tiempo de espera para la llamada.
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_TIEMPO, MAX_TIEMPO));
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		// finalizamos la llamada y cancelamos la gestion de la misma.
		atiendeLlamada.colgarLlamada();
		llamada.setReceptor(null);
		llamadas.remove(llamada);
		LOG.info("llamada finalizada del Señor " + llamada.getEmisor().getNombre());
	}

}
