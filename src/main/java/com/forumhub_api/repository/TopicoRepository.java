package com.forumhub_api.repository;

import com.forumhub_api.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND FUNCTION('YEAR', t.dataCriacao) = :ano")
    List<Topico> findByCursoAndDataCriacaoYear(@Param("curso") String curso, @Param("ano") int ano);
}
