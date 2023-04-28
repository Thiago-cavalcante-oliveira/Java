package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/veiculo")

public class VeiculoController {
    private VeiculoRepositorio veiculoRepositorio;

    public VeiculoController(VeiculoRepositorio veiculoRepositorio) {
        this.veiculoRepositorio = veiculoRepositorio;
    }

    @GetMapping
    public ResponseEntity<?> buscaVeiculoPorId(@RequestParam("id") final Long id) {
        final Veiculo veiculo = this.veiculoRepositorio.findById(id).orElse(null);
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping
    public ResponseEntity<?> buscaLista() {
        return ResponseEntity.ok(this.veiculoRepositorio.findAll());
    }

    @GetMapping("/listaativo")
    public ResponseEntity<?> buscaListaAtivo() {
        List<Veiculo> listaVeiuclo = this.veiculoRepositorio.findAll();
        List<Veiculo> checaLista = new ArrayList();
        for (Veiculo valor : checaLista
        ) {
            if (valor.isAtivo()) {
                checaLista.add(valor);
            }
        }
        return ResponseEntity.ok(checaLista);
    }

    @PostMapping
    public ResponseEntity<?> cadastraVeiculo(@RequestBody final Veiculo veiculo) {
        this.veiculoRepositorio.save(veiculo);
        return ResponseEntity.ok("Cadastrado com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizaVeiculo(@PathVariable final Long id, @RequestBody Veiculo veiculo) {
        try {
            Veiculo valorBanco = this.veiculoRepositorio.findById(id).orElse(null);
            if (valorBanco == null || !valorBanco.getId().equals(veiculo.getId())) {
                throw new RuntimeException("Não localizado");
            }
            this.veiculoRepositorio.save(veiculo);
            return ResponseEntity.ok("Veiculo atualizado.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> atualizaVeiculo(@PathVariable final Long id) {
        try {
            Veiculo valorBanco = this.veiculoRepositorio.findById(id).orElse(null);
            if (valorBanco == null) {
                throw new RuntimeException("Não localizado");
            }
            this.veiculoRepositorio.delete(valorBanco);
            return ResponseEntity.ok("Veiculo Deletado.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            Veiculo valorBanco = this.veiculoRepositorio.findById(id).orElse(null);
            if (valorBanco == null) {
                throw new RuntimeException("Não localizado");
            }
            valorBanco.setAtivo(false);
            this.veiculoRepositorio.save(valorBanco);
            return ResponseEntity.ok("Veiculo Inativado.");

        }
    }

}
