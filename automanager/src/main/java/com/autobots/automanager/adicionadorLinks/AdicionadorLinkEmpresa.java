package com.autobots.automanager.adicionadorLinks;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelo.AdicionadorLink;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

import com.autobots.automanager.controles.EmpresaControle;


@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa> {

	@Override
	public void adicionarLink(List<Empresa> lista) {
		for (Empresa empresa : lista) {
			Link linkEmpresa = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.buscarPorId(empresa.getId()))
					.withSelfRel();
			empresa.add(linkEmpresa);
		}
	}
	
	@Override
	public void adicionarLink(Empresa empresa) {
		Link linkEmpresa = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.buscarTodos())
				.withRel("Todas Empresas");
		empresa.add(linkEmpresa);
	}
}
