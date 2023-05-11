package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Usuario;


@Component
public class Selecionador {
	public static Usuario clienteSelecionador(List<Usuario> usuarios, long id) {
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
}