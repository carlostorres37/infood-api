package br.com.crls.infood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crls.infood.domain.exception.EntidadeNaoEncontradaException;
import br.com.crls.infood.domain.model.Cozinha;
import br.com.crls.infood.domain.model.Restaurante;
import br.com.crls.infood.domain.repository.CozinhaRepository;
import br.com.crls.infood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRespository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinhaOptional = cozinhaRepository.findById(cozinhaId);
		if (!cozinhaOptional.isPresent()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
		}
		
		restaurante.setCozinha(cozinhaOptional.get());
		
		return restauranteRespository.save(restaurante);
	}
	
	

}
