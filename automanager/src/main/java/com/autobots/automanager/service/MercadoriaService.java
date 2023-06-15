package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MercadoriaService {

    @Autowired
    private MercadoriaRepositorio repositorio;

    public Long create(Mercadoria cadastro) {
        return repositorio.save(cadastro).getId();
    }

    public List<Mercadoria> findAll(){
        List<Mercadoria> mercadorias = repositorio.findAll();
        return mercadorias;
    }

    public Mercadoria findById(Long id) {
        Mercadoria mercadoria = repositorio.findById(id).orElse(null);
        return mercadoria;
    }

    public Mercadoria update(Mercadoria mercadoriaAtualizacao) {
        Mercadoria mercadoriaExistente = findById(mercadoriaAtualizacao.getId());
        mercadoriaAtualizacao.setId(mercadoriaExistente.getId());
        mercadoriaAtualizacao.setCadastro(mercadoriaExistente.getCadastro());

        if (mercadoriaAtualizacao.getValidade() == null){
            mercadoriaAtualizacao.setValidade(mercadoriaExistente.getValidade());
        }
        if (mercadoriaAtualizacao.getFabricao() == null){
            mercadoriaAtualizacao.setFabricao(mercadoriaExistente.getFabricao());
        }
        if (mercadoriaAtualizacao.getNome() == null){
            mercadoriaAtualizacao.setNome(mercadoriaExistente.getNome());
        }
        if (mercadoriaAtualizacao.getDescricao() == null){
            mercadoriaAtualizacao.setDescricao(mercadoriaExistente.getDescricao());
        }
        return repositorio.save(mercadoriaAtualizacao);
    }

    public void delete(Mercadoria mercadoria) {
        repositorio.delete(mercadoria);
    }


}
