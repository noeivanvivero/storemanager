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

import com.noe.data.ComercialUnit;
import com.noe.repository.IRepositoryComercialUnit;
import com.noe.rest.assembly.ComercialUnitResourceAssembler;
import com.noe.rest.exception.ComercialUnitNotFoundException;


@RestController
@RequestMapping("api")
public class ComercialUnitRestController {
	@Autowired
	private IRepositoryComercialUnit repository;
	
	@Autowired 
	private ComercialUnitResourceAssembler assembler;
	
	@GetMapping("/comercialUnits")
	public CollectionModel<EntityModel<ComercialUnit>> all(){
		
		List<EntityModel<ComercialUnit>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(ComercialUnitRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/comercialUnits")
	public ResponseEntity<?> create(@RequestBody ComercialUnit newComercialUnit)  throws URISyntaxException{
	    EntityModel<ComercialUnit> entity = assembler.toModel(repository.save(newComercialUnit));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/comercialUnits/{id}")
	public EntityModel<ComercialUnit> one(@PathVariable Long id){
		ComercialUnit entity = this.repository.findById(id).orElseThrow(()->new ComercialUnitNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/comercialUnits/{id}")
	public ResponseEntity<?> replace(@RequestBody ComercialUnit newComercialUnit, @PathVariable Long id)throws URISyntaxException {
		ComercialUnit updated = repository.findById(id)
				.map(comercialUnit -> {
						comercialUnit.setCode(newComercialUnit.getCode());
						comercialUnit.setName(newComercialUnit.getName());
						comercialUnit.setRfc(newComercialUnit.getRfc());
						return repository.save(comercialUnit);
				})
				.orElseGet(() -> {
						newComercialUnit.setId(id);
						return repository.save(newComercialUnit);
				});
		EntityModel<ComercialUnit> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/comercialUnits/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
