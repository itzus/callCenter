package com.almundo.callcenter.model.cons;

public enum Cargo {

	OPERADOR(3), SUPERVISOR(2), DIRECTOR(1);

	private int jerarquia;

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
