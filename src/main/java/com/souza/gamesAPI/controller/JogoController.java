package com.souza.gamesAPI.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.souza.gamesAPI.service.JogoService;
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

import com.souza.gamesAPI.dto.JogoDto;
import com.souza.gamesAPI.dto.JogoResponse;
import com.souza.gamesAPI.form.AtualizacaoJogoForm;
import com.souza.gamesAPI.form.JogoForm;

@RestController
@RequestMapping(value = "jogos")
public class JogoController {

	@Autowired
	private JogoService jogoService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
 	public Page<JogoDto> listar( @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 12) Pageable paginacao){
		return jogoService.listar(paginacao);
	}

	@GetMapping(value="/noPagination", produces = MediaType.APPLICATION_JSON_VALUE )
	public List<JogoResponse> listarNoPagination(){
		return jogoService.listarNoPagination();
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseEntity<JogoDto> cadastrar(@RequestBody @Valid JogoForm jogoForm){
		return jogoService.cadastrar(jogoForm);
	}

	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<JogoDto> detalhar(@PathVariable Long id){
		return jogoService.detalhar(id);
	}
	
	@PutMapping(value="/{id}")
	@Transactional
	public ResponseEntity<JogoDto> atualizar(@PathVariable Long id,
											 @RequestBody @Valid AtualizacaoJogoForm jogoForm){
		return jogoService.atualizar(id, jogoForm);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		return jogoService.deletar(id);
	}
	
	@GetMapping(value="/categoria")
	public Page<JogoDto> listarGamePorCategoria(@RequestParam String nomeCategoria,
												@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 12)Pageable pageable){
		return jogoService.listarGamePorCategoria(nomeCategoria, pageable);
	}
	
	@GetMapping(value="/string", produces = MediaType.APPLICATION_JSON_VALUE )
	public Page<JogoDto> listarJogosQueContemString(@RequestParam String nome,
													@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 12)Pageable pageable){
		return jogoService.listarJogosQueContemString(nome, pageable);
	}

	@GetMapping(value="/porStringECategoria", produces = MediaType.APPLICATION_JSON_VALUE )
	public Page<JogoDto> listarJogosQueContemStringCategoria(@RequestParam String nomeCategoria,
															 @RequestParam String nome,
															 @PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 12)Pageable pageable){
		return jogoService.listarJogosQueContemStringCategoria(nomeCategoria, nome, pageable);
	}

	@GetMapping(value = "/exists", produces = MediaType.APPLICATION_JSON_VALUE )
	public boolean verificarSeJogoJaExiste(@RequestParam String nome){

		return jogoService.verificarSeJogoJaExiste(nome);
	}

	@GetMapping(value = "/edit/exists", produces = MediaType.APPLICATION_JSON_VALUE )
	public boolean verificarSeJogoJaExisteEditForm(@RequestParam String nome,
												   @RequestParam long id){

		return jogoService.verificarSeJogoJaExisteEditForm(nome, id);
	}
}
