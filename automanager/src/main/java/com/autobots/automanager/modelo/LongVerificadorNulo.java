package com.autobots.automanager.modelo;

public class LongVerificadorNulo {
	public boolean verificar(long d) {
		boolean nulo = true;
		if (!(d == 0)) {
			nulo = false;
		}
		return nulo;
	}
}
