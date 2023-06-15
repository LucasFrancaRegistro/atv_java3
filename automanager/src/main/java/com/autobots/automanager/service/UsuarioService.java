package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio repositorio;

    public Long create(Usuario usuario) {
        return repositorio.save(usuario).getId();
    }

    public List<Usuario> findAll(){
        List<Usuario> usuarios = repositorio.findAll();
        return usuarios;
    }

    public Usuario findById(Long id) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        return usuario;
    }

    public Usuario update(Usuario usuarioAtualizacao) {
        Usuario usuarioExistente = findById(usuarioAtualizacao.getId());
        usuarioAtualizacao.setId(usuarioExistente.getId());

        if (usuarioAtualizacao.getNome() == null){
            usuarioAtualizacao.setNome(usuarioExistente.getNome());
        }
        if (usuarioAtualizacao.getNomeSocial() == null){
            usuarioAtualizacao.setNomeSocial(usuarioExistente.getNomeSocial());
        }
        if (usuarioAtualizacao.getPerfis() == null){
            usuarioAtualizacao.setPerfis(usuarioExistente.getPerfis());
        }
        if (usuarioAtualizacao.getTelefones() == null){
            usuarioAtualizacao.setTelefones(usuarioExistente.getTelefones());
        }
        if (usuarioAtualizacao.getEndereco() == null){
            usuarioAtualizacao.setEndereco(usuarioExistente.getEndereco());
        }
        if (usuarioAtualizacao.getDocumentos() == null){
            usuarioAtualizacao.setDocumentos(usuarioExistente.getDocumentos());
        }
        if (usuarioAtualizacao.getEmails() == null){
            usuarioAtualizacao.setEmails(usuarioExistente.getEmails());
        }
        if (usuarioAtualizacao.getCredenciais() == null){
            usuarioAtualizacao.setCredenciais(usuarioExistente.getCredenciais());
        }
        if (usuarioAtualizacao.getMercadorias() == null){
            usuarioAtualizacao.setMercadorias(usuarioExistente.getMercadorias());
        }
        if (usuarioAtualizacao.getVendas() == null){
            usuarioAtualizacao.setVendas(usuarioExistente.getVendas());
        }
        if (usuarioAtualizacao.getVeiculos() == null){
            usuarioAtualizacao.setVeiculos(usuarioExistente.getVeiculos());
        }
        return repositorio.save(usuarioAtualizacao);
    }

    public void delete(Usuario usuario) {
        repositorio.delete(usuario);
    }
}
