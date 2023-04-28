package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/marca")

public class MarcaController {
    @Autowired
        public MarcaRepositorio marcaRepositorio;
    @GetMapping //outra forma de buscar por id
    public ResponseEntity<?> findByIdRequest(@RequestParam("id")final Long id ){
        final Marca marca = this.marcaRepositorio.findById(id).orElse(null);
        return marca == null
                ? ResponseEntity.badRequest().body("nenhum valor encontrado") :
                ResponseEntity.ok(marca);
    }

    @GetMapping("/lista")
        public ResponseEntity<?> retornaLista (){
        return ResponseEntity.ok(this.marcaRepositorio.findAll());
    }

    @GetMapping("/listaAtiva")
    public ResponseEntity<?> retornaListaAtiva (){
        List <Marca> listaMarca = this.marcaRepositorio.findAll();
        List<Marca> checaLista = new ArrayList<>();
        for (Marca valor:listaMarca
             ) {
            if (valor.isAtivo()){
                checaLista.add(valor);
            }


        }
        return ResponseEntity.ok(checaLista);
    }

    @PostMapping
        public ResponseEntity<?> cadastraMarca(@RequestBody final Marca marca){
        this.marcaRepositorio.save(marca);
        return ResponseEntity.ok("MArca cadstrada com sucesso");
    }
    @PutMapping("/{id}")
        public ResponseEntity<?> atualizaMarca(@PathVariable("id") final Long id,
                                               @RequestBody final Marca marca)
    {
        try {
            final Marca marcaBanco = this.marcaRepositorio.findById(id).orElse(null);
            if (marcaBanco == null || !marcaBanco.getId().equals(marca.getId())){
                throw new RuntimeException("Não foi possível localizar");
            }
            this.marcaRepositorio.save(marca);
            return ResponseEntity.ok("Marca atualizada!");
        } catch (DataIntegrityViolationException e){
            return  ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <?> inativar(@PathVariable("id") Long id){
        try {
            final Marca marcaBanco = this.marcaRepositorio.findById(id).orElse(null);
            if (marcaBanco == null ){
                throw new RuntimeException("Não foi possível identificar o registro");
            }
            this.marcaRepositorio.delete(marcaBanco);
            return ResponseEntity.ok("Registro Deletado");
        }
        catch (DataIntegrityViolationException e){
            return  ResponseEntity.internalServerError().body("Error"+e + e.getCause().getCause().getMessage());
        }
        catch (Exception e){
            final Marca marcaBanco = this.marcaRepositorio.findById(id).orElse(null);
            if (marcaBanco == null ){
                throw new RuntimeException("Não foi possível identificar o registro");
            }
            marcaBanco.setAtivo(false);
            this.marcaRepositorio.save(marcaBanco);
            return ResponseEntity.ok("Registro Inativado");
        }

    }




}
