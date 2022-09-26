package com.souza.gamesAPI.dto;

import com.souza.gamesAPI.model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaResponse {

	private Long id;

	private String nome;

	public CategoriaResponse(Categoria categoria) {
		this.nome = categoria.getNome();
		this.id = categoria.getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static List<CategoriaResponse> converter(List<Categoria> categorias) {

		List<CategoriaResponse> response = new ArrayList<CategoriaResponse>();

		categorias.stream().forEach(categoria -> {
			response.add(new CategoriaResponse(categoria));
		});
		return response;
	}
}
