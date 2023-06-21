package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface MovimentacaoRepositorio extends JpaRepository <Movimentacao, Long> {
    @Query("select movi from Movimentacao movi where movi.ativo = true")
    public List<Movimentacao> listarAtivo();
    @Query("select count(*)>0 from Movimentacao movimentacao where movimentacao.id = :id")
    public boolean checaMovimentacao(@Param("id")Long id);

    @Query("select count(*)>0 from Movimentacao movimentcao where movimentcao.id = :id and movimentcao.entrada is not null and movimentcao.saida is not null")
    public boolean checaMovimentacaoFinalizada(@Param("id")Long id);

    @Query("select count (*)>0 from Movimentacao movimentacao where movimentacao.condutor.id = :id and movimentacao.saida = null ")
    public boolean checaMoviemntacaoAbertaSemSaida(@Param("id") Long id);

    @Query("select movi from Movimentacao movi where movi.ativo = true and movi.saida = null")
    public List<Movimentacao> listarSemSaida();
    @Query("select count(*)>0 from Movimentacao movi where movi.veiculo.id = :id  and movi.saida is null")
    public boolean checaCarroEstacionado(@Param("id") Long id);

    @Query("select movi.id from Movimentacao movi where movi.id = :id  and movi.saida is null")
    public Long checaCarroEstacionadoAtualizar(@Param("id") Long id);

    @Query("select count(*)>0 from Movimentacao movimentacao where movimentacao.condutor.id = :id")
    public boolean checaCondutorMovimentacao(@Param("id") Long id);
}
