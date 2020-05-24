package com.noe.rest.controller;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noe.data.Tax;
import com.noe.repository.IRepositoryTax;
import com.noe.rest.assembly.TaxResourceAssembler;
import com.noe.rest.exception.TaxNotFoundException;


@RestController
@RequestMapping("api")
public class TaxRestController {
	@Autowired
	private IRepositoryTax repository;
	
	@Autowired 
	private TaxResourceAssembler assembler;
	
	@GetMapping("/taxes")
	public CollectionModel<EntityModel<Tax>> all(){
		
		List<EntityModel<Tax>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(TaxRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/taxes")
	public ResponseEntity<?> create(@RequestBody Tax newTax)  throws URISyntaxException{
	    EntityModel<Tax> entity = assembler.toModel(repository.save(newTax));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/taxes/{id}")
	public EntityModel<Tax> one(@PathVariable Long id){
		Tax entity = this.repository.findById(id).orElseThrow(()->new TaxNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/taxes/{id}")
	public ResponseEntity<?> replace(@RequestBody Tax newTax, @PathVariable Long id)throws URISyntaxException {
		Tax updated = repository.findById(id)
				.map(tax -> {
					tax.setCode(newTax.getCode());
					tax.setDescription(newTax.getDescription());
					tax.setProducts(newTax.getProducts());
					tax.setRate(newTax.getRate());
					return repository.save(tax);
				})
				.orElseGet(() -> {
					newTax.setId(id);
					return repository.save(newTax);
				});
		EntityModel<Tax> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/taxes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
