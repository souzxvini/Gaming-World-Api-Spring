package com.souza.gamesAPI.service;

import com.souza.gamesAPI.dto.JogoDto;
import com.souza.gamesAPI.dto.JogoResponse;
import com.souza.gamesAPI.form.AtualizacaoJogoForm;
import com.souza.gamesAPI.form.JogoForm;
import com.souza.gamesAPI.model.Jogo;
import com.souza.gamesAPI.repository.CategoriaRepository;
import com.souza.gamesAPI.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Page<JogoDto> listar(Pageable paginacao) {
        Page<Jogo> jogos = jogoRepository.findAll(paginacao);

        return JogoDto.converter(jogos);
    }

    public List<JogoResponse> listarNoPagination(){
        List<Jogo> jogos = jogoRepository.findAll();

        return JogoResponse.converter(jogos);
    }

    public ResponseEntity<JogoDto> cadastrar(JogoForm jogoForm){
        Jogo jogo = jogoForm.converter(categoriaRepository);
        jogo.setNome(jogo.getNome().toUpperCase());

        jogoRepository.save(jogo);

        return ResponseEntity.ok(new JogoDto(jogo));
    }

    public ResponseEntity<JogoDto> detalhar(Long id){
        Optional<Jogo> jogo = jogoRepository.findById(id);

        if(jogo.isPresent()) {
            return ResponseEntity.ok(new JogoDto(jogo.get()));
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<JogoDto> atualizar(Long id, AtualizacaoJogoForm jogoForm){
        Optional<Jogo> optional = jogoRepository.findById(id);

        if(optional.isPresent()) {
            Jogo jogo = jogoForm.atualizar(id, jogoRepository, categoriaRepository);
            return ResponseEntity.ok(new JogoDto(jogo));
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> deletar(Long id){
        Optional<Jogo> jogo = jogoRepository.findById(id);

        if(jogo.isPresent()) {
            jogoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public Page<JogoDto> listarGamePorCategoria(String nomeCategoria, Pageable pageable){
        Page<Jogo> jogos = jogoRepository.findAllByCategoriaNome(nomeCategoria, pageable);
        Page<JogoDto> response = JogoDto.converter(jogos);
        return response;
    }

    public Page<JogoDto> listarJogosQueContemString(String nome, Pageable pageable){
        if(nome == null || nome.isEmpty()) {
            Page<Jogo> jogos = jogoRepository.findAll(pageable);
        }

        Page<Jogo> jogos = jogoRepository.findByNomeLike("%" + nome.toUpperCase() + "%", pageable);
        return JogoDto.converter(jogos);
    }

    public Page<JogoDto> listarJogosQueContemStringCategoria(String nomeCategoria, String nome, Pageable pageable){
        Page<Jogo> jogos = jogoRepository.findByNomeLikeAndCategoriaNome("%" + nome.toUpperCase() + "%", nomeCategoria.toUpperCase(), pageable);
        Page<JogoDto> response = JogoDto.converter(jogos);
        return response;
    }

    public boolean verificarSeJogoJaExiste(String nome){
        Optional<Jogo> jogo = jogoRepository.findByNome(nome);

        if(jogo.isEmpty() ){
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarSeJogoJaExisteEditForm(String nome, long id){

        Optional<Jogo> jogo = jogoRepository.findByNome(nome);
        Optional<Jogo> jogoSendoEditado = jogoRepository.findById(id);

        if(jogo.isEmpty()){
            return false;
        }
        if(jogo.get().getNome() == jogoSendoEditado.get().getNome() ){
            return false;
        }else {
            return true;
        }
    }

}
