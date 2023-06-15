package com.autobots.automanager.controles;

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkVeiculo;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.service.UsuarioService;
import com.autobots.automanager.service.VeiculoService;
import com.autobots.automanager.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControler {
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private VendaService vendaService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLinkVeiculo;
	
	@PostMapping("cadastrar/{id}")
	public ResponseEntity<?> cadastrar (@RequestBody Veiculo veiculo, @PathVariable Long id){
		Usuario usuario = usuarioService.findById(id);
		if(usuario == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			usuario.getVeiculos().add(veiculo);
			veiculo.setProprietario(usuario);
			veiculoService.create(veiculo);
			usuarioService.create(usuario);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}
	
	@GetMapping("/veiculos")
	public ResponseEntity<List<Veiculo>> buscarTodos(){
		List<Veiculo> veiculo = veiculoService.findAll();
		adicionadorLinkVeiculo.adicionarLink(veiculo);
		return new ResponseEntity<List<Veiculo>>(veiculo, HttpStatus.FOUND);
	}
	
	@GetMapping("/veiculo/{id}")
	public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id){
		Veiculo veiculo = veiculoService.findById(id);
		HttpStatus status = HttpStatus.CONFLICT;
		if(veiculo == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			adicionadorLinkVeiculo.adicionarLink(veiculo);
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Veiculo>(veiculo, status);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarVeiculo (@RequestBody Veiculo veiculo, @PathVariable Long id){
		Veiculo vec = veiculoService.findById(id);
		if (vec == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		veiculo.setId(id);
		veiculoService.update(veiculo);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Veiculo veiculoSelecionado = veiculoService.findById(id);
		if(veiculoSelecionado == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<Usuario> usuarios = usuarioService.findAll();
		List<Venda> vendas = vendaService.findAll();
		
		for(Usuario usuario : usuarios) {
			for(Veiculo veiculo: usuario.getVeiculos()) {
				if(veiculo.getId() == id) {
					usuario.getVeiculos().remove(veiculo);
					usuarioService.create(usuario);
				}
			}
		}
		
		for(Venda venda : vendas) {
				if(venda.getVeiculo().getId() == id) {
					venda.setVeiculo(null);
					vendaService.create(venda);
				}
			}

		veiculoService.delete(veiculoSelecionado);
		return new ResponseEntity<>(HttpStatus.OK);
		
		}
		
	
}

