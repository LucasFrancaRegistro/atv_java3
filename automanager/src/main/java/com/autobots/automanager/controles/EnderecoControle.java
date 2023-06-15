package com.autobots.automanager.controles;

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkEndereco;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.selecionadores.EnderecoSelecionador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Endereco")
public class EnderecoControle{
    @Autowired
    private EnderecoRepositorio repositorio;

    @Autowired
    private EnderecoSelecionador selecionador;

    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;


    @GetMapping("/enderecos")
    public ResponseEntity<List<Endereco>> buscarEnderecos(){
        List<Endereco> enderecos = repositorio.findAll();
        if (enderecos.isEmpty()) {
            ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLink.adicionarLink(enderecos);
            ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
            return resposta;
        }
    }

    @GetMapping("/endereco/{id}")
    public ResponseEntity<Endereco> buscarEnderecoPorId(@PathVariable Long id){
        List<Endereco> enderecos = repositorio.findAll();
        Endereco endereco = selecionador.selecionar(enderecos, id);
        if (endereco == null) {
            ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLink.adicionarLink(endereco);
            ResponseEntity<Endereco> resposta = new ResponseEntity<>(endereco, HttpStatus.FOUND);
            return resposta;
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (endereco.getId() == null) {
            repositorio.save(endereco);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }


    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Endereco endereco = repositorio.getById(atualizacao.getId());
        if (endereco != null) {
            EnderecoAtualizador atualizador = new EnderecoAtualizador();
            atualizador.atualizar(endereco, atualizacao);
            repositorio.save(endereco);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);

    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirEndereco(@RequestBody Endereco exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Endereco endereco = repositorio.getById(exclusao.getId());
        if (endereco != null) {
            repositorio.delete(endereco);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}
