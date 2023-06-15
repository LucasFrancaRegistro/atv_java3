package com.autobots.automanager.controles;


import com.autobots.automanager.adicionadorLinks.AdicionadorLinkUsuario;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.service.EmpresaService;
import com.autobots.automanager.service.UsuarioService;
import com.autobots.automanager.service.VeiculoService;
import com.autobots.automanager.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioControler {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLinkUsuario;
	
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario, @PathVariable Long id){
		Empresa empresaSelecionada = empresaService.findById(id);
		
		if(empresaSelecionada != null) {
	        if(usuario.getPerfis().toString().contains("FORNECEDOR")) {
	        	if(usuario.getMercadorias().size() > 0)
	        	empresaSelecionada.getMercadorias().addAll(usuario.getMercadorias());
	        }	
	        
	        usuarioService.create(usuario);
	        
	        empresaSelecionada.getUsuarios().add(usuario);
	        empresaService.create(empresaSelecionada);

	        return new ResponseEntity<> (HttpStatus.CREATED);
	        
		}
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        	
	}
	
	
	@GetMapping("/usuario")
	public ResponseEntity<List<Usuario>> pegarTodos(){
		List<Usuario> usuario = usuarioService.findAll();
		adicionadorLinkUsuario.adicionarLink(usuario);
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.FOUND);
	}
	
	@GetMapping("/uusarios/{id}")
	public ResponseEntity<Usuario> pegarUm(@PathVariable Long id){
		Usuario usuario = usuarioService.findById(id);
		HttpStatus status = HttpStatus.CONFLICT;
		if(usuario == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			adicionadorLinkUsuario.adicionarLink(usuario);
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Usuario>(usuario, status);
	}
	
	@PutMapping("/cadastrar/credencial/{id}")
	public ResponseEntity<?> cadastroCredencial(@PathVariable Long id, @RequestBody CredencialUsuarioSenha credencialUsuario){
		Usuario usuario = usuarioService.findById(id);
		if(usuario == null) {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		usuario.getCredenciais().add(credencialUsuario);
		usuarioService.create(usuario);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Usuario usuarioSelecionado = usuarioService.findById(id);
		if (usuarioSelecionado == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		for (Empresa empresa : empresaService.findAll()) {
			for(Usuario funcionario : empresa.getUsuarios()) {
				if(funcionario.getId() == usuarioSelecionado.getId()) {
					empresa.getUsuarios().remove(funcionario);
				}
			}
		}
		
		 for(Venda venda : vendaService.findAll()) {
			 if(venda.getFuncionario().getId() == usuarioSelecionado.getId()) {
				 venda.setFuncionario(null);
			 }
			 if(venda.getCliente().getId() == usuarioSelecionado.getId()) {
				 venda.setCliente(null);
			 }
		 }
		
		for (Veiculo veiculo : veiculoService.findAll()) {
			if(veiculo.getProprietario().getId() == usuarioSelecionado.getId()) {
				veiculo.setProprietario(null);
			}
		}
		
		usuarioSelecionado.getDocumentos().removeAll(usuarioSelecionado.getDocumentos());
		usuarioSelecionado.getTelefones().removeAll(usuarioSelecionado.getTelefones());
		usuarioSelecionado.getEmails().removeAll(usuarioSelecionado.getEmails());
		usuarioSelecionado.getCredenciais().removeAll(usuarioSelecionado.getCredenciais());
		usuarioSelecionado.getMercadorias().removeAll(usuarioSelecionado.getMercadorias());
		usuarioSelecionado.getVeiculos().removeAll(usuarioSelecionado.getVeiculos());
		usuarioSelecionado.getVendas().removeAll(usuarioSelecionado.getVendas());
		usuarioSelecionado.setEndereco(null);
		
		
		usuarioService.delete(usuarioSelecionado);
		
		return new ResponseEntity<>(HttpStatus.OK);
			
		}

}
