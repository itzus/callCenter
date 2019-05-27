package com.almundo.callcenter;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.almundo.callcenter.model.Llamada;
import com.almundo.callcenter.model.Persona;
import com.almundo.callcenter.service.Dispatcher;
import com.almundo.callcenter.service.IDispatcher;

/**
 * Test para el gestor de llamadas del callcenter.
 * 
 * @author stilaguy
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CallcenterApplicationTests {

	@Autowired
	private IDispatcher servicio;

	private static final Logger LOG = LogManager.getLogger(CallcenterApplicationTests.class);

	private static List<Persona> emisores;

	static {
		// Personas que realizan las peticiones de las llamadas.
		emisores = new ArrayList<Persona>();
		try {
			emisores.add(new Persona("Andres Lujano"));
			emisores.add(new Persona("Ana Maria Rodriguez"));
			emisores.add(new Persona("Sandra Sanchez"));
			emisores.add(new Persona("Peter Parker"));
			emisores.add(new Persona("Dionisio Pinzon"));
			emisores.add(new Persona("Alejandro Estrada"));
			emisores.add(new Persona("Deisy Rincon"));
			emisores.add(new Persona("Alfonso Ruiz"));
			emisores.add(new Persona("Omar Valdivieso"));
			emisores.add(new Persona("Francisco II Suarez"));
			emisores.add(new Persona("Edwin Morat"));
			emisores.add(new Persona("Emerito Diaz"));
			emisores.add(new Persona("Juana Valentina H"));
			emisores.add(new Persona("Mariana Paez"));
			emisores.add(new Persona("Laura Ramirez"));
			emisores.add(new Persona("Sandra Sanchez"));
			emisores.add(new Persona("Peter Parker"));
			emisores.add(new Persona("Dionisio Pinzon"));
			emisores.add(new Persona("Alejandro Estrada"));
			emisores.add(new Persona("Deisy Rincon"));
			emisores.add(new Persona("Alfonso Ruiz"));
			emisores.add(new Persona("Omar Valdivieso"));
			emisores.add(new Persona("Francisco II Suarez"));
			emisores.add(new Persona("Edwin Morat"));
			emisores.add(new Persona("Emerito Diaz"));
			emisores.add(new Persona("Juana Valentina H"));
			emisores.add(new Persona("Mariana Paez"));
			emisores.add(new Persona("Laura Ramirez"));
			emisores.add(new Persona("Juana Valentina H"));
			emisores.add(new Persona("Mariana Paez"));
			emisores.add(new Persona("Laura Ramirez"));
			emisores.add(new Persona("Sandra Sanchez"));
			emisores.add(new Persona("Peter Parker"));
			emisores.add(new Persona("Dionisio Pinzon"));
			emisores.add(new Persona("Alejandro Estrada"));
			emisores.add(new Persona("Deisy Rincon"));
			emisores.add(new Persona("Alfonso Ruiz"));
			emisores.add(new Persona("Omar Valdivieso"));
			emisores.add(new Persona("Francisco II Suarez"));
			emisores.add(new Persona("Edwin Morat"));
			emisores.add(new Persona("Emerito Diaz"));
			emisores.add(new Persona("Juana Valentina H"));
			emisores.add(new Persona("Mariana Paez"));
			emisores.add(new Persona("Laura Ramirez"));
			emisores.add(new Persona("Juana Valentina H"));
			emisores.add(new Persona("Mariana Paez"));
			emisores.add(new Persona("Laura Ramirez"));
			emisores.add(new Persona("Sandra Sanchez"));
			emisores.add(new Persona("Peter Parker"));
			emisores.add(new Persona("Dionisio Pinzon"));
			emisores.add(new Persona("Alejandro Estrada"));
			emisores.add(new Persona("Deisy Rincon"));
			emisores.add(new Persona("Alfonso Ruiz"));
			emisores.add(new Persona("Omar Valdivieso"));
			emisores.add(new Persona("Francisco II Suarez"));
			emisores.add(new Persona("Edwin Morat"));
			emisores.add(new Persona("Emerito Diaz"));
			emisores.add(new Persona("Juana Valentina H"));
			emisores.add(new Persona("Mariana Paez"));
			emisores.add(new Persona("Laura Ramirez"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test de gestion de 10 llamadas.
	 * 
	 * @throws Exception
	 */
	@Test
	public void realizar10Llamadas() throws Exception {
		int llamadas = 0;
		for (Persona persona : emisores) {
			try {
				if (llamadas > 9)
					break;
				llamadas++;
				servicio.dispatchCall(new Llamada(persona));
				assertTrue(true);
			} catch (Exception e) {
				LOG.info(e.getMessage());
			}
		}
	}

	@Test
	public void multiplesLlamadas() throws Exception {
		for (Persona persona : emisores) {
			try {
				servicio.dispatchCall(new Llamada(persona));
				assertTrue(true);
			} catch (Exception e) {
				LOG.info(e.getMessage());
			}
		}
	}

}
