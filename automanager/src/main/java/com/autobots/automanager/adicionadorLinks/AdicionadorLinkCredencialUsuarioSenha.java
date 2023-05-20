package com.autobots.automanager.adicionadorLinks;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.CredencialUsuarioSenhaControle;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkCredencialUsuarioSenha implements AdicionadorLink<CredencialUsuarioSenha>{
	@Override
	public void adicionarLink(List<CredencialUsuarioSenha> lista) {
		for (CredencialUsuarioSenha objeto : lista) {
			long id = objeto.getId();
			Link linkProprioCredencial = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(CredencialUsuarioSenhaControle.class)
							.obterCredencialUsuarioSenha(id))
					.withSelfRel();
			Link linkProprioExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(CredencialUsuarioSenhaControle.class)
							.excluirCredencialUsuarioSenha(objeto))
					.withSelfRel();
			Link linkProprioAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(CredencialUsuarioSenhaControle.class)
							.atualizarCredencialUsuarioSenha(objeto))
					.withSelfRel();
			Link linkProprioCadastrar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(CredencialUsuarioSenhaControle.class)
							.cadastrarCredencialUsuarioSenha(objeto))
					.withSelfRel();
			Link linkProprioCredenciais = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(CredencialUsuarioSenhaControle.class)
							.obterCredenciaisUsuarioSenha())
					.withSelfRel();
			objeto.add(linkProprioCredencial);
			objeto.add(linkProprioExcluir);
			objeto.add(linkProprioAtualizar);
			objeto.add(linkProprioCadastrar);
			objeto.add(linkProprioCredenciais);
		}
	}

	@Override
	public void adicionarLink(CredencialUsuarioSenha objeto) {
		Link linkProprioCredencial = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialUsuarioSenhaControle.class)
						.obterCredencialUsuarioSenha(objeto.getId()))
				.withRel("credencialUsuario");
		Link linkProprioCredenciais = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialUsuarioSenhaControle.class)
						.obterCredenciaisUsuarioSenha())
				.withRel("credenciaisUsuario");
		Link linkProprioCadastrar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialUsuarioSenhaControle.class)
						.cadastrarCredencialUsuarioSenha(objeto))
				.withRel("cadastrar");
		Link linkProprioAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialUsuarioSenhaControle.class)
						.atualizarCredencialUsuarioSenha(objeto))
				.withRel("atualizar");
		Link linkProprioExcluir = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialUsuarioSenhaControle.class)
						.excluirCredencialUsuarioSenha(objeto))
				.withRel("excluir");
		objeto.add(linkProprioCredencial);
		objeto.add(linkProprioCredenciais);
		objeto.add(linkProprioCadastrar);
		objeto.add(linkProprioAtualizar);
		objeto.add(linkProprioExcluir);
	}
}
