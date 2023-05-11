package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.modelo.DoubleVerificadorNulo;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class ServicoAtualizador {
	private StringVerificadorNulo verificadorString = new StringVerificadorNulo();
	private DoubleVerificadorNulo verificadorDouble = new DoubleVerificadorNulo();

	public void atualizar(Servico servico, Servico atualizacao) {
		if (atualizacao != null) {
			if (!verificadorString.verificar(atualizacao.getNome())) {
				servico.setNome(atualizacao.getNome());
			}
			if (!verificadorString.verificar(atualizacao.getDescricao())) {
				servico.setDescricao(atualizacao.getDescricao());
			}
			if (!verificadorDouble.verificar(atualizacao.getValor())) {
				servico.setValor(atualizacao.getValor());
			}
			
		}
	}

	public void atualizar(Set<Servico> servicos, Set<Servico> atualizacoes) {
		for (Servico atualizacao : atualizacoes) {
			for (Servico servico : servicos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == servico.getId()) {
						atualizar(servico, atualizacao);
					}
				}
			}
		}
	}
}
