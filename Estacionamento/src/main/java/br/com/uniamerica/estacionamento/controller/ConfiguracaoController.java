package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepositorio;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
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
    @Autowired
    private ConfiguracaoService service;

    @GetMapping
    public ResponseEntity <?> buscaid(@RequestParam("id") final Long id){
        final Configuracao configuracao = this.configuracaoRepositorio.findById(id).orElse(null);
        return configuracao == null ? ResponseEntity.badRequest().body("Não localizado") :
                ResponseEntity.ok(configuracao);
    }

    @PostMapping
    public ResponseEntity<?> cadastraConfiguracao(@RequestBody final Configuracao configuracao){
        try{
            this.service.cadastrar(configuracao);
            return ResponseEntity.ok("Cadastrado com sucesso.");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
       }

    @PutMapping
    public  ResponseEntity <?> editaConfiguracao(@RequestParam final Long id, @RequestBody Configuracao configuracao){
       try{
           this.service.atualizar(id, configuracao);
           return ResponseEntity.ok("Configuracao Atualizada com sucesso.");
       }  catch (RuntimeException e) {
           return ResponseEntity.badRequest().body("Error " + e.getMessage());
       }
    }

}
