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

import com.noe.data.UnitOfMeasure;
import com.noe.repository.IRepositoryUnitOfMeasure;
import com.noe.rest.assembly.UnitOfMeasureResourceAssembler;
import com.noe.rest.exception.UnitOfMeasureNotFoundException;


@RestController
@RequestMapping("api")
public class UnitOfMeasureRestController {
	@Autowired
	private IRepositoryUnitOfMeasure repository;
	
	@Autowired 
	private UnitOfMeasureResourceAssembler assembler;
	
	@GetMapping("/unitsOfMeasure")
	public CollectionModel<EntityModel<UnitOfMeasure>> all(){
		
		List<EntityModel<UnitOfMeasure>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(UnitOfMeasureRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/unitsOfMeasure")
	public ResponseEntity<?> create(@RequestBody UnitOfMeasure newUnitOfMeasure)  throws URISyntaxException{
	    EntityModel<UnitOfMeasure> entity = assembler.toModel(repository.save(newUnitOfMeasure));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/unitsOfMeasure/{id}")
	public EntityModel<UnitOfMeasure> one(@PathVariable Long id){
		UnitOfMeasure entity = this.repository.findById(id).orElseThrow(()->new UnitOfMeasureNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/unitsOfMeasure/{id}")
	public ResponseEntity<?> replace(@RequestBody UnitOfMeasure newUnitOfMeasure, @PathVariable Long id)throws URISyntaxException {
		UnitOfMeasure updated = repository.findById(id)
				.map(unitOfMeasure -> {
					unitOfMeasure.setCode(newUnitOfMeasure.getCode());
					unitOfMeasure.setDescription(newUnitOfMeasure.getDescription());
					unitOfMeasure.setType(newUnitOfMeasure.getType());
					return repository.save(unitOfMeasure);
				})
				.orElseGet(() -> {
					newUnitOfMeasure.setId(id);
					return repository.save(newUnitOfMeasure);
				});
		EntityModel<UnitOfMeasure> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/unitsOfMeasure/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
