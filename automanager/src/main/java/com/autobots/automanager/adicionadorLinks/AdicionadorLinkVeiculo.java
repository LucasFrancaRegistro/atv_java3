package com.autobots.automanager.adicionadorLinks;

import java.util.List;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelo.AdicionadorLink;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VeiculoControler;



@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink <Veiculo>{
	
	@Override
	public void adicionarLink( List<Veiculo> lista ) {
		for (Veiculo veiculo : lista) {
			Link linkVeiculo =  WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControler.class)
							.buscarPorId(veiculo.getId()))
					.withSelfRel();
			veiculo.add(linkVeiculo);
			
		}
	}
	@Override
	public void adicionarLink( Veiculo veiculo ) {
			Link linkVeiculo =  WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControler.class)
							.buscarTodos())
					.withRel("Todos Clientes");
			veiculo.add(linkVeiculo);
			
}
	}
