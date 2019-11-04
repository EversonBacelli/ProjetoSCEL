package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class REQ01CadastrarLivro {
 @Autowired
LivroRepository repository;	private Validator validator;
	private ValidatorFactory validatorFactory;

	@Test
	public void ScelApplicationTests() {
		// dado que o isbn nao esta cadastrado
		repository.deleteAll();
		// quando o usurio inclui as informacoes obrigatorias e confirma a operacao
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		repository.save(livro);
		// entao
		assertEquals(1, repository.count());
	}

	
	@Test
	public void CT02CadastrarLivroComSucesso_dados_validos() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		// given:
		Livro livro = new Livro("3333", "Teste de Software", "Delamaro");
		// when:
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		// then:
		assertTrue(violations.isEmpty());
	}
	
	@Test
	public void CT03DeveDetectarTituloInvalido() {
	 validatorFactory = Validation.buildDefaultValidatorFactory();
	 validator = validatorFactory.getValidator();
	 // dado que o titulo do livro esta invalido
	 Livro livro = new Livro("3333", "", "Delamaro");
	 // when:
	 Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
	 // then:
	 assertEquals(violations.size(), 1);
	 assertEquals("O titulo deve ser preenchido", violations.iterator().next().getMessage());
	}
	
	@Test 
	public void CT04ValidarAutor() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Livro livro = new Livro("3333", "Teste de Software","");
		 Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		 assertEquals(violations.size(), 1);
		 assertEquals("Autor deve ter entre 1 e 50 caracteres", violations.iterator().next().getMessage());
	}
	
}
