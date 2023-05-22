package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.CondutorRepositorio;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.InputMismatchException;
import java.util.Optional;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepositorio repository;
    @Autowired
    private MovimentacaoRepositorio movimentacaoRepositorio;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Condutor condutor) {
        if (condutor.getNome() == null || condutor.getNome().isEmpty() || condutor.getNome().isBlank()) {
            throw new RuntimeException("Nome não informado");
        } else if (condutor.getNome().length() > 150) {
            throw new RuntimeException("Vc ultrapassou o limite de 150 caracteres");
        } else if (condutor.getCpf() == null || condutor.getCpf().isEmpty() || condutor.getCpf().isBlank()) {
            throw new RuntimeException("cpf não informado");
        } else if (!isCPF(condutor.getCpf())) {
            throw new RuntimeException("CPF inválido.");
        } else if (condutor.getTelefone() == null || condutor.getTelefone().isEmpty() || condutor.getTelefone().isBlank()) {
            throw new RuntimeException("telefone não informado");
        } else if (!condutor.getTelefone().matches("^\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}$|^\\([0-9]{2}\\)[0-9]{4}-[0-9]{4}$")) {
            throw new RuntimeException("Telefone inválido. O formato dever ser (00)000-00 ou (11)11111-1111");
        } else if (repository.cpfEmUso(condutor.getCpf())) {
            throw new RuntimeException("cpf já está cadastrado.");
        } else if (repository.TelefoneEmUso(condutor.getTelefone())) {
            throw new RuntimeException("Telefone já está cadastrado em outro condutor.");
        } else
            repository.save(condutor);
    }

    public void Atualizar(final Long id, final Condutor condutor) {

        if (!id.equals(condutor.getId())) {
            throw new RuntimeException("Erro no Id do condutor");
        } else if (condutor.getNome() == null || condutor.getNome().isEmpty()) {
            throw new RuntimeException("Nome não informado");
        } else if (condutor.getCpf() == null || condutor.getCpf().isEmpty()) {
            throw new RuntimeException("cpf não informado");
        } else if (!isCPF(condutor.getCpf())) {
            throw new RuntimeException("CPF Iválido.");
        } else if (condutor.getTelefone() == null || condutor.getTelefone().isEmpty()) {
            throw new RuntimeException("telefone não informado");
        } else if (repository.cpfEmUso(condutor.getCpf())) {
            if (id != this.repository.checaCpfRetornaIdCondutor(condutor.getId())) {
                throw new RuntimeException("cpf já está cadastrado em outro condutor.");
            }
        } else if (repository.TelefoneEmUso(condutor.getTelefone())) {
            if (!id.equals(this.repository.checaTelefoneRetornaIdCondutor(condutor.getTelefone()))) {
                throw new RuntimeException("telefone já está cadastrado em outro condutor.");
            }
        } else if (!condutor.isAtivo()) {
            throw new RuntimeException("Não pode ser Inativo ao Atualizar.");
        }
        this.repository.save(condutor);
    }

    public void deletar(final Long id) {

        Condutor condutor = this.repository.findById(id).orElse(null);

        if (movimentacaoRepositorio.existsById(condutor.getId())) {
            if (movimentacaoRepositorio.checaMoviemntacaoAbertaSemSaida(id)) {
                throw new RuntimeException("Este condutor possui uma movimentacao em aberto, não pode ser excluido.");
            }
        }
        else if (movimentacaoRepositorio.existsById(condutor.getId())) {
            condutor.setAtivo(false);
            repository.save(condutor);

        }else{
            repository.delete(condutor);
        }
    }

    public Condutor buscaPorId(final Long id) {
        Condutor condutor = this.repository.findById(id).orElse(null);
        if (id == null) {
            throw new RuntimeException("Id não informado.");
        } else if (!this.repository.checaId(id)) {
            throw new RuntimeException("Id não existe na base de dados");
        }
        return condutor;
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
            return (false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }
}
