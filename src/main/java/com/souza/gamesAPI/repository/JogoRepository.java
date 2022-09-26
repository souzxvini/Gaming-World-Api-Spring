package com.souza.gamesAPI.repository;

import com.souza.gamesAPI.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.souza.gamesAPI.model.Jogo;

import java.util.Optional;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long>{

	Page<Jogo> findAllByCategoriaNome(String genero, Pageable pageable);
	
	Page<Jogo> findByNomeLike(String nome, Pageable pageable);

	@Query("select j from Jogo j where nome like :nome and j.categoria.nome = :nomeCategoria")
	Page<Jogo> findByNomeLikeAndCategoriaNome(@Param("nome") String nome, @Param("nomeCategoria") String nomeCategoria, Pageable pageable);

	Optional<Jogo> findByNome(String nome);

}
