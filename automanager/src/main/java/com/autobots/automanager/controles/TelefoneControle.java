package com.autobots.automanager.controles;

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkTelefone;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import com.autobots.automanager.selecionadores.TelefoneSelecionador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {

    @Autowired
    private TelefoneRepositorio repositorio;

    @Autowired
    private TelefoneSelecionador selecionador;

    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @GetMapping("/telefones")
    public ResponseEntity<List<Telefone>> buscarTelefones(){
        List<Telefone> telefones = repositorio.findAll();
        if (telefones.isEmpty()) {
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLink.adicionarLink(telefones);
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
            return resposta;
        }
    }

    @GetMapping("/telefone/{id}")
    public ResponseEntity<Telefone> buscarTelefonePorId(@PathVariable Long id){
        List<Telefone> telefones = repositorio.findAll();
        Telefone telefone = selecionador.selecionar(telefones, id);
        if (telefone == null) {
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLink.adicionarLink(telefone);
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(telefone, HttpStatus.FOUND);
            return resposta;
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (telefone.getId() == null) {
            repositorio.save(telefone);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }


    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Telefone telefone = repositorio.getById(atualizacao.getId());
        if (telefone != null) {
            TelefoneAtualizador atualizador = new TelefoneAtualizador();
            atualizador.atualizar(telefone, atualizacao);
            repositorio.save(telefone);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirTelefone(@RequestBody Telefone exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Telefone telefone = repositorio.getById(exclusao.getId());
        if (telefone != null) {
            repositorio.delete(telefone);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}
