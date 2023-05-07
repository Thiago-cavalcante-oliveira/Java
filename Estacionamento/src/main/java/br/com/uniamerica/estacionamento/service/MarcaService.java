package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepositorio;
import br.com.uniamerica.estacionamento.repository.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepositorio repository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Marca marca) {
        if (marca.getNome() == null) {
            throw new RuntimeException("Nome não informado");
        } else if (repository.checaNome(marca.getNome())) {
            throw new RuntimeException("Nome já está cadastrado.");
        } else if (!marca.isAtivo()) {
            throw new RuntimeException("Não pode ser Inativo ao cadastrar.");
        } else
            repository.save(marca);
    }

    public void Atualizar(final Marca marca, final Long id) {
        if (id != marca.getId()) {
            throw new RuntimeException("Houve um erro: o id informado é diferente do ID em Banco");
        } else if (repository.checaId(id)) {
            throw new RuntimeException("ID não encontrado na base de dados");
        } else if (marca.getNome() == null) {
            throw new RuntimeException("Nome não informado.");
        } else if (repository.checaNome(marca.getNome())) {
            throw new RuntimeException("Este nome já está cadastrado.");
        } else if (!marca.isAtivo()) {
            throw new RuntimeException("Este item está desativado.");
        }
        repository.save(marca);
    }

    public Optional<Marca> buscaPorID(@RequestParam("id") final Long id) {
        if (id == null) {
            throw new RuntimeException("Id não informado.");
        } else if (repository.checaId(id)) {
            throw new RuntimeException("Id não existe na base de dados");
        }
        Optional<Marca> marca = this.repository.findById(id);
        return marca;
    }

    public void deletar(final Long id) {
        if (id == null) {
            throw new RuntimeException("ID não informado.");
        } else if (repository.checaId(id)) {
            throw new RuntimeException("ID não encontrado na base de dados.");
        } else if (repository.checaUso(id)) {
            Optional<Marca> marca = this.repository.findById(id);
            if (marca.isPresent()) {
                marca.get().setAtivo(false);
            } else {
                repository.deleteById(id);
            }
        }
    }
}
