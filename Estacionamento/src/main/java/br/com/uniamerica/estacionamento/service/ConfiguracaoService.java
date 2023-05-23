package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ConfiguracaoService {
    @Autowired
    private ConfiguracaoRepositorio repository;

    @Transactional
    public void cadastrar(final Configuracao configuracao) {
        if (configuracao.getInicioExpediente() == null) {
            throw new RuntimeException("Informar horário de abertura.");
        } else if (configuracao.getFimExpediente() == null) {
            throw new RuntimeException("Informar horário de fechamento.");
        } else if (configuracao.getTempoDeCreditoDesconto() == null) {
            throw new RuntimeException("Informar Tempo para para crédito de desconto.");
        } else if (configuracao.getTempoParaGerarDesconto() == null) {
            throw new RuntimeException("Informar tempo para gerar o desconto.");
        } else if (configuracao.getValorHora() == null || configuracao.getValorHora().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Informar valor da hora estacionada.");
        } else if (configuracao.getValorMinutoMulta() == null || configuracao.getValorMinutoMulta().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Informar valor de multa por minuto.");
        } else if (configuracao.getVagasCarro() < 0) {
            throw new RuntimeException("Informar vagas de carro.");
        } else if (configuracao.getVagasVan() < 0) {
            throw new RuntimeException("Informar vagas de van.");
        } else if (configuracao.getVagasMoto() < 0) {
            throw new RuntimeException("Informar vagas de moto.");
        } else {
            configuracao.setVersao(configuracao.getVersao() + 1);
            this.repository.save(configuracao);
        }
    }

    public void atualizar(final Long id, final Configuracao configuracao) {

        if (id != configuracao.getId()) {
            throw new RuntimeException("Erro no ID");
        } else if (configuracao.getInicioExpediente() == null) {
            throw new RuntimeException("Informar horário de abertura.");
        } else if (configuracao.getFimExpediente() == null) {
            throw new RuntimeException("Informar horário de fechamento.");
        } else if (configuracao.getTempoDeCreditoDesconto() == null) {
            throw new RuntimeException("Informar Tempo para para crédito de desconto.");
        } else if (configuracao.getTempoParaGerarDesconto() == null) {
            throw new RuntimeException("Informar tempo para gerar o desconto.");
        } else if (configuracao.getValorHora() == null || configuracao.getValorHora().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Informar valor da hora estacionada.");
        } else if (configuracao.getValorMinutoMulta() == null || configuracao.getValorMinutoMulta().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Informar valor de multa por minuto.");
        } else if (configuracao.getVagasCarro() < 0) {
            throw new RuntimeException("Informar vagas de carro.");
        } else if (configuracao.getVagasVan() < 0) {
            throw new RuntimeException("Informar vagas de van.");
        } else if (configuracao.getVagasMoto() < 0) {
            throw new RuntimeException("Informar vagas de moto.");
        } else {

            int versao = this.repository.getById(id).getVersao();
            versao += 1;
            configuracao.setVersao(versao);
            this.repository.save(configuracao);
        }
    }

    public String deletar(Long id) {
        Configuracao configuracao = this.repository.findById(id).orElse(null);
        if (id == null) {
            throw new RuntimeException("ID da configuracao nao informado");
        } else if (!this.repository.existsById(id)) {
            throw new RuntimeException("ID da configuracao não localizado no base de dados");
        } else if (this.repository.checaConfiguracaoMovimentacaoAberta(configuracao.getId())) {
            throw new RuntimeException("Configuracao não pode ser excluida pois está ligado a uma movimentação ativa no momento.");
        } else if (this.repository.existsById(id) && this.repository.checauso(id)) {
            configuracao.setAtivo(false);
            this.repository.save(configuracao);
            return "Configuracao inativada, pois está vinculada a uma movimentacao.";
        }
        this.repository.delete(configuracao);
        return "Configuracao deletada.";
    }


}
