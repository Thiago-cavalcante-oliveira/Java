package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CondutorRepositorio extends JpaRepository <Condutor, Long> {
    @Query("select count(*)>0 from Condutor condutor where condutor.id = :id")
    public boolean checaId(@Param("id") Long id);

    @Query("select condutor from Condutor condutor where condutor.ativo = true ")
    public List<Condutor> listarAtivos();

    @Query("select count(*)>0 from Condutor condutor where condutor.cpf = :cpf ")
    public boolean cpfEmUso(@Param("cpf") String cpf);

    @Query("select count(*)>0 from Condutor condutor where condutor.telefone = :telefone ")
    public boolean TelefoneEmUso(@Param("telefone") String telefone);

    @Query(" select condutor from Condutor condutor")
    public List<Condutor> listar();

    @Query ("select count(*)>0 from Movimentacao movimentacao where movimentacao.condutor.id = :id")
    public boolean checaUso(@Param("id") Long id);

    @Query("select condutor.id from Condutor condutor where condutor.cpf = :cpf")
    public Long checaCpfRetornaIdCondutor(@Param("cpf")String cpf);

    @Query("select condutor.id from Condutor condutor where condutor.telefone = :telefone")
    public Long checaTelefoneRetornaIdCondutor(@Param("telefone")String telefone);
    @Query("SELECT count(*)>0 from Movimentacao movimentacao where movimentacao.condutor.id = :id and movimentacao.entrada is not null and movimentacao.saida is null ")
    public boolean checaCondutorMovimentacaoAberta(@Param("id")Long id);

}
