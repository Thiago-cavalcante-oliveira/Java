package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepositorio;
import br.com.uniamerica.estacionamento.repository.ModeloRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    //@Autowired
    //private CondutorRepositorio condutorRepositorio;
    @Autowired
    private CondutorRepositorio condutorRepositorio;
    public CondutorController (CondutorRepositorio condutorRepositorio){
        this.condutorRepositorio = condutorRepositorio;
    }

    /*
    http://localhost:8080/api/condutor/1

     */
/*
    @GetMapping("/{id}") //
    public ResponseEntity <?> findByIdRequest(@PathVariable("id")final long id ){
        //return ResponseEntity.ok(new Condutor());

        return ResponseEntity.ok(this.condutorRepositorio.findById(id).orElse(null));
    }*/
/*
    http://localhost:8080/api/condutor?id=1

     */

    @GetMapping //outra forma de buscar po id
    public ResponseEntity <?> findByIdRequest(@RequestParam("id")final Long id ){
        final Condutor condutor = this.condutorRepositorio.findById(id).orElse(null);
        return condutor == null
                ? ResponseEntity.badRequest().body("nenhum valor encontrado") :
                 ResponseEntity.ok(condutor);
    }
       // return ResponseEntity.ok(new Condutor());


    @GetMapping("/lista")
    public ResponseEntity <?> listaCompleta (){
        return ResponseEntity.ok(this.condutorRepositorio.findAll());
    }

    @PostMapping
    public ResponseEntity <?> cadastrar (@RequestBody final Condutor condutor){
        this.condutorRepositorio.save(condutor);
        return ResponseEntity.ok("Registro cadastrado com sucesso");
    }
   @PutMapping("id")
    public ResponseEntity <?> editar(
            @RequestParam("id") Long id,
            @RequestBody final Condutor condutor
   ){
        try {

            final Condutor condutorBanco = this.condutorRepositorio.findById(id).orElse(null);

            if (condutorBanco == null || !condutorBanco.getId().equals(condutor.getId())){
            throw new RuntimeException("Não foi possível identificar o registro");
        }
        this.condutorRepositorio.save(condutor);
        return ResponseEntity.ok("Registro cadastrado");
        }
        catch (DataIntegrityViolationException e){
            return  ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
       catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error");
       }

   }



    @DeleteMapping ("id")

    public ResponseEntity <?> deletar(
            @RequestParam("id") Long id,
            @RequestBody final Condutor condutor
    ){
        try {

            final Condutor condutorBanco = this.condutorRepositorio.findById(id).orElse(null);

            if (condutorBanco == null || !condutorBanco.getId().equals(condutor.getId())){
                throw new RuntimeException("Não foi possível identificar o registro");
            }
            this.condutorRepositorio.save(condutor);
            return ResponseEntity.ok("Registro cadastrado");
        }
        catch (DataIntegrityViolationException e){
            return  ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error");
        }

    }




}