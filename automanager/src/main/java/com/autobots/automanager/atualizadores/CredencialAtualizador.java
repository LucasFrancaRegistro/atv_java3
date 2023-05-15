package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entitades.Credencial;

public class CredencialAtualizador {
	public void atualizar(Credencial credencial, Credencial atualizacao) {
		if (atualizacao != null) {
			if (!(credencial.getCriacao()==null)) {
				credencial.setCriacao(atualizacao.getCriacao());
			}
			if (!(credencial.getUltimoAcesso()==null)) {
				credencial.setUltimoAcesso(atualizacao.getUltimoAcesso());
			}
		}
	}
	
	public void atualizar(Set<Credencial> credenciais, Set<Credencial> atualizacoes) {
		for (Credencial atualizacao : atualizacoes) {
			for (Credencial credencial : credenciais) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == credencial.getId()) {
						atualizar(credencial, atualizacao);
					}
				}
			}
		}
	}
}
