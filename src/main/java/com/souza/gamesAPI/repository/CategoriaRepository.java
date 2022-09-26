package com.souza.gamesAPI.repository;

import com.souza.gamesAPI.model.Jogo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.souza.gamesAPI.model.Categoria;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	Categoria findByNome(String nomeCategoria);

	@Query("select c from Categoria c where nome = :nome")
	Optional<Categoria> findByNomeOptional(@Param("nome") String nome);

	Page<Categoria> findByNomeLike(String nome, Pageable pageable);
}
