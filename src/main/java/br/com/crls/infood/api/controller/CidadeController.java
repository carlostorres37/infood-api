package br.com.crls.infood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.crls.infood.domain.exception.EntidadeEmUsoException;
import br.com.crls.infood.domain.exception.EntidadeNaoEncontradaException;
import br.com.crls.infood.domain.model.Cidade;
import br.com.crls.infood.domain.repository.CidadeRepository;
import br.com.crls.infood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("cidade")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscarPorId(@PathVariable Long id) { 
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		if (cidade.isPresent()) {
			return ResponseEntity.ok(cidade.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		return cadastroCidadeService.salvar(cidade);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cidade> atualziar(@PathVariable Long id, @RequestBody Cidade cidade) {
		Optional<Cidade> cidadeOptinal = cidadeRepository.findById(id);
		if (cidadeOptinal.isPresent()) {
			BeanUtils.copyProperties(cidade, cidadeOptinal.get(), "id");
			cadastroCidadeService.salvar(cidadeOptinal.get());
			return ResponseEntity.ok(cidadeOptinal.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cidade> deleter(@PathVariable Long id) {
		try {
			cadastroCidadeService.Excluir(id);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
