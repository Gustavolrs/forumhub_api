package com.forumhub_api.controller;

import com.forumhub_api.model.Topico;
import com.forumhub_api.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.forumhub_api.service.TopicoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    public ResponseEntity<Topico> criarTopico(@Valid @RequestBody Topico topico) {
        topico.setStatus("PENDENTE"); // Define o status padrão
        return ResponseEntity.ok(service.criarTopico(topico));
    }

    @GetMapping
    public ResponseEntity<List<Topico>> listarTodos() {
        // Exclui os campos resposta e status
        List<Topico> topicos = service.listarTodosOrdenados().stream()
                .peek(topico -> {
                    topico.setResposta(null);
                    topico.setStatus(null);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(topicos);
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
