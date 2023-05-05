package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {
@Autowired
    private ConfiguracaoRepositorio configuracaoRepositorio;
    //public ConfiguracaoController(ConfiguracaoController configuracaoController){
      //  this.configuracaoRepositorio = configuracaoRepositorio;
    //}

    @GetMapping
    public ResponseEntity <?> buscaid(@RequestParam("id") final Long id){
        final Configuracao configuracao = this.configuracaoRepositorio.findById(id).orElse(null);

        return configuracao == null ? ResponseEntity.badRequest().body("Não localizado") :
                ResponseEntity.ok(configuracao);
    }

    @PostMapping
    public ResponseEntity<?> cadastraConfiguracao(@RequestBody final Configuracao configuracao){
        configuracaoRepositorio.save(configuracao);
        return ResponseEntity.ok("Configuracao ");
    }

    @PutMapping("/{id}")
    public  ResponseEntity <?> editaConfiguracao(@PathVariable final Long id, @RequestBody Configuracao configuracao){
       try{
           Configuracao valorBanco = this.configuracaoRepositorio.findById(id).orElse(null);
           if (valorBanco == null || !valorBanco.getId().equals(configuracao.getId())){
                throw new RuntimeException("Não foi possível localizar.");
           }
           this.configuracaoRepositorio.save(configuracao);
           return ResponseEntity.ok("Configuracao cadastrada com sucesso.");
       } catch (DataIntegrityViolationException e) {
           return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
       } catch (RuntimeException e) {
           return ResponseEntity.internalServerError().body("Error");
       }
    }

}
