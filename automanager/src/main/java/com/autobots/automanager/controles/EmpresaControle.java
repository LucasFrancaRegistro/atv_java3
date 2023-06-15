package com.autobots.automanager.controles;

import java.util.Date;
import java.util.List;

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkEmpresa;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.service.EmpresaService;
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



@RestController
@RequestMapping("/empresa")
public class EmpresaControle {
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private AdicionadorLinkEmpresa empresaLink;

	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa){
		empresa.setCadastro(new Date());
		empresaService.create(empresa);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/empresas")
	public ResponseEntity<List<Empresa>> buscarTodos(){
		List<Empresa> todos = empresaService.findAll();
		HttpStatus status = HttpStatus.CONFLICT;
		if(todos.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<List<Empresa>>(status);
		}else {
			status = HttpStatus.FOUND;
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<List<Empresa>>(todos, status);
			empresaLink.adicionarLink(todos);
			return resposta;
		}
	}

	@GetMapping("/empresa/{id}")
	public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id){
		Empresa empresa = empresaService.findById(id);
		if(empresa == null) {
			return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
		}else {
			empresaLink.adicionarLink(empresa);
			return new ResponseEntity<Empresa>(empresa, HttpStatus.FOUND);
		}
	}
	
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa){
		Empresa empresaExistente = empresaService.findById(id);
		if(empresaExistente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		empresa.setId(id);
		empresaService.update(empresaExistente);
		empresaService.create(empresa);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Empresa empresaSelecionada = empresaService.findById(id);
		if(empresaSelecionada == null) {
			return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
		}
		empresaService.delete(empresaSelecionada);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}