package com.forumhub_api.controller;

import com.forumhub_api.model.Topico;
import com.forumhub_api.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    public ResponseEntity<Topico> criarTopico(@Valid @RequestBody Topico topico) {
        return ResponseEntity.ok(service.criarTopico(topico));
    }

    @GetMapping
    public ResponseEntity<List<Topico>> listarTodos() {
        return ResponseEntity.ok(service.listarTodosOrdenados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizarTopico(@PathVariable Long id, @Valid @RequestBody Topico dadosAtualizados) {
        return ResponseEntity.ok(service.atualizarTopico(id, dadosAtualizados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTopico(@PathVariable Long id) {
        service.excluirTopico(id);
        return ResponseEntity.noContent().build();
    }
}