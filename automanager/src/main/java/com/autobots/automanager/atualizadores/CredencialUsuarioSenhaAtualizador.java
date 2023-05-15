package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class CredencialUsuarioSenhaAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(CredencialUsuarioSenha credencial, CredencialUsuarioSenha atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(credencial.getNomeUsuario())) {
				credencial.setNomeUsuario(atualizacao.getNomeUsuario());
			}
			if (!verificador.verificar(atualizacao.getSenha())) {
				credencial.setSenha(atualizacao.getSenha());
			}
		}
	}
}
