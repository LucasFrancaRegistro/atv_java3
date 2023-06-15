package com.autobots.automanager.selecionadores;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnderecoSelecionador {

    public Endereco selecionar(List<Endereco> enderecos, long id) {
        Endereco selecionado = null;
        for (Endereco endereco : enderecos) {
            if (endereco.getId() == id) {
                selecionado = endereco;
            }
        }
        return selecionado;
    }

}
