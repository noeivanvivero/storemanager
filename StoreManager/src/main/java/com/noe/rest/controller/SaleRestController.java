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

import com.noe.data.Sale;
import com.noe.repository.IRepositorySale;
import com.noe.rest.assembly.SaleResourceAssembler;
import com.noe.rest.exception.SaleNotFoundException;


@RestController
@RequestMapping("api")
public class SaleRestController {
	@Autowired
	private IRepositorySale repository;
	
	@Autowired 
	private SaleResourceAssembler assembler;
	
	@GetMapping("/sales")
	public CollectionModel<EntityModel<Sale>> all(){
		
		List<EntityModel<Sale>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(SaleRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/sales")
	public ResponseEntity<?> create(@RequestBody Sale newSale)  throws URISyntaxException{
	    EntityModel<Sale> entity = assembler.toModel(repository.save(newSale));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/sales/{id}")
	public EntityModel<Sale> one(@PathVariable Long id){
		Sale entity = this.repository.findById(id).orElseThrow(()->new SaleNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/sales/{id}")
	public ResponseEntity<?> replace(@RequestBody Sale newSale, @PathVariable Long id)throws URISyntaxException {
		Sale updated = repository.findById(id)
				.map(sale -> {
					sale.setCustomer(newSale.getCustomer());
					sale.setDate(newSale.getDate());
					sale.setDetails(newSale.getDetails());
					sale.setEmployee(newSale.getEmployee());
					sale.setSucursal(newSale.getSucursal());
					return repository.save(sale);
				})
				.orElseGet(() -> {
					newSale.setId(id);
					return repository.save(newSale);
				});
		EntityModel<Sale> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/sales/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
