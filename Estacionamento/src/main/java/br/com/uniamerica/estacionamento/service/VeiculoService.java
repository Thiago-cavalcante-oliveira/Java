package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepositorio repository;

    public Optional<Veiculo> BuscarPorID(final Long id){
        if(id == null){
            throw new RuntimeException("Você não informou um id para consultar.");
        }
        else if (repository.checaId(id)){
            throw new RuntimeException("ID não localizado.");
        }
        else{
            Optional<Veiculo> veiculo = this.repository.findById(id);
            return veiculo;}
    }

    @Transactional(rollbackFor =  Exception.class)
    public void cadastrar(final Veiculo veiculo){
        if(veiculo.getPlaca() == null){
            throw new RuntimeException("Nome não informado");
        } else if (veiculo.getCor() == null){
            throw new RuntimeException("cor não informado");
        } else if (veiculo.getTipo() == null){
            throw new RuntimeException("tipo não informado");
                 }
        else if (veiculo.getAno() <0 ){
                throw new RuntimeException("Ano não informado");
                }
                else if (repository.checaPlaca(veiculo.getPlaca())) {
            throw new RuntimeException("Nome já está cadastrado.");
        } else if (!veiculo.isAtivo()) {
            throw new RuntimeException("Não pode ser Inativo ao cadastrar.");
        }else
            repository.save(veiculo);
    }

    public void Atualizar(final Long id, final Veiculo veiculo){
        if(veiculo.getPlaca() == null){
            throw new RuntimeException("Nome não informado");
        } else if (veiculo.getCor() == null){
            throw new RuntimeException("cor não informado");
        } else if (veiculo.getTipo() == null){
            throw new RuntimeException("tipo não informado");
        } else if (veiculo.getAno() <0 ){
            throw new RuntimeException("Ano não informado");
        } else if (repository.checaPlaca(veiculo.getPlaca())) {
            throw new RuntimeException("Nome já está cadastrado.");
        } else if (!veiculo.isAtivo()) {
            throw new RuntimeException("Não pode ser Inativo ao cadastrar.");
        }
        repository.save(veiculo);
    }

    public void deletar(final Long id) {
        if (id == null) {
            throw new RuntimeException("ID não informado.");
        } else if (repository.checaId(id)) {
            throw new RuntimeException("ID não encontrado na base de dados.");
        } else if (repository.checaUso(id)) {
            Optional<Veiculo> veiculo = this.repository.findById(id);
            if (veiculo.isPresent()) {
                veiculo.get().setAtivo(false);
            } else {
                repository.deleteById(id);
            }
        }
    }

}
