package com.souza.gamesAPI.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.souza.gamesAPI.dto.CategoriaResponse;
import com.souza.gamesAPI.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.souza.gamesAPI.dto.CategoriaDto;
import com.souza.gamesAPI.form.AtualizacaoCategoriaForm;
import com.souza.gamesAPI.form.CategoriaForm;

@RestController
@RequestMapping(value = "categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public Page<CategoriaDto> listar(@PageableDefault(sort= "id", direction = Direction.DESC, page = 0, size = 24) Pageable pageable){
		return categoriaService.listar(pageable);
	}

	@GetMapping("/noPagination")
	public List<CategoriaResponse> listarSemPaginacao(){
		return categoriaService.listarSemPaginacao();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CategoriaDto> cadastrar(@RequestBody @Valid CategoriaForm categoriaForm, UriComponentsBuilder uriBuilder){
		return categoriaService.cadastrar(categoriaForm, uriBuilder);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDto> detalhar(@PathVariable Long id){
		return categoriaService.detalhar(id);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CategoriaDto> atualizar(@PathVariable Long id,
												  @RequestBody @Valid AtualizacaoCategoriaForm atualizacaoCategoriaForm){
		return categoriaService.atualizar(id, atualizacaoCategoriaForm);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletar(@PathVariable Long id){
		return categoriaService.deletar(id);
	}

	@GetMapping(value="/string")
	public Page<CategoriaDto> listarCategoriaQueContemString(@RequestParam String nome,
															 @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 12)Pageable pageable){
		return categoriaService.listarCategoriaQueContemString(nome, pageable);
	}

	@GetMapping("/exists")
	public boolean verificarSeCategoriaJaExiste(@RequestParam String nomeCategoria){
		return categoriaService.verificarSeCategoriaJaExiste(nomeCategoria);
	}

	@GetMapping(value = "/edit/exists", produces = MediaType.APPLICATION_JSON_VALUE )
	public boolean verificarSeCategoriaJaExisteEditForm(@RequestParam String nome,
												   @RequestParam long id){

		return categoriaService.verificarSeCategoriaJaExisteEditForm(nome, id);
	}
	
}
