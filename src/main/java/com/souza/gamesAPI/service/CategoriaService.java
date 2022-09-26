package com.souza.gamesAPI.service;

import com.souza.gamesAPI.dto.CategoriaDto;
import com.souza.gamesAPI.dto.CategoriaResponse;
import com.souza.gamesAPI.form.AtualizacaoCategoriaForm;
import com.souza.gamesAPI.form.CategoriaForm;
import com.souza.gamesAPI.model.Categoria;
import com.souza.gamesAPI.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Page<CategoriaDto> listar(Pageable pageable){
        Page<Categoria> categorias = categoriaRepository.findAll(pageable);
        return CategoriaDto.converter(categorias);
    }

    public List<CategoriaResponse> listarSemPaginacao(){
        List<Categoria> categorias = categoriaRepository.findAll();
        return CategoriaResponse.converter(categorias);
    }

    public ResponseEntity<CategoriaDto> cadastrar(CategoriaForm categoriaForm, UriComponentsBuilder uriBuilder){
        Categoria categoria = categoriaForm.converter();

        categoria.setNome(categoria.getNome().toUpperCase());

        categoriaRepository.save(categoria);
        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
    }

    public ResponseEntity<CategoriaDto> detalhar(Long id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if(categoria.isPresent()) {
            return ResponseEntity.ok(new CategoriaDto(categoria.get()));
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<CategoriaDto> atualizar(Long id, AtualizacaoCategoriaForm atualizacaoCategoriaForm){
        Optional<Categoria> optional = categoriaRepository.findById(id);

        if(optional.isPresent()) {
            Categoria categoria = atualizacaoCategoriaForm.atualizar(id, categoriaRepository);
            return ResponseEntity.ok(new CategoriaDto(categoria));
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> deletar(Long id){
        Optional<Categoria> optional = categoriaRepository.findById(id);

        if(optional.isPresent()) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public Page<CategoriaDto> listarCategoriaQueContemString(String nome, Pageable pageable){
        if(nome == null || nome.isEmpty()) {
            Page<Categoria> categorias = categoriaRepository.findAll(pageable);
        }

        Page<Categoria> categorias = categoriaRepository.findByNomeLike("%" + nome.toUpperCase() + "%", pageable);
        return CategoriaDto.converter(categorias);
    }

    public boolean verificarSeCategoriaJaExiste(String nomeCategoria){
        Optional<Categoria> categoria = categoriaRepository.findByNomeOptional(nomeCategoria);

        if(categoria.isEmpty() ){
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarSeCategoriaJaExisteEditForm(String nome, long id){

        Optional<Categoria> categoria = categoriaRepository.findByNomeOptional(nome);
        Optional<Categoria> categoriaSendoEditada = categoriaRepository.findById(id);

        if(categoria.isEmpty() || categoria == null){
            return false;
        }
        if(categoria.get().getNome() == categoriaSendoEditada.get().getNome() ){
            return false;
        }else {
            return true;
        }
    }
}
