package com.souza.gamesAPI.dto;

import java.util.ArrayList;
import java.util.List;

import com.souza.gamesAPI.model.Categoria;
import com.souza.gamesAPI.model.Jogo;

public class JogoResponse {

	private Long id;
	private String nome;
	private String descricao;
	private String anoLancamento;
	private double preco;
	private Categoria categoria;
	private String urlImagem;
	private String empresa;
	
	public JogoResponse(Jogo jogo) {
		this.nome = jogo.getNome();
		this.descricao = jogo.getDescricao();
		this.categoria = jogo.getCategoria();
		this.id = jogo.getId();
		this.preco = jogo.getPreco();
		this.anoLancamento = jogo.getAnoLancamento();
		this.urlImagem = jogo.getUrlImagem();
		this.empresa = jogo.getEmpresa();
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public String getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(String anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

	public static List<JogoResponse> converter(List<Jogo> jogos) {
		
		List<JogoResponse> response = new ArrayList<JogoResponse>();
		
		jogos.stream().forEach(jogo -> {
			response.add(new JogoResponse(jogo));
			});
		return response;
	}
	
	public static List<JogoResponse> converterPage(List<Jogo> jogos) {
		
		List<JogoResponse> response = new ArrayList<JogoResponse>();
		
		jogos.stream().forEach(jogo -> {
			response.add(new JogoResponse(jogo));
			});
		return response;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
}
