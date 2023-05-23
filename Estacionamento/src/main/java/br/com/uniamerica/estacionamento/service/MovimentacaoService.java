package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepositorio;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepositorio;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepositorio;
import br.com.uniamerica.estacionamento.repository.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepositorio repository;
    @Autowired
    private CondutorRepositorio condutorRepositorio;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;
    @Autowired
    private ConfiguracaoRepositorio configuracaoRepositorio;

    public void cadastrar(final Movimentacao movimentacao) {
        if (movimentacao.getCondutor() == null) {
            throw new RuntimeException("Condutor nao informado.");
        } else if (!condutorRepositorio.existsById(movimentacao.getCondutor().getId())) {
            throw new RuntimeException("Condutor não encontrado no banco de dados");
        } else if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException("veiculo nao informado.");
        } else if (!veiculoRepositorio.existsById(movimentacao.getVeiculo().getId())) {
            throw new RuntimeException("Veiculo não encontrado no banco de dados.");
        } else if (movimentacao.getEntrada() == null) {
            throw new RuntimeException("Entrada nao informado.");
        } else if (this.repository.checaCarroEstacionado(movimentacao.getVeiculo().getId())) {
            throw new RuntimeException("Veiculo já está estacionado.");
        }
        repository.save(movimentacao);

    }

    public void atualizar(final Movimentacao movimentacao) {
        if (movimentacao.getCondutor() == null) {
            throw new RuntimeException("Condutor nao informado.");
        } else if (!condutorRepositorio.existsById(movimentacao.getCondutor().getId())) {
            throw new RuntimeException("Condutor não encontrado no banco de dados.");
        } else if (!condutorRepositorio.getById(movimentacao.getCondutor().getId()).isAtivo()) {
            throw new RuntimeException("Condutor inativo.");
        } else if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException("veiculo nao informado.");
        } else if (veiculoRepositorio.existsById(movimentacao.getVeiculo().getId())) {
            throw new RuntimeException("VEiculo não encontrado no banco de dados.");
        } else if (!veiculoRepositorio.getById(movimentacao.getVeiculo().getId()).isAtivo()) {
            throw new RuntimeException("Veiculo inativo");
        } else if (movimentacao.getEntrada() == null) {
            throw new RuntimeException("Entrada nao informado.");
        } else if (repository.existsById(movimentacao.getVeiculo().getId())
                && movimentacao.getSaida() == null) {
            throw new RuntimeException("Veiculo já está estacionado.");
        }
        repository.save(movimentacao);

    }

    public void deletar(@RequestParam("id") Long id) {
        Movimentacao movimentacao = this.repository.findById(id).orElse(null);
        if (id == null) {
            throw new RuntimeException("ID não informado.");
        } else if (!repository.checaMovimentacao(id)) {
            throw new RuntimeException("Id da movimentacao não localizado");
        } else if (!repository.checaMoviemntacaoAbertaSemSaida(id)) {
            movimentacao.setAtivo(false);
            this.repository.save(movimentacao);
        }
        repository.delete(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class)
    public void calculaTempoEstacionado(Long id, Movimentacao saida) {
        if (!configuracaoRepositorio.checaConfiguracaoAtiva()) {
            throw new RuntimeException("Não há uma Configuracao cadastrada.");
        }
        Long calculaDesconto, calculaHoraEstacionado, tempoEstacionado, tempoPagoCondutor;
        Configuracao configuracao = this.configuracaoRepositorio.buscaUltimaConfiguracaoCadastrada();
        Movimentacao movimentacao = this.repository.getById(id);
        movimentacao.setSaida(saida.getSaida());
        Condutor condutor = movimentacao.getCondutor();

        if (movimentacao.getSaida() == null) {
            throw new RuntimeException("Vc precisa informar a data e hora da saída.");
        } else if (movimentacao.getSaida().isBefore(movimentacao.getEntrada())) {
            throw new RuntimeException("A saída não pode ser anterior a entrada.");
        } else if (!configuracaoRepositorio.checaConfiguracaoAtiva()) {
            throw new RuntimeException("Nã há uma configuracao ativa, favor configurar o sistemas antes de concluir o estacionamento.");
        }

        Duration duracaoEstacionado = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());
        tempoEstacionado = duracaoEstacionado.toMinutes();
        movimentacao.setTempo(tempoEstacionado);
        tempoPagoCondutor = condutor.getTempoPagoEmMinuto();
        tempoPagoCondutor += tempoEstacionado;

        BigDecimal valorEstacionado = new BigDecimal(tempoEstacionado);
        movimentacao.setValorHora(valorEstacionado.multiply(configuracao.getValorHora().divide(BigDecimal.valueOf(60))));
        calculaHoraEstacionado = tempoPagoCondutor / 60;

        calculaDesconto = (calculaHoraEstacionado / configuracao.getTempoParaGerarDesconto()) * configuracao.getTempoDeCreditoDesconto();
        //calculaDesconto = calculaDesconto/60;
        tempoPagoCondutor = (tempoPagoCondutor % configuracao.getTempoParaGerarDesconto());

        condutor.setTempoPagoEmHora(calculaHoraEstacionado);
        condutor.setTempoPagoEmMinuto(tempoPagoCondutor);
        condutor.setTempoDesconto(calculaDesconto);
        condutorRepositorio.save(condutor);
        repository.save(movimentacao);
    }

    @Transactional(rollbackFor = Exception.class)
    public String calculaMulta(Long id, Movimentacao sair) {
        Long multaEntrada = 0L, multaSaida = 0L, somaTempo = 0L;
        Movimentacao movimentacao = this.repository.getById(id);
        movimentacao.setSaida(sair.getSaida());
        repository.save(movimentacao);
        Condutor condutor = this.condutorRepositorio.getReferenceById(movimentacao.getCondutor().getId());

        LocalTime entrada = movimentacao.getEntrada().toLocalTime();
        LocalTime saida = movimentacao.getSaida().toLocalTime();
        Configuracao configuracao = this.configuracaoRepositorio.buscaUltimaConfiguracaoCadastrada();
        movimentacao.setConfiguracao(configuracao);
        movimentacao.setVersao(configuracao.getVersao());
        repository.save(movimentacao);

        int dias = 0, ano = 0;

        ano = movimentacao.getSaida().getYear() - movimentacao.getEntrada().getYear();
        if (ano > 0) {
            dias += movimentacao.getSaida().getDayOfYear() + (ano * 365) - movimentacao.getEntrada().getDayOfYear();
        } else {

            dias += movimentacao.getSaida().getDayOfYear() - movimentacao.getEntrada().getDayOfYear();
        }

        if (entrada.isBefore(configuracao.getInicioExpediente())) {
            Duration diferencaEntrada = Duration.between(entrada, configuracao.getInicioExpediente());
            somaTempo += diferencaEntrada.toMinutes();
        } else if (saida.isAfter(configuracao.getFimExpediente())) {
            Duration diferencaSaida = Duration.between(configuracao.getFimExpediente(), saida);
            somaTempo = diferencaSaida.toMinutes();
        }
        if (dias > 0) {
            Long tempoExpediente = Duration.between(configuracao.getInicioExpediente(), configuracao.getFimExpediente()).toMinutes();
            somaTempo += dias * 24 * 60 - tempoExpediente * dias;
        }

        BigDecimal somaTempoMulta = new BigDecimal(somaTempo);
        BigDecimal multiplicadorMulta = new BigDecimal(60);

        BigDecimal valorMulta = somaTempoMulta.multiply(configuracao.getValorMinutoMulta());
        movimentacao.setValorMulta(valorMulta);
        if (somaTempoMulta.equals(0)) {
            multiplicadorMulta.add(BigDecimal.valueOf(0));
        }

        movimentacao.setTempoDesconto(condutor.getTempoDesconto());
        movimentacao.setValorDesconto(configuracao.getValorHora().multiply(BigDecimal.valueOf(condutor.getTempoDesconto())));

        movimentacao.setValorTotal(valorMulta.add(movimentacao.getValorHora()).subtract(movimentacao.getValorDesconto()));

        movimentacao.setTempoMulta(somaTempo);
        movimentacao.setValorHoraMulta(configuracao.getValorMinutoMulta().multiply(multiplicadorMulta));

        repository.save(movimentacao);

        return "----------------------------------\n" +
                "+++++++CUPOM FISCAL+++++++\n" +
                "|CONDUTOR: " + movimentacao.getCondutor().getNome() + "\n" +
                "|VEICULO: " + movimentacao.getVeiculo().getModelo().getNome() + " PLACA: " + movimentacao.getVeiculo().getPlaca() + "\n" +
                "|TEMPO PARA USO EM DESCONTO: " + movimentacao.getCondutor().getTempoDesconto() + "\n" +
                "__________________________________\n" +
                "DATA DE ENTRADA: " + movimentacao.getEntrada().getDayOfMonth() + "/" + movimentacao.getEntrada().getMonthValue() + "/" + movimentacao.getEntrada().getYear() +
                "  HORA DE ENTRADA: " + movimentacao.getEntrada().getHour() + ":" + movimentacao.getEntrada().getMinute() + "\n" +
                " \nDATA DE SAÍDA: " + movimentacao.getSaida().getDayOfMonth() + "/" + movimentacao.getSaida().getMonthValue() + "/" + movimentacao.getSaida().getYear() +
                "|HORA DE SAIDA: " + movimentacao.getSaida().getHour() + ":" + movimentacao.getSaida().getMinute() + "\n" +
                "----------------------------------\n" +
                "|TEMPO ESTACIONADO: " + movimentacao.getTempo() / 60 + " HORAS+" + "\n" +
                "|VALOR DA HORA NORMAL: R$ " + configuracao.getValorHora() + "\n" +
                "|VALOR DO TEMPO ESTACIONADO: R$ " + movimentacao.getValorHora() + "\n" +
                "|TEMPO DE MULTA GERADO EM MINUTOS: " + movimentacao.getTempoMulta() + "\n" +
                "|VALOR DA MULTA: R$ " + movimentacao.getValorMulta() + "\n" +
                "|VALOR DE DESCONTO GERADO: R$ " + movimentacao.getValorDesconto() + "\n" +
                "|VALOR TOTAL: R$ " + movimentacao.getValorTotal() + "\n" +
                "-----------------------------------\n" +
                "==========VOLTE SEMPRE=============";


    }

    @Transactional
    public String deletarMovimentacao(Long id) {
        Movimentacao movimentacao = this.repository.findById(id).orElse(null);
        if (movimentacao == null) {
            throw new RuntimeException("ID da Movimentacao não localizado no base de dados");
        } else {
            if (this.repository.checaMovimentacaoFinalizada(id)) {
                movimentacao.setAtivo(false);
                this.repository.save(movimentacao);
                return "movimentacao inativada, pois esta movimentacao está concluida.";

            } else {

                this.repository.delete(movimentacao);
                return "movimentacao deletada.";
            }
        }
    }
}
