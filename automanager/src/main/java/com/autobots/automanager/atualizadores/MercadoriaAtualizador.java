package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.modelo.DoubleVerificadorNulo;
import com.autobots.automanager.modelo.LongVerificadorNulo;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class MercadoriaAtualizador {
	private StringVerificadorNulo verificadorString = new StringVerificadorNulo();
	private DoubleVerificadorNulo verificadorDouble = new DoubleVerificadorNulo();
	private LongVerificadorNulo verificadorLong = new LongVerificadorNulo();

	public void atualizar(Mercadoria mercadoria, Mercadoria atualizacao) {
		if (atualizacao != null) {
			if (!verificadorString.verificar(atualizacao.getNome())) {
				mercadoria.setNome(atualizacao.getNome());
			}
			if (!verificadorString.verificar(atualizacao.getDescricao())) {
				mercadoria.setDescricao(atualizacao.getDescricao());
			}
			if (!verificadorDouble.verificar(atualizacao.getValor())) {
				mercadoria.setValor(atualizacao.getValor());
			}
			if (!verificadorLong.verificar(atualizacao.getQuantidade())) {
				mercadoria.setQuantidade(atualizacao.getQuantidade());
			}
			if (!(atualizacao.getCadastro() == null)) {
				mercadoria.setCadastro(atualizacao.getCadastro());
			}
			if (!(atualizacao.getFabricao() == null)) {
				mercadoria.setFabricao(atualizacao.getFabricao());
			}
			if (!(atualizacao.getValidade() == null)) {
				mercadoria.setValidade(atualizacao.getValidade());
			}
			
			
			
		}
	}
}
