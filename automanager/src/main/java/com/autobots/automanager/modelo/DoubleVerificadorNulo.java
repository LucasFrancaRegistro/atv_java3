package com.autobots.automanager.modelo;

public class DoubleVerificadorNulo {
	public boolean verificar(double d) {
		boolean nulo = true;
		if (!(d == 0)) {
			nulo = false;
		}
		return nulo;
	}
}
