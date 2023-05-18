package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepositorio;
import br.com.uniamerica.estacionamento.repository.ModeloRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ModeloService  {
    @Autowired
    private ModeloRepositorio repository;
    @Autowired
    private MarcaRepositorio marcaRepositorio;

    @Transactional(rollbackFor = Exception.class)//abre uma transacao dentro de uma transacao
    public void cadastrarModelo(final Modelo modelo){
        if (modelo.getNome() == null || modelo.getNome().isEmpty()){
            throw new RuntimeException("Nome do condutor não informado");
        } else if (repository.checaNomeModelo(modelo.getNome())){
                throw new RuntimeException("Modelo já está cadastrado.");
        } else if (!marcaRepositorio.existsById(modelo.getMarca().getId())) {
            throw new RuntimeException("O ID da Marca não está cadastrado.");
        }
        repository.save(modelo);
    }


    public void AtualizarModelo(final Modelo modelo, final Long id){
        if (id != modelo.getId()){
            throw new RuntimeException("Houve um erro: o id informado é diferente do ID do modelo");
        }
        else if (!repository.ChecaId(id)) {
            throw new RuntimeException("ID não encontrado na base de dados");
        }
        else if(modelo.getNome() == null || modelo.getNome().isEmpty()){
            throw new RuntimeException("Nome do modelo não informado.");
        }
        else if (!marcaRepositorio.existsById(modelo.getMarca().getId())) {
            throw new RuntimeException("O ID da Marca não existe");
        }
        else if (repository.checaNomeModelo(modelo.getNome())) {
            throw new RuntimeException("Este modelo já está cadastrado.");
        }
        else if (!modelo.isAtivo()) {
            throw new RuntimeException("Este modelo está desativado.");
           }
        repository.save(modelo);
    }

    public Optional<Modelo> BuscarPorID(final Long id){
        if(id == null){
            throw new RuntimeException("Você não informou um id para consultar.");
        }
        else if (repository.ChecaId(id)){
            throw new RuntimeException("ID não localizado.");
        }
        else{
       Optional<Modelo> modelo = this.repository.findById(id);
        return modelo;}
    }

    public void deletar(final Long id) {
        Modelo modelo = this.repository.findById(id).orElse(null);
        if (id == null) {
            throw new RuntimeException("ID não informado.");
        } else if (!repository.ChecaId(id)) {
            throw new RuntimeException("ID não encontrado na base de dados.");
        } else if (repository.checaUso(id)) {
                modelo.setAtivo(false);
                repository.save(modelo);
            } else {
                repository.delete(modelo);
            }
        }
            }



