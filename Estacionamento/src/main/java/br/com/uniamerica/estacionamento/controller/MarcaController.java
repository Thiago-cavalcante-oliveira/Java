package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepositorio;
import br.com.uniamerica.estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/api/marca")

public class MarcaController {
    @Autowired
        private MarcaService service;
    @Autowired
    private MarcaRepositorio marcaRepositorio;

    @GetMapping ("/{id}")
    public ResponseEntity<?> findByIdRequest(@PathVariable("id")final Long id ){
        try{

            return ResponseEntity.ok(service.buscaPorId(id));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/lista")
        public ResponseEntity<?> retornaLista (){
        return ResponseEntity.ok(this.marcaRepositorio.findAll());
    }

    @GetMapping("/listaAtiva")
    public ResponseEntity<?> retornaListaAtiva (){
       return ResponseEntity.ok(marcaRepositorio.listaMarcasAtivas());
    }

    @PostMapping
        public ResponseEntity<?> cadastraMarca(@RequestBody final Marca marca){
        try{
            service.cadastrar(marca);
            return ResponseEntity.ok("Cadastrado com sucesso.");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
       }

    @PutMapping("/{id}")
        public ResponseEntity<?> atualizaMarca(@PathVariable("id") final Long id,
                                               @RequestBody final Marca marca)
    {
        try {
            this.service.Atualizar(marca, id);
            return ResponseEntity.ok("Atualuizado co Sucesso");
            }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> inativar(@PathVariable("id") final Long id){
        try {
            this.service.deletar(id);
            return  ResponseEntity.ok("Marca deletada.");
         }
         catch (Exception e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }

    }




}
