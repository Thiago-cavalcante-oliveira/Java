package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepositorio;
import br.com.uniamerica.estacionamento.repository.MarcaRepositorio;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepositorio;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/movimentacao")

public class MovimentacaoController {
@Autowired
    private MovimentacaoRepositorio movimentacaoRepositorio;
    //public MovimentacaoController (MovimentacaoRepositorio movimentacaoRepositorio){
    //    this.movimentacaoRepositorio = movimentacaoRepositorio;
    //}
    @Autowired
    private MovimentacaoService service;
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

        return ResponseEntity.ok(movimentacaoRepositorio.listarAtivo()) ;
    }

    @GetMapping("/listaAtivaSemSaida")
    public ResponseEntity<?> listaAtivoSemSaida() {

        return ResponseEntity.ok(movimentacaoRepositorio.listarSemSaida());
    }


    @PostMapping
        public ResponseEntity<?> cadastraMovimentacao (@RequestBody final Movimentacao movimentacao){
      try {
          this.service.cadastrar(movimentacao);
          return ResponseEntity.ok("Carro estacionado");
      }
      catch (RuntimeException e){
          return ResponseEntity.badRequest().body("erro: " + e.getMessage());
      }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizaMovimentacao(@PathVariable("id") final Long id, @RequestBody final Movimentacao movimentacao) {
        try {

            this.service.atualizar(movimentacao);
            return ResponseEntity.ok("Movimentacao atualizada!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<?> finalizaEstacionamento(@RequestParam("id") Long id, @RequestBody LocalDateTime finalizar){

    return ResponseEntity.ok("Finalizado estacionamento.");

    }

    @PatchMapping
    public ResponseEntity<?> finalizarEstacionamento(@RequestParam("id") Long id,
                                                     @RequestBody Movimentacao sair){
        service.calculaTempoEstacionado(id, sair);
        service.calculaMulta(id, sair);
        return ResponseEntity.ok("finalizado");
    }


    @DeleteMapping ("/{id}")
    public ResponseEntity <?> inativar(@PathVariable("id") Long id){
        try {
            final Movimentacao valorBanco = this.movimentacaoRepositorio.findById(id).orElse(null);
            if (valorBanco == null ){
                throw new RuntimeException("Não foi possível identificar o registro");
            }
            valorBanco.setAtivo(false);
            this.movimentacaoRepositorio.delete(valorBanco);
            return ResponseEntity.ok("Registro Deletado");
        }
        catch (DataIntegrityViolationException e){
            return  ResponseEntity.internalServerError().body("Error"+e + e.getCause().getCause().getMessage());
        }
        catch (Exception e){
            final Movimentacao valorBanco = this.movimentacaoRepositorio.findById(id).orElse(null);
            if (valorBanco == null ){
                throw new RuntimeException("Não foi possível identificar o registro");
            }
            valorBanco.setAtivo(false);
            this.movimentacaoRepositorio.save(valorBanco);
            return ResponseEntity.ok("Registro Inativado");
        }


    }



}
