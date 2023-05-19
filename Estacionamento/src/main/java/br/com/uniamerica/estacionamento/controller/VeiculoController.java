package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepositorio;
import br.com.uniamerica.estacionamento.service.MarcaService;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/veiculo")

public class VeiculoController {
    @Autowired
    private VeiculoService service;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    public VeiculoController(VeiculoRepositorio veiculoRepositorio) {
        this.veiculoRepositorio = veiculoRepositorio;
    }

    @GetMapping
    public ResponseEntity<?> buscaVeiculoPorId(@RequestParam("id") final Long id) {
        try {
            return ResponseEntity.ok(service.BuscarPorID(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro:" + e.getMessage());
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<?> buscaLista() {
        return ResponseEntity.ok(this.veiculoRepositorio.findAll());
    }

    @GetMapping("/listaativo")
    public ResponseEntity<?> buscaListaAtivo() {
        return ResponseEntity.ok(veiculoRepositorio.listarAtivos());
    }

    @PostMapping
    public ResponseEntity<?> cadastraVeiculo(@RequestBody final Veiculo veiculo) {
        try {
            service.cadastrar(veiculo);

            return ResponseEntity.ok("Cadastrado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestParam("id") final Long id,
                                       @RequestBody final Veiculo veiculo) {
        try {
            this.service.Atualizar(id, veiculo);
            return ResponseEntity.ok("Veiculo atualizado.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> atualizaVeiculo(@RequestParam final Long id) {
        try {
            this.service.deletar(id);
            return ResponseEntity.ok("Veiculo Deletado.");
        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());

        }
    }

}
