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

import com.noe.data.Inventory;
import com.noe.repository.IRepositoryInventory;
import com.noe.rest.assembly.InventoryResourceAssembler;
import com.noe.rest.exception.InventoryNotFoundException;


@RestController
@RequestMapping("api")
public class InventoryRestController {
	@Autowired
	private IRepositoryInventory repository;
	
	@Autowired 
	private InventoryResourceAssembler assembler;
	
	@GetMapping("/inventories")
	public CollectionModel<EntityModel<Inventory>> all(){
		
		List<EntityModel<Inventory>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(InventoryRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/inventories")
	public ResponseEntity<?> create(@RequestBody Inventory newInventory)  throws URISyntaxException{
	    EntityModel<Inventory> entity = assembler.toModel(repository.save(newInventory));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/inventories/{id}")
	public EntityModel<Inventory> one(@PathVariable Long id){
		Inventory entity = this.repository.findById(id).orElseThrow(()->new InventoryNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/inventories/{id}")
	public ResponseEntity<?> replace(@RequestBody Inventory newInventory, @PathVariable Long id)throws URISyntaxException {
		Inventory updated = repository.findById(id)
				.map(inventory -> {
					inventory.setProduct(newInventory.getProduct());
					inventory.setQuantity(newInventory.getQuantity());
					inventory.setSucursal(newInventory.getSucursal());
					return repository.save(inventory);
				})
				.orElseGet(() -> {
					newInventory.setId(id);
					return repository.save(newInventory);
				});
		EntityModel<Inventory> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/inventories/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
