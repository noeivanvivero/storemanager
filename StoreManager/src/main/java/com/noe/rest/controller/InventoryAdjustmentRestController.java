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

import com.noe.data.InventoryAdjustment;
import com.noe.repository.IRepositoryInventoryAdjustment;
import com.noe.rest.assembly.InventoryAdjustmentResourceAssembler;
import com.noe.rest.exception.InventoryAdjustmentNotFoundException;


@RestController
@RequestMapping("api")
public class InventoryAdjustmentRestController {
	@Autowired
	private IRepositoryInventoryAdjustment repository;
	
	@Autowired 
	private InventoryAdjustmentResourceAssembler assembler;
	
	@GetMapping("/inventoryAdjustments")
	public CollectionModel<EntityModel<InventoryAdjustment>> all(){
		
		List<EntityModel<InventoryAdjustment>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(InventoryAdjustmentRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/inventoryAdjustments")
	public ResponseEntity<?> create(@RequestBody InventoryAdjustment newInventoryAdjustment)  throws URISyntaxException{
	    EntityModel<InventoryAdjustment> entity = assembler.toModel(repository.save(newInventoryAdjustment));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/inventoryAdjustments/{id}")
	public EntityModel<InventoryAdjustment> one(@PathVariable Long id){
		InventoryAdjustment entity = this.repository.findById(id).orElseThrow(()->new InventoryAdjustmentNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/inventoryAdjustments/{id}")
	public ResponseEntity<?> replace(@RequestBody InventoryAdjustment newInventoryAdjustment, @PathVariable Long id)throws URISyntaxException {
		InventoryAdjustment updated = repository.findById(id)
				.map(inventoryAdjustment -> {
					inventoryAdjustment.setEmployee(newInventoryAdjustment.getEmployee());
					inventoryAdjustment.setInventory(newInventoryAdjustment.getInventory());
					inventoryAdjustment.setQuantity(newInventoryAdjustment.getQuantity());
					inventoryAdjustment.setType(newInventoryAdjustment.getType());
					return repository.save(inventoryAdjustment);
				})
				.orElseGet(() -> {
					newInventoryAdjustment.setId(id);
					return repository.save(newInventoryAdjustment);
				});
		EntityModel<InventoryAdjustment> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/inventoryAdjustments/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
