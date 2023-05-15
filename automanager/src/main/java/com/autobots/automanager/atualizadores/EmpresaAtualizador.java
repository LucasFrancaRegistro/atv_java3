package com.autobots.automanager.atualizadores;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class EmpresaAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private MercadoriaAtualizador mercadoriaAtualizar = new MercadoriaAtualizador();
	private VendaAtualizador vendaAtualizar = new VendaAtualizador();
	private UsuarioAtualizador usuarioAtualizar = new UsuarioAtualizador();

	public void atualizarDados(Empresa empresa, Empresa atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getRazaoSocial())) {
				empresa.setRazaoSocial(atualizacao.getRazaoSocial());
			}
			if (!verificador.verificar(atualizacao.getNomeFantasia())) {
				empresa.setNomeFantasia(atualizacao.getNomeFantasia());
			}
			if (!(empresa.getCadastro()==null)) {
				empresa.setCadastro(atualizacao.getCadastro());
			}
		}
	}
	
	public void atualizar(Empresa empresa, Empresa atualizacao) {
		atualizarDados(empresa, atualizacao);
		enderecoAtualizador.atualizar(empresa.getEndereco(), atualizacao.getEndereco());
		telefoneAtualizador.atualizar(empresa.getTelefones(), atualizacao.getTelefones());
		mercadoriaAtualizar.atualizar(empresa.getMercadorias(), atualizacao.getMercadorias());
		vendaAtualizar.atualizar(empresa.getVendas(), atualizacao.getVendas());
		usuarioAtualizar.atualizarUsuarios(empresa.getUsuarios(), atualizacao.getUsuarios());
	}
}
