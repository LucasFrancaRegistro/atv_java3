package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class VeiculoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private static VendaAtualizador vendaAtualizar = new VendaAtualizador();

	public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
		if (atualizacao != null) {
			if (!(veiculo.getProprietario()==null)) {
				veiculo.setProprietario(atualizacao.getProprietario());
			}
			if (!verificador.verificar(atualizacao.getPlaca())) {
				veiculo.setPlaca(atualizacao.getPlaca());
			}
			if (!verificador.verificar(atualizacao.getModelo())) {
				veiculo.setModelo(atualizacao.getModelo());
			}
			if (!(veiculo.getTipo()==null)) {
				veiculo.setTipo(atualizacao.getTipo());
			}
		}
	}
	
	public void atualizar(Set<Veiculo> veiculos, Set<Veiculo> atualizacoes) {
		for (Veiculo atualizacao : atualizacoes) {
			for (Veiculo veiculo : veiculos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == veiculo.getId()) {
						atualizar(veiculo, atualizacao);
						vendaAtualizar.atualizar(veiculo.getVendas(), atualizacao.getVendas());
					}
				}
			}
		}
	}
}
