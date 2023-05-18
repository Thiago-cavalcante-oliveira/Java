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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepositorio repository;
    @Autowired
    private ModeloRepositorio modeloRepositorio;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Marca marca) {
        if (marca.getNome() == null || marca.getNome().isEmpty()) {
            throw new RuntimeException("Nome não informado");
        } else if (repository.checaNome(marca.getNome())) {

            throw new RuntimeException("Nome já está cadastrado.");
        } else
            repository.save(marca);
    }

    public void Atualizar(final Marca marca, final Long id) {
        if (id != marca.getId()) {
            throw new RuntimeException("Houve um erro: o id informado é diferente do ID em Banco");
        } else if (!repository.existsById(id)) {
            throw new RuntimeException("ID não encontrado na base de dados");
        } else if (marca.getNome() == null) {
            throw new RuntimeException("Nome não informado.");
        } else if (repository.checaNome(marca.getNome())) {
            throw new RuntimeException("Este marca já está cadastrado.");
        } else if (!marca.isAtivo()) {
            throw new RuntimeException("Esta marca está desativada.");
        }
        repository.save(marca);
    }

    public Optional<Marca> buscaPorId(final Long id) {
        if (id == null) {
            throw new RuntimeException("Id não informado.");
        } else if (!repository.checaId(id)) {
            throw new RuntimeException("Id não existe na base de dados");
        }
        Optional<Marca> marca = this.repository.findById(id);
        return marca;
    }

    public void deletar(final Long id) {

        Marca marca = this.repository.findById(id).orElse(null);
        if(id == null ){
            throw new RuntimeException("Id não informado");
        }
         else if (repository.checaUso(id)){
            marca.setAtivo(false);
            this.repository.save(marca);
            } else {
                repository.delete(marca);
            }
        }
    }

