package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MarcaRepositorio;
import br.com.uniamerica.estacionamento.repository.ModeloRepositorio;
import br.com.uniamerica.estacionamento.repository.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepositorio repository;
    @Autowired
    private MarcaRepositorio marcaRepositorio;
    @Autowired
    private ModeloRepositorio modeloRepositorio;

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
            throw new RuntimeException("Placa não informado");
        } else if (veiculo.getCor() == null){
            throw new RuntimeException("cor não informado");
        }
        else if (veiculo.getTipo() == null){
            throw new RuntimeException("tipo não informado");
        }else if (veiculo.getAno() <1900 ){
            throw new RuntimeException("Ano não informado");
        }else if (this.repository.checaPlaca(veiculo.getPlaca())) {
            throw new RuntimeException("placa já está cadastrado.");
        } else if (!this.modeloRepositorio.existsById(veiculo.getModelo().getId())) {
            throw new RuntimeException("Modelo não cadastrado na base de dados.");
        } else
            repository.save(veiculo);
    }

    public void Atualizar(final Long id, final Veiculo veiculo){

        if(veiculo.getPlaca() == null){
            throw new RuntimeException("placa não informado");
        } else if (id != veiculo.getId()) {
            throw new RuntimeException("Existe um erro com o ID informado");
        } else if (veiculo.getCor() == null){
            throw new RuntimeException("cor não informado");
        }  else if (veiculo.getTipo() == null){
            throw new RuntimeException("tipo não informado");
        } else if (veiculo.getAno() <1900 ){
            throw new RuntimeException("Ano não informado ou inválido");
        } else if (repository.checaPlaca(veiculo.getPlaca())) {
            if (veiculo.getId() != repository.checaUsoRetornaId(veiculo.getPlaca())) {
                throw new RuntimeException("Placa já está cadastrada em outro veiculo.");
            }
        }
        repository.save(veiculo);
    }

    public void deletar(final Long id) {
        Veiculo veiculo = this.repository.findById(id).orElse(null);
        if (id == null) {
            throw new RuntimeException("ID não informado.");
        } else if (!repository.checaId(id)) {
            throw new RuntimeException("ID não encontrado na base de dados.");
        } else if (repository.checaUso(id)) {
            veiculo.setAtivo(false);
            repository.save(veiculo);
            } else {
                repository.delete(veiculo);
            }
        }
    }
