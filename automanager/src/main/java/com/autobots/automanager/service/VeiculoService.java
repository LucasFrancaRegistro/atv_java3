package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepositorio repositorio;

    public Long create(Veiculo veiculo) {
        return repositorio.save(veiculo).getId();
    }

    public List<Veiculo> findAll(){
        List<Veiculo> veiculos = repositorio.findAll();
        return veiculos;
    }

    public Veiculo findById(Long id) {
        Veiculo veiculo = repositorio.findById(id).orElse(null);
        return veiculo;
    }

    public Veiculo update(Veiculo veiculoAtualizacao) {
        Veiculo veiculoExistente = findById(veiculoAtualizacao.getId());
        veiculoAtualizacao.setId(veiculoExistente.getId());

        if (veiculoAtualizacao.getTipo() == null){
            veiculoAtualizacao.setTipo(veiculoExistente.getTipo());
        }
        if (veiculoAtualizacao.getModelo() == null){
            veiculoAtualizacao.setModelo(veiculoExistente.getModelo());
        }
        if (veiculoAtualizacao.getPlaca() == null){
            veiculoAtualizacao.setPlaca(veiculoExistente.getPlaca());
        }
        if (veiculoAtualizacao.getProprietario() == null){
            veiculoAtualizacao.setProprietario(veiculoExistente.getProprietario());
        }
        if (veiculoAtualizacao.getVendas() == null){
            veiculoAtualizacao.setVendas(veiculoExistente.getVendas());
        }
        return repositorio.save(veiculoAtualizacao);
    }

    public void delete(Veiculo veiculo) {
        repositorio.delete(veiculo);
    }
}
