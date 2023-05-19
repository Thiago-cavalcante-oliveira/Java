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
        } else if (repository.existsById(movimentacao.getVeiculo().getId())
                && repository.checaCarroEstacionado(movimentacao.getVeiculo().getId())) {
            throw new RuntimeException("Veiculo já está estacionado.");
        } else {
            repository.save(movimentacao);
        }
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
        } else {
            repository.save(movimentacao);
        }
    }

    public void deletar(@PathVariable("id") Long id) {
        if (id == null) {
            throw new RuntimeException("ID não informado.");
        } else {
            Optional<Movimentacao> movimentacao = this.repository.findById(id);
            if (movimentacao.isPresent()) {
                Movimentacao checamovimentacao = movimentacao.get();
                checamovimentacao.setAtivo(false);
                this.repository.save(checamovimentacao);
            }

        }
    }

    public void calculaTempoEstacionado(Long id, Movimentacao saida) {
        Long calculaDesconto, tempoEstacionado, tempoPagoCondutor;
        Configuracao configuracao = this.configuracaoRepositorio.getById(1L);
        Movimentacao movimentacao = this.repository.getById(id);
        movimentacao.setSaida(saida.getSaida());
        Condutor condutor = movimentacao.getCondutor();

        Duration duracaoEstacionado = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());
        tempoEstacionado = duracaoEstacionado.toMinutes();
        movimentacao.setTempo(tempoEstacionado);
        tempoPagoCondutor = condutor.getTempoPagoEmMinuto();
        tempoPagoCondutor += tempoEstacionado;

        BigDecimal valorEstacionado = new BigDecimal(tempoEstacionado);

        movimentacao.setValorHora(valorEstacionado.multiply(configuracao.getValorHora().divide(BigDecimal.valueOf(60))));

        calculaDesconto = (tempoPagoCondutor / configuracao.getTempoParaGerarDesconto())*configuracao.getTempoDeCreditoDesconto();
        calculaDesconto = calculaDesconto/60;
        tempoPagoCondutor = (tempoPagoCondutor % configuracao.getTempoParaGerarDesconto());

        condutor.setTempoDesconto(calculaDesconto);
        condutor.setTempoPagoEmMinuto(tempoPagoCondutor);
        condutorRepositorio.save(condutor);
        repository.save(movimentacao);
    }

    public String calculaMulta(Long id, Movimentacao sair) {
        Long multaEntrada = 0L, multaSaida = 0L, somaTempo = 0L;
        Movimentacao movimentacao = this.repository.getById(id);
        movimentacao.setSaida(sair.getSaida());
        repository.save(movimentacao);
        Condutor condutor = this.condutorRepositorio.getReferenceById(movimentacao.getCondutor().getId());

        LocalTime entrada = movimentacao.getEntrada().toLocalTime();
        LocalTime saida = movimentacao.getSaida().toLocalTime();
        Configuracao configuracao = this.configuracaoRepositorio.getById(1L);

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
        if (somaTempoMulta.equals(0)){
            multiplicadorMulta.add(BigDecimal.valueOf(0));
        }

        movimentacao.setTempoDesconto(condutor.getTempoDesconto());
        movimentacao.setValorDesconto(configuracao.getValorHora().multiply(BigDecimal.valueOf(condutor.getTempoDesconto())));

        movimentacao.setValorTotal(valorMulta.add(movimentacao.getValorHora()).subtract(movimentacao.getValorDesconto()));

        movimentacao.setTempoMulta(somaTempo);
        movimentacao.setValorHoraMulta(configuracao.getValorMinutoMulta().multiply(multiplicadorMulta));
        repository.save(movimentacao);

        return "----------------------------------\n"+
                "+++++++CUPOM FISCAL+++++++\n" +
                "|CONDUTOR: "+ movimentacao.getCondutor().getNome()+"\n" +
                "|VEICULO: " + movimentacao.getVeiculo().getModelo().getNome() + " PLACA: " + movimentacao.getVeiculo().getPlaca()+ "\n" +
                "__________________________________\n" +
                "DATA DE ENTRADA: " + movimentacao.getEntrada().getDayOfMonth() +"/" + movimentacao.getEntrada().getMonthValue() + "/" + movimentacao.getEntrada().getYear() +
                "  HORA DE ENTRADA: " + movimentacao.getEntrada().getHour() + ":" + movimentacao.getEntrada().getMinute() +"\n" +
                "\nDATA DE SAÍDA: " + movimentacao.getSaida().getDayOfMonth() + "/" + movimentacao.getSaida().getMonthValue()+ "/" + movimentacao.getSaida().getYear() +"\n"+
                "  HORA DE SAIDA: " + movimentacao.getSaida().getHour()+":"+movimentacao.getSaida().getMinute() +
                "----------------------------------\n" +
                "|TEMPO ESTACIONADO EM MINUTOS: " + movimentacao.getTempo() + "\n"+
                "|VALOR DO TEMPO ESTACIONADO: R$ " + movimentacao.getValorHora() + "\n" +
                "|TEMPO DE MULTA GERADO: " + movimentacao.getTempoMulta() +"\n"+
                "|VALOR DA MULTA: R$ " + movimentacao.getValorMulta() +"\n"+
                "|VALOR DE DESCONTO: R$ " + movimentacao.getValorDesconto() +"\n"+
                "|VALOR TOTAL: R$ " + movimentacao.getValorTotal()+ "\n"+
                "-----------------------------------\n"+
                "==========VOLTE SEMPRE=============";


    }
}
