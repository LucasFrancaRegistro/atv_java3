package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepositorio repositorio;


    public void create(Empresa empresa) {
        repositorio.save(empresa);
    }


    public List<Empresa> findAll(){
        List<Empresa> empresas = repositorio.findAll();
        return empresas;
    }

    public Empresa findById(Long id) {
        Empresa empresa = repositorio.findById(id).orElse(null);
        return empresa;
    }

    public Empresa update(Empresa empresaAtualizacao) {
        Empresa empresaExistente = findById(empresaAtualizacao.getId());
        empresaAtualizacao.setId(empresaExistente.getId());
        empresaAtualizacao.setRazaoSocial(empresaExistente.getRazaoSocial());
        empresaAtualizacao.setCadastro(empresaExistente.getCadastro());
        if (empresaAtualizacao.getServicos() == null){
            empresaAtualizacao.setServicos(empresaExistente.getServicos());
        }
        if (empresaAtualizacao.getMercadorias() == null){
            empresaAtualizacao.setMercadorias(empresaExistente.getMercadorias());
        }
        if (empresaAtualizacao.getUsuarios() == null){
            empresaAtualizacao.setUsuarios(empresaExistente.getUsuarios());
        }
        if (empresaAtualizacao.getVendas() == null){
            empresaAtualizacao.setVendas(empresaExistente.getVendas());
        }
        if (empresaAtualizacao.getEndereco() == null){
            empresaAtualizacao.setEndereco(empresaExistente.getEndereco());
        }
        if (empresaAtualizacao.getTelefones() == null){
            empresaAtualizacao.setTelefones(empresaExistente.getTelefones());
        }

        return repositorio.save(empresaAtualizacao);
    }

    public void delete(Empresa empresa) {
        repositorio.delete(empresa);
    }

}
