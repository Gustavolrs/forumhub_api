package com.forumhub_api.service;

import com.forumhub_api.exception.ResourceNotFoundException;
import com.forumhub_api.exception.ValidationException;
import com.forumhub_api.model.Topico;
import com.forumhub_api.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    public Topico criarTopico(Topico topico) {
        validarDuplicidade(topico);
        return repository.save(topico);
    }

    public Optional<Topico> buscarPorId(Long id) {
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico não encontrado!")));
    }

    public void excluirTopico(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Tópico não encontrado!");
        }
        repository.deleteById(id);
    }

    public List<Topico> listarTodosOrdenados() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Topico atualizarTopico(Long id, Topico dadosAtualizados) {
        return repository.findById(id)
                .map(topico -> {
                    topico.setTitulo(dadosAtualizados.getTitulo());
                    topico.setCurso(dadosAtualizados.getCurso());
                    topico.setAutor(dadosAtualizados.getAutor());

                    // Verifica se há mensagem nova (resposta)
                    if (dadosAtualizados.getMensagem() != null && !dadosAtualizados.getMensagem().isBlank()) {
                        topico.setResposta(dadosAtualizados.getMensagem());
                        topico.setStatus("RESPONDIDO");
                    }

                    return repository.save(topico);
                })
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado!"));
    }

    private void validarDuplicidade(Topico topico) {
        boolean existe = repository.existsByTituloAndMensagem(topico.getTitulo(), topico.getMensagem());
        if (existe) {
            throw new ValidationException("Já existe um tópico com este título e mensagem!");
        }
    }
}