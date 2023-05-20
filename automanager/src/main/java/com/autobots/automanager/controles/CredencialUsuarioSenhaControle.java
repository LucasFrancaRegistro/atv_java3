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

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkCredencialUsuarioSenha;
import com.autobots.automanager.atualizadores.CredencialUsuarioSenhaAtualizador;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.repositorios.CredencialUsuarioSenhaRepositorio;

@RestController
@RequestMapping("/credencial")
public class CredencialUsuarioSenhaControle {
	@Autowired
	private CredencialUsuarioSenhaRepositorio repositorio;
	@Autowired
	private AdicionadorLinkCredencialUsuarioSenha adicionadorLink;

	@GetMapping("/credencia/{id}")
	public ResponseEntity<CredencialUsuarioSenha> obterCredencialUsuarioSenha(@PathVariable long id) {
		List<CredencialUsuarioSenha> credencials = repositorio.findAll();
		CredencialUsuarioSenha credencial = Selecionador.credencialUsuarioSenhaSelecionador(credencials, id);
		if (credencial == null) {
			ResponseEntity<CredencialUsuarioSenha> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(credencial);
			ResponseEntity<CredencialUsuarioSenha> resposta = new ResponseEntity<CredencialUsuarioSenha>(credencial, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/credencials")
	public ResponseEntity<List<CredencialUsuarioSenha>> obterCredenciaisUsuarioSenha() {
		List<CredencialUsuarioSenha> credencials = repositorio.findAll();
		if (credencials.isEmpty()) {
			ResponseEntity<List<CredencialUsuarioSenha>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(credencials);
			ResponseEntity<List<CredencialUsuarioSenha>> resposta = new ResponseEntity<>(credencials, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastrarCredencialUsuarioSenha(@RequestBody CredencialUsuarioSenha credencial) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (credencial.getId() == null) {
			repositorio.save(credencial);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarCredencialUsuarioSenha(@RequestBody CredencialUsuarioSenha atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		CredencialUsuarioSenha credencial = repositorio.getById(atualizacao.getId());
		if (credencial != null) {
			CredencialUsuarioSenhaAtualizador atualizador = new CredencialUsuarioSenhaAtualizador();
			atualizador.atualizar(credencial, atualizacao);
			repositorio.save(credencial);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirCredencialUsuarioSenha(@RequestBody CredencialUsuarioSenha exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		CredencialUsuarioSenha credencial = repositorio.getById(exclusao.getId());
		if (credencial != null) {
			repositorio.delete(credencial);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
