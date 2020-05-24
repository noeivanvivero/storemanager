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

import com.noe.data.Customer;
import com.noe.repository.IRepositoryCustomer;
import com.noe.rest.assembly.CustomerResourceAssembler;
import com.noe.rest.exception.CustomerNotFoundException;


@RestController
@RequestMapping("api")
public class CustomerRestController {
	@Autowired
	private IRepositoryCustomer repository;
	
	@Autowired 
	private CustomerResourceAssembler assembler;
	
	@GetMapping("/customers")
	public CollectionModel<EntityModel<Customer>> all(){
		
		List<EntityModel<Customer>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(CustomerRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/customers")
	public ResponseEntity<?> create(@RequestBody Customer newCustomer)  throws URISyntaxException{
	    EntityModel<Customer> entity = assembler.toModel(repository.save(newCustomer));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/customers/{id}")
	public EntityModel<Customer> one(@PathVariable Long id){
		Customer entity = this.repository.findById(id).orElseThrow(()->new CustomerNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/customers/{id}")
	public ResponseEntity<?> replace(@RequestBody Customer newCustomer, @PathVariable Long id)throws URISyntaxException {
		Customer updated = repository.findById(id)
				.map(customer -> {
						customer.setAddresses(newCustomer.getAddresses());
						customer.setEmail(newCustomer.getEmail());
						customer.setLastname(newCustomer.getLastname());
						customer.setName(newCustomer.getLastname());
						customer.setPassword(newCustomer.getPassword());
						customer.setPhone(newCustomer.getPhone());
						customer.setRfc(newCustomer.getRfc());
						return repository.save(customer);
				})
				.orElseGet(() -> {
						newCustomer.setId(id);
						return repository.save(newCustomer);
				});
		EntityModel<Customer> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
