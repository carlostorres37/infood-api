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

import br.com.crls.infood.domain.model.Cozinha;
import br.com.crls.infood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Cozinha> buscarPorId(@PathVariable Long id) { 
		return cozinhaRepository.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualziar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		Optional<Cozinha> cozinhaOptinal = cozinhaRepository.findById(id);
		if (cozinhaOptinal.isPresent()) {
			BeanUtils.copyProperties(cozinha, cozinhaOptinal.get(), "id");
			cozinhaRepository.save(cozinhaOptinal.get());
			return ResponseEntity.ok(cozinhaOptinal.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> deleter(@PathVariable Long id) {
		try {
			Optional<Cozinha> cozinhaOptional = cozinhaRepository.findById(id);
			if (cozinhaOptional.isPresent()) {
				cozinhaRepository.delete(cozinhaOptional.get());
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	

}
