package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;


@Component
public class Selecionador {
	public static Usuario usuarioSelecionador(List<Usuario> usuarios, long id) {
		Usuario selecionado = null;
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id) {
				selecionado = usuario;
			}
		}
		return selecionado;
	}
	public static Documento documentoSelecionador(List<Documento> documentos, long id) {
		Documento selecionado = null;
		for (Documento documento : documentos) {
			if (documento.getId() == id) {
				selecionado = documento;
			}
		}
		return selecionado;
	}
	public static Endereco enderecoSelecionador(List<Endereco> enderecos, long id) {
		Endereco selecionado = null;
		for (Endereco endereco : enderecos) {
			if (endereco.getId() == id) {
				selecionado = endereco;
			}
		}
		return selecionado;
	}
	public static Telefone telefoneSelecionador(List<Telefone> Telefones, long id) {
		Telefone selecionado = null;
		for (Telefone Telefone : Telefones) {
			if (Telefone.getId() == id) {
				selecionado = Telefone;
			}
		}
		return selecionado;
	}
	public static Venda vendaSelecionador(List<Venda> vendas, long id) {
		Venda selecionado = null;
		for (Venda venda : vendas) {
			if (venda.getId() == id) {
				selecionado = venda;
			}
		}
		return selecionado;
	}
	public static Servico servicoSelecionador(List<Servico> servicos, long id) {
		Servico selecionado = null;
		for (Servico servico : servicos) {
			if (servico.getId() == id) {
				selecionado = servico;
			}
		}
		return selecionado;
	}
	public static Veiculo veiculoSelecionador(List<Veiculo> veiculos, long id) {
		Veiculo selecionado = null;
		for (Veiculo veiculo : veiculos) {
			if (veiculo.getId() == id) {
				selecionado = veiculo;
			}
		}
		return selecionado;
	}
	public static Empresa empresaSelecionador(List<Empresa> empresas, long id) {
		Empresa selecionado = null;
		for (Empresa empresa : empresas) {
			if (empresa.getId() == id) {
				selecionado = empresa;
			}
		}
		return selecionado;
	}
	public static Credencial credencialSelecionador(List<Credencial> credenciais, long id) {
		Credencial selecionado = null;
		for (Credencial credencial : credenciais) {
			if (credencial.getId() == id) {
				selecionado = credencial;
			}
		}
		return selecionado;
	}
	public static CredencialUsuarioSenha credencialUsuarioSenhaSelecionador(List<CredencialUsuarioSenha> credenciais, long id) {
		CredencialUsuarioSenha selecionado = null;
		for (CredencialUsuarioSenha credencial : credenciais) {
			if (credencial.getId() == id) {
				selecionado = credencial;
			}
		}
		return selecionado;
	}
	public static Mercadoria mercadoriaSelecionador(List<Mercadoria> mercadorias, long id) {
		Mercadoria selecionado = null;
		for (Mercadoria mercadoria : mercadorias) {
			if (mercadoria.getId() == id) {
				selecionado = mercadoria;
			}
		}
		return selecionado;
	}
	public static Email emailSelecionador(List<Email> emails, long id) {
		Email selecionado = null;
		for (Email email : emails) {
			if (email.getId() == id) {
				selecionado = email;
			}
		}
		return selecionado;
	}
	
}