package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepositorio;
import br.com.uniamerica.estacionamento.repository.MarcaRepositorio;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepositorio;
import org.apache.catalina.connector.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/movimentacao")

public class MovimentacaoController {

    private MovimentacaoRepositorio movimentacaoRepositorio;
    public MovimentacaoController (MovimentacaoRepositorio movimentacaoRepositorio){
        this.movimentacaoRepositorio = movimentacaoRepositorio;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscaMovimentacao(@RequestParam("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepositorio.findById(id).orElse(null);
        return movimentacao == null ?
                ResponseEntity.badRequest().body("Valor não encontrado") :
                ResponseEntity.ok(movimentacao);
    }
    @GetMapping("/lista")
    public ResponseEntity <?> buscaLista(){
        return ResponseEntity.ok(this.movimentacaoRepositorio.findAll());
    }

    @GetMapping("/listaAtiva")
    public ResponseEntity<?> listaAtivo(){
        List<Movimentacao> listaMovimentacao = this.movimentacaoRepositorio.findAll();
        List <Movimentacao> checaLista = new ArrayList<>();
        for (Movimentacao valor: listaMovimentacao
             ) {
            if(valor.isAtivo()){
                checaLista.add(valor);
            }
              }
        return ResponseEntity.ok(checaLista) ;
    }

    @GetMapping("/listaAtivaSemSaida")
    public ResponseEntity<?> listaAtivoSemSaida() {
        List<Movimentacao> listaMovimentacao = this.movimentacaoRepositorio.findAll();
        List<Movimentacao> checaLista = new ArrayList<>();
        for (Movimentacao valor : listaMovimentacao
        ) {
            if (valor.getSaida().equals(null) && valor.isAtivo()) {
                checaLista.add(valor);
            }

        }
        return ResponseEntity.ok(listaMovimentacao);
    }


    @PostMapping
        public ResponseEntity<?> cadastraMovimentacao (@RequestBody final Movimentacao movimentacao){
        this.movimentacaoRepositorio.save(movimentacao);
        return ResponseEntity.ok("Carro estacionado");
    }

    @PutMapping
    public ResponseEntity<?> atualizaMovimentacao(@RequestParam("id") final Long id,
                                           @RequestBody final Movimentacao movimentacao) {
        try {
            final Movimentacao movimentacaoBanco = this.movimentacaoRepositorio.findById(id).orElse(null);
            if (movimentacaoBanco == null || !movimentacaoBanco.getId().equals(movimentacao.getId())) {
                throw new RuntimeException("Não foi possível localizar");
            }
            this.movimentacaoRepositorio.save(movimentacao);
            return ResponseEntity.ok("Marca atualizada!");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error");
        }
    }


        @DeleteMapping ("/{id}")

        public ResponseEntity <?> inativar(
            @PathVariable("id") Long id

    ){
        try {

            final Movimentacao movimentacaoBanco = this.movimentacaoRepositorio.findById(id).orElse(null);

            if (movimentacaoBanco == null ){
                throw new RuntimeException("Não foi possível identificar o registro");
            }
            movimentacaoBanco.setAtivo(false);
            this.movimentacaoRepositorio.save(movimentacaoBanco);
            return ResponseEntity.ok("Registro deletado");
        }
        catch (DataIntegrityViolationException e){
            return  ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error");
        }

    }



}
