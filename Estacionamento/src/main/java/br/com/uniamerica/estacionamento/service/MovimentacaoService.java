package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepositorio repository;

    public void cadastrar(final Movimentacao movimentacao){
        if (movimentacao.getCondutor() == null){
            throw new RuntimeException("Condutor nao informado.");
        }
       else if (movimentacao.getVeiculo() == null){
            throw new RuntimeException("veiculo nao informado.");
        }
        else if (movimentacao.getEntrada() == null){
            throw new RuntimeException("Entrada nao informado.");
        }
        else{
            repository.save(movimentacao);
        }
    }

    public void atualizar(final Movimentacao movimentacao){
        if (movimentacao.getCondutor() == null){
            throw new RuntimeException("Condutor nao informado.");
        }
        else if (movimentacao.getVeiculo() == null){
            throw new RuntimeException("veiculo nao informado.");
        }
        else if (movimentacao.getEntrada() == null){
            throw new RuntimeException("Entrada nao informado.");
        }
        else{
            repository.save(movimentacao);
        }
    }

}
