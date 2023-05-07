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

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    //@Autowired
    //private CondutorRepositorio condutorRepositorio;
    @Autowired
    private CondutorRepositorio condutorRepositorio;
   @Autowired
   private CondutorService service;

      @GetMapping //outra forma de buscar po id
    public ResponseEntity <?> findByIdRequest(@RequestParam("id")final Long id ){
        try{
         return ResponseEntity.ok( this.service.BuscarPorID(id));
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
   @PutMapping("/{id}")
    public ResponseEntity <?> editar(
            @PathVariable("id") Long id,
            @RequestBody final Condutor condutor
   ){
        try {
            final Condutor condutorBanco = this.condutorRepositorio.findById(id).orElse(null);
            if (condutorBanco == null || !condutorBanco.getId().equals(condutor.getId())){
            throw new RuntimeException("Não foi possível identificar o registro");
        }
        this.condutorRepositorio.save(condutor);
        return ResponseEntity.ok("Registro Atualizado");
        }
        catch (DataIntegrityViolationException e){
            return  ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
       catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error");
       }
   }



    @DeleteMapping ("/{id}")
    public ResponseEntity <?> inativar(@PathVariable("id") Long id){
       try {
            final Condutor condutorBanco = this.condutorRepositorio.findById(id).orElse(null);
            if (condutorBanco == null ){
                throw new RuntimeException("Não foi possível identificar o registro");
           }
            this.condutorRepositorio.delete(condutorBanco);
            return ResponseEntity.ok("Registro Deletado");
        }
        catch (DataIntegrityViolationException e){
            return  ResponseEntity.internalServerError().body("Error"+e + e.getCause().getCause().getMessage());
        }
        catch (Exception e){
            final Condutor condutorBanco = this.condutorRepositorio.findById(id).orElse(null);
            if (condutorBanco == null ){
                throw new RuntimeException("Não foi possível identificar o registro");
            }
            condutorBanco.setAtivo(false);
            this.condutorRepositorio.save(condutorBanco);
            return ResponseEntity.ok("Registro Inativado");
        }

    }




}
