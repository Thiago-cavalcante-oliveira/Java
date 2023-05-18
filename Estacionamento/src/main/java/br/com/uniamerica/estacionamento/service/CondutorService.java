package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.CondutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.InputMismatchException;
import java.util.Optional;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepositorio repository;

    @Transactional(rollbackFor =  Exception.class)
    public void cadastrar(final Condutor condutor) {
        if (condutor.getNome() == null ||  condutor.getNome().isEmpty()) {
            throw new RuntimeException("Nome não informado");
        } else if ( condutor.getCpf() == null || condutor.getCpf().isEmpty()) {
            throw new RuntimeException("cpf não informado");
        } else if ( !isCPF(condutor.getCpf())){
            throw new RuntimeException("CPF inválido.");
        } else if (condutor.getTelefone() == null || condutor.getTelefone().isEmpty()) {
            throw new RuntimeException("telefone não informado");
        } else if (!condutor.getTelefone().matches( "^\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}$|^\\([0-9]{2}\\)[0-9]{4}-[0-9]{4}$")) {
            throw new RuntimeException("Telefone inválido");
        } else if (repository.cpfEmUso(condutor.getCpf())) {
            throw new RuntimeException("cpf já está cadastrado.");
        }
        else if (repository.TelefoneEmUso(condutor.getTelefone())) {
            throw new RuntimeException("TElefone já está cadastrado.");
        }
        else
            repository.save(condutor);
    }

    public void Atualizar(final Long id, final Condutor condutor){
        if(!id.equals(condutor.getId())){
            throw new RuntimeException("Erro no Id do condutor");
        }
        else if(condutor.getNome() == null || condutor.getNome().isEmpty()){
            throw new RuntimeException("Nome não informado");
        } else if (condutor.getCpf() == null || condutor.getCpf().isEmpty()){

                    throw new RuntimeException("cpf não informado");

        } else if (!isCPF(condutor.getCpf())) {
            throw new RuntimeException("CPF Iválido.");
        } else if (condutor.getTelefone() == null || condutor.getTelefone().isEmpty()){
            throw new RuntimeException("telefone não informado");
        }
        else if (repository.cpfEmUso(condutor.getCpf())) {
            if (condutor.getId() != id) {
                throw new RuntimeException("cpf já está cadastrado.");
            }
        }
        else if (repository.TelefoneEmUso(condutor.getTelefone())) {
            throw new RuntimeException("telefone já está cadastrado.");
        }
        else if (!condutor.isAtivo()) {
            throw new RuntimeException("Não pode ser Inativo ao Atualizar.");
        }else
            repository.save(condutor);
    }

    public void deletar(final Long id) {
        if (repository.checaUso(id)) {
            Optional<Condutor> optionalCondutor = this.repository.findById(id);
            if (optionalCondutor.isPresent()) {
                Condutor condutor = optionalCondutor.get();
                condutor.setAtivo(false);
                this.repository.save(condutor);
            } else {
                this.repository.deleteById(id);
            }
        }
    }
    public Optional<Condutor> BuscarPorID(final Long id){
        if(id == null){
            throw new RuntimeException("Você não informou um id para consultar.");
        }
        else if (!repository.checaId(id)){
            throw new RuntimeException("ID não localizado.");
        }
        else{
            Optional<Condutor> condutor = this.repository.findById(id);
            return condutor;}
    }




    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
