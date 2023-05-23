package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ConfiguracaoRepositorio extends JpaRepository <Configuracao, Long> {

    @Query("select count(*)>0 from Movimentacao movimentacao where movimentacao.configuracao.id = :id and movimentacao.entrada is not null" )
    public boolean checauso(@Param("id") Long id);
    @Query("select count(*)>0 from Movimentacao movimentacao where movimentacao.configuracao.id = :id and movimentacao.entrada is not null and movimentacao.saida is null " )
    public boolean checaConfiguracaoMovimentacaoAberta(@Param("id") Long id);

    @Query("select configuracao from Configuracao configuracao where  configuracao.ativo = true")
    List<Configuracao> checaListaAtivaConfiguracao();

    @Query("select count(*)>0 from Configuracao configuracao where configuracao.ativo = true")
    public boolean checaConfiguracaoAtiva();


    @Query("SELECT configuracao1 FROM Configuracao configuracao1 WHERE configuracao1.id = (SELECT MAX(configuracao2.id) FROM Configuracao configuracao2)")
    public Configuracao buscaUltimaConfiguracaoCadastrada();
}
