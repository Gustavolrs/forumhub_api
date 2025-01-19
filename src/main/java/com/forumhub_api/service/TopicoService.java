package com.forumhub_api.service;

import com.forumhub_api.model.Topico;
import com.forumhub_api.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return repository.findById(id);
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
                    topico.setMensagem(dadosAtualizados.getMensagem());
                    topico.setAutor(dadosAtualizados.getAutor());
                    topico.setCurso(dadosAtualizados.getCurso());
                    topico.setStatus(dadosAtualizados.getStatus());
                    return repository.save(topico);
                })
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado!"));
    }

    private void validarDuplicidade(Topico topico) {
        boolean existe = repository.existsByTituloAndMensagem(topico.getTitulo(), topico.getMensagem());
        if (existe) {
            throw new IllegalArgumentException("Já existe um tópico com este título e mensagem!");
        }
    }
}