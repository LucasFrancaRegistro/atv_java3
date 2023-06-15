package com.autobots.automanager.controles;

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkMercadoria;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.service.EmpresaService;
import com.autobots.automanager.service.MercadoriaService;
import com.autobots.automanager.service.UsuarioService;
import com.autobots.automanager.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControler {
	@Autowired
	private MercadoriaService mercadoriaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	EmpresaService empresaService;
	
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLinkMercadoria;
	
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Mercadoria mercadoria, @PathVariable Long id){
		
		Long idMercadoria = mercadoriaService.create(mercadoria);
		Mercadoria mercadoriaNova = mercadoriaService.findById(idMercadoria);
		
		Usuario usuario = usuarioService.findById(id);
		
		if(usuario == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		for(Empresa empresa : empresaService.findAll()) {
			for (Usuario usuarios : usuarioService.findAll()) {
				if(usuarios.getId().equals(usuario.getId())) {
					empresa.getMercadorias().add(mercadoriaNova);
					empresaService.create(empresa);
				}
			}
		}
		
		usuario.getMercadorias().add(mercadoriaNova);
		usuarioService.create(usuario);
		return new ResponseEntity<> (HttpStatus.CREATED);
	}
	
	@GetMapping("/mercadorias")
	public ResponseEntity<List<Mercadoria>> buscarTodos(){
		List<Mercadoria> mercadoria = mercadoriaService.findAll();
		if(mercadoria.isEmpty()) {
			return new ResponseEntity<List<Mercadoria>>(HttpStatus.NOT_FOUND);
		}
		adicionadorLinkMercadoria.adicionarLink(mercadoria);
		return new ResponseEntity<List<Mercadoria>>(mercadoria, HttpStatus.FOUND);
	}
	
	@GetMapping("/mercadoria/{id}")
	public ResponseEntity<Mercadoria> buscarPorId(@PathVariable Long id){
		Mercadoria mercadoria = mercadoriaService.findById(id);
		HttpStatus status = HttpStatus.CONFLICT;
		if(mercadoria == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			adicionadorLinkMercadoria.adicionarLink(mercadoria);
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Mercadoria>(mercadoria, status);
		
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria mercadoria, @PathVariable Long id){
		Mercadoria mercadoriaExistente  = mercadoriaService.findById(id);
		if (mercadoriaExistente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		mercadoria.setId(id);
		mercadoriaService.update(mercadoria);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Mercadoria mercadoriaSelecionada = mercadoriaService.findById(id);
	
	    if(mercadoriaSelecionada == null) {
	    	return new ResponseEntity<>(HttpStatus.FOUND);
	    }
	    
	    List<Usuario> usuarios = usuarioService.findAll();
	    List<Empresa> empresas = empresaService.findAll();
	    List<Venda> vendas = vendaService.findAll();
	    
	    for(Usuario usuario : usuarios) {
	    	for(Mercadoria mercadoria : usuario.getMercadorias()) {
	    		if(mercadoria.getId() == id) {
	    			usuario.getMercadorias().remove(mercadoria);
					usuarioService.create(usuario);
	    		}
	    	}
	    }
	    
	    for(Empresa empresa : empresas) {
	    	for(Mercadoria mercadoria : empresa.getMercadorias()) {
	    		if(mercadoria.getId() == id) {
	    			empresa.getMercadorias().remove(mercadoria);
	    			empresaService.create(empresa);
	    		}
	    	}
	    }
	    
	    for(Venda venda : vendas) {
	    	for(Mercadoria mercadoria : venda.getMercadorias()) {
	    		if(mercadoria.getId() == id) {
	    			venda.getMercadorias().remove(mercadoria);
	    			vendaService.create(venda);
	    		}
	    	}
	    }

	    mercadoriaService.delete(mercadoriaSelecionada);
	    return new ResponseEntity<>(HttpStatus.OK);

	}
	
}
