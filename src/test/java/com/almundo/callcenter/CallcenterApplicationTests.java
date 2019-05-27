package com.almundo.callcenter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.almundo.callcenter.model.Llamada;
import com.almundo.callcenter.model.Persona;
import com.almundo.callcenter.service.IDispatcher;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallcenterApplicationTests {

	@Autowired
	private IDispatcher servicio;

	private static List<Persona> emisores;

	static {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void realizarLlamadas() throws Exception {
		int llamadas = 0;
		for (Persona persona : emisores) {
			try {
				if (llamadas > 9)
					break;
				llamadas++;
				servicio.dispatchCall(new Llamada(persona));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
