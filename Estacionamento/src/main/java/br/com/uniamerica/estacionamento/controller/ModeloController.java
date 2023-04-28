package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.ModeloRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/modelo")

public class ModeloController {
    private ModeloRepositorio modeloRepositorio;
    public ModeloController(ModeloController modeloController ){
        this.modeloRepositorio = modeloRepositorio;
    }

    @GetMapping
    public ResponseEntity<?> buscaModelo(@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloRepositorio.findById(id).orElse(null);
        return modelo == null ? ResponseEntity.badRequest().body("Não localizado") :
        ResponseEntity.ok(modelo);
            }
    @GetMapping("/lista")
    public ResponseEntity<?> buscaListaModelo(){
        return ResponseEntity.ok(this.modeloRepositorio.findAll());
            }

    @GetMapping("/listaativo")
    public ResponseEntity<?> buscaListaAtivo(){
        List <Modelo> modeloLista = this.modeloRepositorio.findAll();
        List <Modelo> checaLista    = new ArrayList<>();
        for (Modelo valor:modeloLista
             ) {
            if ( valor.isAtivo()){
                checaLista.add(valor);
            }
        }
        return ResponseEntity.ok(checaLista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizaModelo(@PathVariable final Long id, @RequestBody Modelo modelo) {
        try {
            Modelo valorBanco = this.modeloRepositorio.findById(id).orElse(null);
            if (valorBanco == null || !valorBanco.getId().equals(modelo.getId())) {
                throw new RuntimeException("Não foi possível localizar");
            }
            this.modeloRepositorio.save(modelo);
            return ResponseEntity.ok("Modelo Atualizado com sucesso.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluiModelo(@PathVariable final Long id) {
        try {
            Modelo valorBanco = this.modeloRepositorio.findById(id).orElse(null);
            if (valorBanco == null ) {
                throw new RuntimeException("Não foi possível localizar");
            }
            this.modeloRepositorio.delete(valorBanco);
            return ResponseEntity.ok("Modelo deletado com sucesso.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        } catch (Exception e){
            final Modelo valorBanco = this.modeloRepositorio.findById(id).orElse(null);
            if (valorBanco == null ){
                throw new RuntimeException("Não foi possível identificar o registro");
            }
            valorBanco.setAtivo(false);
            this.modeloRepositorio.save(valorBanco);
            return ResponseEntity.ok("Registro Inativado");
        }
    }



}
