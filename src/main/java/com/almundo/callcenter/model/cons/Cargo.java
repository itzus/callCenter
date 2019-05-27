package com.almundo.callcenter.model.cons;

/**
 * Determina los cargos de los empleados del call center y la jerarquia entre
 * los mismo.
 * 
 * @author user
 *
 */
public enum Cargo {

	OPERADOR(3), SUPERVISOR(2), DIRECTOR(1);

	private int jerarquia; // entre menor es el valor de la jerarquia esta es mas alta.

	Cargo(int jerarquia) {
		this.jerarquia = jerarquia;
	}

	public int getJerarquia() {
		return this.jerarquia;
	}

	public Cargo obtenerJerarquia(Long jerarquia) {
		for (Cargo cargo : Cargo.values()) {
			if (cargo.jerarquia == jerarquia)
				return cargo;
		}
		return null;

	}

}
