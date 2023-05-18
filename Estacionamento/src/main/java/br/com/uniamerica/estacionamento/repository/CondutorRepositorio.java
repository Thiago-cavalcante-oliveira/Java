package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CondutorRepositorio extends JpaRepository <Condutor, Long> {
    @Query("select count(*)>0 from Condutor condutor where condutor.id = :id")
    public boolean checaId(@RequestParam("id") Long id);

    @Query("select condutor from Condutor condutor where condutor.ativo = true ")
    public List<Condutor> listarAtivos();

    @Query("select count(*)>0 from Condutor condutor where condutor.cpf = :cpf ")
    public boolean cpfEmUso(@RequestParam("cpf") String cpf);

    @Query("select count(*)>0 from Condutor condutor where condutor.telefone = :telefone ")
    public boolean TelefoneEmUso(@RequestParam("telefone") String telefone);

    @Query(" select condutor from Condutor condutor")
    public List<Condutor> listar();

    @Query ("select count(*)>0 from Movimentacao movimentacao where movimentacao.condutor.id = :id")
    public boolean checaUso(@RequestParam("id") Long id);


}
