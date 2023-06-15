package com.autobots.automanager.controles;

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkVenda;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
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
@RequestMapping("/venda")

public class VendaControler {

	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AdicionadorLinkVenda linkVenda;
	
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Venda venda, @PathVariable Long id){
		Empresa empresaSelecionada = empresaService.findById(id);
		if(empresaSelecionada == null) {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Usuario funcionarioSelecionado = usuarioService.findById(venda.getFuncionario().getId());
		Usuario clienteSelecionado = usuarioService.findById(venda.getCliente().getId());
		Veiculo veiculoSelecionado = veiculoService.findById(venda.getVeiculo().getId());
		venda.setVeiculo(veiculoSelecionado);
		venda.setCliente(clienteSelecionado);
		venda.setFuncionario(funcionarioSelecionado);
		usuarioService.create(funcionarioSelecionado);

		empresaSelecionada.getVendas().add(venda);
		empresaService.create(empresaSelecionada);

		vendaService.create(venda);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	@GetMapping("/vendas")
	public ResponseEntity<List<Venda>> buscarTodos(){
		List<Venda> venda = vendaService.findAll();
		linkVenda.adicionarLink(venda);
		return new ResponseEntity<List<Venda>>(venda, HttpStatus.FOUND);
	}
	
	@GetMapping("/venda/{id}")
	public ResponseEntity<Venda> buscarPorId(@PathVariable Long id){
		Venda venda = vendaService.findById(id);
		HttpStatus status = HttpStatus.CONFLICT;
		if(venda == null) {
			status = HttpStatus.NOT_FOUND;	
		}else{
			linkVenda.adicionarLink(venda);
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Venda>(venda, status);
	}

	@PutMapping("/atualizar/{id}")
		public ResponseEntity<?> atualizarVenda (@RequestBody Venda venda, @PathVariable Long id){
			Venda vendaExistente = vendaService.findById(id);
			if (vendaExistente == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			venda.setId(id);
			vendaService.update(venda);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
	
	
	
}
