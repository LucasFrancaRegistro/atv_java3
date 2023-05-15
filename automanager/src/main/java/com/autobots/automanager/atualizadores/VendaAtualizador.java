package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class VendaAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private static MercadoriaAtualizador mercadoriaAtualizar = new MercadoriaAtualizador();
	private static ServicoAtualizador servicoAtualizar = new ServicoAtualizador();

	public void atualizar(Venda venda, Venda atualizacao) {
		if (atualizacao != null) {
			if (!(venda.getCliente()==null)) {
				venda.setCliente(atualizacao.getCliente());
			}
			if (!verificador.verificar(atualizacao.getIdentificacao())) {
				venda.setIdentificacao(atualizacao.getIdentificacao());
			}
			if (!(venda.getCadastro()==null)) {
				venda.setCadastro(atualizacao.getCadastro());
			}
			if (!(venda.getFuncionario()==null)) {
				venda.setFuncionario(atualizacao.getFuncionario());
			}
			if (!(venda.getCadastro()==null)) {
				venda.setCadastro(atualizacao.getCadastro());
			}
			if (!(venda.getVeiculo()==null)) {
				venda.setVeiculo(atualizacao.getVeiculo());
			}
		}
	}
	
	public void atualizar(Set<Venda> vendas, Set<Venda> atualizacoes) {
		for (Venda atualizacao : atualizacoes) {
			for (Venda venda : vendas) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == venda.getId()) {
						atualizar(venda, atualizacao);
						mercadoriaAtualizar.atualizar(venda.getMercadorias(), atualizacao.getMercadorias());
						servicoAtualizar.atualizar(venda.getServicos(), atualizacao.getServicos());
					}
				}
			}
		}
	}
}
