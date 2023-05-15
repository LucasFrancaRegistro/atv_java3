package com.autobots.automanager.atualizadores;

import java.util.Set;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class UsuarioAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
	private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
	private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
	private CredencialAtualizador credencialAtualizador = new CredencialAtualizador();
	private MercadoriaAtualizador mercadoriaAtualizar = new MercadoriaAtualizador();
	private VendaAtualizador vendaAtualizar = new VendaAtualizador();
	private VeiculoAtualizador veiculoAtualizar = new VeiculoAtualizador();
	private EmailAtualizador emailAtualizar = new EmailAtualizador();

	private void atualizarDados(Usuario usuario, Usuario atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			usuario.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			usuario.setNomeSocial(atualizacao.getNomeSocial());
		}
		if (!(atualizacao.getPerfis()==null)) {
			usuario.setPerfis(atualizacao.getPerfis());
		}
	}

	public void atualizar(Usuario usuario, Usuario atualizacao) {
		atualizarDados(usuario, atualizacao);
		enderecoAtualizador.atualizar(usuario.getEndereco(), atualizacao.getEndereco());
		documentoAtualizador.atualizar(usuario.getDocumentos(), atualizacao.getDocumentos());
		telefoneAtualizador.atualizar(usuario.getTelefones(), atualizacao.getTelefones());
		mercadoriaAtualizar.atualizar(usuario.getMercadorias(), atualizacao.getMercadorias());
		veiculoAtualizar.atualizar(usuario.getVeiculos(), atualizacao.getVeiculos());
		vendaAtualizar.atualizar(usuario.getVendas(), atualizacao.getVendas());
		credencialAtualizador.atualizar(usuario.getCredenciais(), atualizacao.getCredenciais());
		emailAtualizar.atualizar(usuario.getEmails(), atualizacao.getEmails());
	}
	
	public void atualizarUsuarios(Set<Usuario> usuarios, Set<Usuario> atualizacoes) {
		for (Usuario atualizacao : atualizacoes) {
			for (Usuario usuario : usuarios) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == usuario.getId()) {
						atualizar(usuario, atualizacao);
					}
				}
			}
		}
	}
}

