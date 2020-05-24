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

import com.noe.data.AdjustmentType;
import com.noe.repository.IRepositoryAdjustmentType;
import com.noe.rest.assembly.AdjustmentTypeResourceAssembler;
import com.noe.rest.exception.AdjustmentTypeNotFoundException;


@RestController
@RequestMapping("api")
public class AdjustmentTypeRestController {
	@Autowired
	private IRepositoryAdjustmentType repository;
	
	@Autowired 
	private AdjustmentTypeResourceAssembler assembler;
	
	@GetMapping("/adjustmentTypes")
	public CollectionModel<EntityModel<AdjustmentType>> all(){
		
		List<EntityModel<AdjustmentType>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(AdjustmentTypeRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/adjustmentTypes")
	public ResponseEntity<?> create(@RequestBody AdjustmentType newAdjustmentType)  throws URISyntaxException{
	    EntityModel<AdjustmentType> entity = assembler.toModel(repository.save(newAdjustmentType));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/adjustmentTypes/{id}")
	public EntityModel<AdjustmentType> one(@PathVariable Long id){
		AdjustmentType entity = this.repository.findById(id).orElseThrow(()->new AdjustmentTypeNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/adjustmentTypes/{id}")
	public ResponseEntity<?> replace(@RequestBody AdjustmentType newAdjustmentType, @PathVariable Long id)throws URISyntaxException {
		AdjustmentType updated = repository.findById(id)
				.map(adjustmentType -> {
						adjustmentType.setCode(newAdjustmentType.getCode());
						adjustmentType.setDescription(newAdjustmentType.getDescription());
						return repository.save(adjustmentType);
				})
				.orElseGet(() -> {
						newAdjustmentType.setId(id);
						return repository.save(newAdjustmentType);
				});
		EntityModel<AdjustmentType> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/adjustmentTypes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
