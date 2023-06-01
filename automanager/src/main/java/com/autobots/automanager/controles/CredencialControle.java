package com.autobots.automanager.controles;

import java.util.List;
import java.util.Set;

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

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkCredencial;
import com.autobots.automanager.atualizadores.CredencialAtualizador;
import com.autobots.automanager.entitades.Credencial;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.repositorios.CredencialRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/credencial")
public class CredencialControle {
	@Autowired
	private CredencialRepositorio repositorio;
	@Autowired
	private UsuarioRepositorio UsuarioRepositorio;
	@Autowired
	private AdicionadorLinkCredencial adicionadorLink;

	@GetMapping("/credencial/{id}")
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
	public ResponseEntity<List<Credencial>> obterCredenciais() {
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

	@PostMapping("/cadastro/{id}")
	public ResponseEntity<?> cadastrarCredencial(@RequestBody Credencial credencial, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (credencial.getId() == null) {
			Usuario usuario = UsuarioRepositorio.getById(id);
			Set<Credencial> credenciais = usuario.getCredenciais();
			credenciais.add(credencial);
			usuario.setCredenciais(credenciais);
			UsuarioRepositorio.save(usuario);
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
	public ResponseEntity<?> excluirCredencial(@RequestBody Credencial exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Credencial credencial = repositorio.getById(exclusao.getId());
		if (credencial != null) {
			Usuario usuario = UsuarioRepositorio.getById(id);
			Set<Credencial> credenciais = usuario.getCredenciais();
			for (Credencial cred: credenciais) {
				if (cred.getId() == exclusao.getId()) {
					credenciais.remove(cred);
					break;
				}
			}
			usuario.setCredenciais(credenciais);
			UsuarioRepositorio.save(usuario);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
