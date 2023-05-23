package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepositorio;
import br.com.uniamerica.estacionamento.repository.ModeloRepositorio;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    //@Autowired
    //private CondutorRepositorio condutorRepositorio;
    @Autowired
    private CondutorRepositorio condutorRepositorio;
   @Autowired
   private CondutorService service;

    @GetMapping //outra forma de buscar por id
    public ResponseEntity<?> findByIdRequest(@RequestParam("id")final Long id ){
        try{
            return ResponseEntity.ok(service.buscaPorId(id));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }


   @GetMapping("/listaativo")
   public ResponseEntity <?> listaCondutorAtivo (){

           return ResponseEntity.ok(condutorRepositorio.listarAtivos()) ;
       }


    @GetMapping("/lista")
    public ResponseEntity <?> listaCompletaCondutor (){
        return ResponseEntity.ok(this.condutorRepositorio.findAll());
    }

    @PostMapping
    public ResponseEntity <?> cadastrar (@RequestBody final Condutor condutor) {
        try {
            this.service.cadastrar(condutor);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
   @PutMapping
    public ResponseEntity <?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final Condutor condutor){
        try {
            service.Atualizar(id,condutor);
            return ResponseEntity.ok("Atualizado.");
        }
       catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro: "+ e.getMessage());
       }
   }



    @DeleteMapping ("/{id}")
    public ResponseEntity <?> inativar(@PathVariable("id") Long id){
      try{

          return ResponseEntity.ok( this.service.deletar(id));
      }
      catch (RuntimeException e){
          return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
      }
    }






}
