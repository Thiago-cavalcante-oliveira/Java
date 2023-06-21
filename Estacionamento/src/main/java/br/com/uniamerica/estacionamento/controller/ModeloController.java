package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.ModeloRepositorio;
import br.com.uniamerica.estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping(value = "/api/modelo")

public class ModeloController {
    @Autowired
    private ModeloService service;
    @Autowired
    private ModeloRepositorio repository;


    @GetMapping
    public ResponseEntity<?> buscarModelo(@RequestParam("id") final Long id) {

        try {
            return ResponseEntity.ok(service.BuscarPorID(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<?> buscaListaModelo() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/listaativo")
    public ResponseEntity<?> buscaListaAtivo() {
        return ResponseEntity.ok(repository.modeloAtivo());
    }

    @PostMapping
    public ResponseEntity<?> cadastrarModelo(@RequestBody Modelo modelo) {
        try {
            this.service.cadastrarModelo(modelo);
            return ResponseEntity.ok("Modelo cadastrado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizaModelo( @PathVariable final Long id, @RequestBody Modelo modelo) {
        try {
            this.service.AtualizarModelo(modelo, id);

            return ResponseEntity.ok("Modelo Atualizado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Erro inesperado." + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluiModelo(@PathVariable("id") final Long id) {

        try {

            this.service.deletar(id);
            return ResponseEntity.ok("Modelo deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }


}
