package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.atualizadores.CredencialAtualizador;
import com.autobots.automanager.atualizadores.EmailAtualizador;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.repositorios.CredencialRepositorio;
import com.autobots.automanager.repositorios.EmailRepositorio;

@RestController
@RequestMapping("/credencial")
public class CredencialControle {
	@Autowired
	private CredencialRepositorio repositorio;
	@Autowired
	private AdicionadorLinkCredencial adicionadorLink;

	@GetMapping("/credencia/{id}")
	public ResponseEntity<Credencial> obterCredencial(@PathVariable long id) {
		List<Credencial> credencials = repositorio.findAll();
		Credencial credencial = Selecionador.credencialSelecionador(credencials, id);
		if (credencial == null) {
			ResponseEntity<Credencial> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(credencial);
			ResponseEntity<Credencial> resposta = new ResponseEntity<Credencial>(credencial, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/credencials")
	public ResponseEntity<List<Credencial>> obterCredencials() {
		List<Credencial> credencials = repositorio.findAll();
		if (credencials.isEmpty()) {
			ResponseEntity<List<Credencial>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(credencials);
			ResponseEntity<List<Credencial>> resposta = new ResponseEntity<>(credencials, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarCredencial(@RequestBody Credencial credencial) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (credencial.getId() == null) {
			repositorio.save(credencial);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarCredencial(@RequestBody Credencial atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Credencial credencial = repositorio.getById(atualizacao.getId());
		if (credencial != null) {
			CredencialAtualizador atualizador = new CredencialAtualizador();
			atualizador.atualizar(credencial, atualizacao);
			repositorio.save(credencial);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirCredencial(@RequestBody Credencial exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Credencial credencial = repositorio.getById(exclusao.getId());
		if (credencial != null) {
			repositorio.delete(credencial);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
