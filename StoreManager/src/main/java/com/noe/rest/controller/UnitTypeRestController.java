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

import com.noe.data.UnitType;
import com.noe.repository.IRepositoryUnitType;
import com.noe.rest.assembly.UnitTypeResourceAssembler;
import com.noe.rest.exception.UnitTypeNotFoundException;


@RestController
@RequestMapping("api")
public class UnitTypeRestController {
	@Autowired
	private IRepositoryUnitType repository;
	
	@Autowired 
	private UnitTypeResourceAssembler assembler;
	
	@GetMapping("/unitTypes")
	public CollectionModel<EntityModel<UnitType>> all(){
		
		List<EntityModel<UnitType>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(UnitTypeRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/unitTypes")
	public ResponseEntity<?> create(@RequestBody UnitType newUnitType)  throws URISyntaxException{
	    EntityModel<UnitType> entity = assembler.toModel(repository.save(newUnitType));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/unitTypes/{id}")
	public EntityModel<UnitType> one(@PathVariable Long id){
		UnitType entity = this.repository.findById(id).orElseThrow(()->new UnitTypeNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/unitTypes/{id}")
	public ResponseEntity<?> replace(@RequestBody UnitType newUnitType, @PathVariable Long id)throws URISyntaxException {
		UnitType updated = repository.findById(id)
				.map(unitType -> {
					unitType.setDescription(newUnitType.getDescription());
					return repository.save(unitType);
				})
				.orElseGet(() -> {
					newUnitType.setId(id);
					return repository.save(newUnitType);
				});
		EntityModel<UnitType> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/unitTypes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
