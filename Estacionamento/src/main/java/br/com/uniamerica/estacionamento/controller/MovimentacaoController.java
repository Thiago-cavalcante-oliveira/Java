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
@CrossOrigin
@RestController
@RequestMapping(value = "/api/movimentacao")

public class MovimentacaoController {
    @Autowired
    private MovimentacaoRepositorio movimentacaoRepositorio;
    //public MovimentacaoController (MovimentacaoRepositorio movimentacaoRepositorio){
    //    this.movimentacaoRepositorio = movimentacaoRepositorio;
    //}
    @Autowired
    private MovimentacaoService service;

    @GetMapping
    public ResponseEntity<?> buscaMovimentacao(@RequestParam("id") final Long id) {
        final Movimentacao movimentacao = this.movimentacaoRepositorio.findById(id).orElse(null);
        try {
            return ResponseEntity.ok(movimentacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao localizar o ID.");
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<?> buscaLista() {
        return ResponseEntity.ok(this.movimentacaoRepositorio.findAll());
    }

    @GetMapping("/listaAtiva")
    public ResponseEntity<?> listaAtivo() {

        return ResponseEntity.ok(movimentacaoRepositorio.listarAtivo());
    }

    @GetMapping("/listaAtivaSemSaida")
    public ResponseEntity<?> listaAtivoSemSaida() {

        return ResponseEntity.ok(movimentacaoRepositorio.listarSemSaida());
    }


    @PostMapping
    public ResponseEntity<?> cadastraMovimentacao(@RequestBody final Movimentacao movimentacao) {
        try {
            this.service.cadastrar(movimentacao);
            return ResponseEntity.ok("Carro estacionado");
        } catch (RuntimeException e) {
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> finalizarEstacionamento(@PathVariable("id") Long id,
                                                     @RequestBody Movimentacao sair) {
        try {
            service.calculaTempoEstacionado(id, sair);
            return ResponseEntity.ok(service.calculaMulta(id, sair));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> inativar(@PathVariable("id") Long id) {
        try {

            return ResponseEntity.ok(this.service.deletarMovimentacao(id));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e + e.getCause().getCause().getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}
