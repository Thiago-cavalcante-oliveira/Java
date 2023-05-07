package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.CondutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepositorio repository;

    @Transactional(rollbackFor =  Exception.class)
    public void cadastrar(final Condutor condutor) {
        if (condutor.getNome() == null) {
            throw new RuntimeException("Nome não informado");
        } else if (condutor.getCpf() == null) {
            throw new RuntimeException("cpf não informado");
        } else if (condutor.getCpf().matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}" +
                "|^[0-9]{11}$")) {
            throw new RuntimeException("CPF inválido.");
        } else if (condutor.getTelefone() == null) {
            throw new RuntimeException("telefone não informado");
        } else if (condutor.getTelefone().matches("([0-9]{2}\\)[0-9]{5}\\-[0-9]{4}" +
                "|^[0-9]{11}$)")) ;{
            throw new RuntimeException("Telefone inválido.");
        }
        else if (repository.cpfEmUso(condutor.getCpf())) {
            throw new RuntimeException("cpf já está cadastrado.");
        }
        else if (repository.TelefoneEmUso(condutor.getTelefone())) {
            throw new RuntimeException("cpf já está cadastrado.");
        }
        else if (!condutor.isAtivo()) {
            throw new RuntimeException("Não pode ser Inativo ao cadastrar.");
        }else
            repository.save(condutor);
    }

    public void Atualizar(final Long id, final Condutor condutor){
        if(id != condutor.getId()){
            throw new RuntimeException("Erro no Id do condutor");
        }
        else if(condutor.getNome() == null){
            throw new RuntimeException("Nome não informado");
        } else if (condutor.getCpf() == null){
            throw new RuntimeException("cpf não informado");
        } else if (condutor.getTelefone() == null){
            throw new RuntimeException("telefone não informado");
        }
        else if (repository.cpfEmUso(condutor.getCpf())) {
            throw new RuntimeException("cpf já está cadastrado.");
        }
        else if (repository.TelefoneEmUso(condutor.getTelefone())) {
            throw new RuntimeException("cpf já está cadastrado.");
        }
        else if (!condutor.isAtivo()) {
            throw new RuntimeException("Não pode ser Inativo ao cadastrar.");
        }else
            repository.save(condutor);
    }

    public void deletar(final Long id) {
        if (id == null) {
            throw new RuntimeException("ID não informado.");
        } else if (repository.checaId(id)) {
            throw new RuntimeException("ID não encontrado na base de dados.");
        } else if (repository.checaUso(id)) {
            Optional<Condutor> condutor = this.repository.findById(id);
            if (condutor.isPresent()) {
                condutor.get().setAtivo(false);
            } else {
                repository.deleteById(id);
            }
        }
    }
    public Optional<Condutor> BuscarPorID(final Long id){
        if(id == null){
            throw new RuntimeException("Você não informou um id para consultar.");
        }
        else if (repository.checaId(id)){
            throw new RuntimeException("ID não localizado.");
        }
        else{
            Optional<Condutor> condutor = this.repository.findById(id);
            return condutor;}
    }
}
