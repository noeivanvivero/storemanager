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

import com.noe.data.CustomerAddress;
import com.noe.repository.IRepositoryCustomerAddress;
import com.noe.rest.assembly.CustomerAddressResourceAssembler;
import com.noe.rest.exception.CustomerAddressNotFoundException;


@RestController
@RequestMapping("api")
public class CustomerAddressRestController {
	@Autowired
	private IRepositoryCustomerAddress repository;
	
	@Autowired 
	private CustomerAddressResourceAssembler assembler;
	
	@GetMapping("/customerAddresses")
	public CollectionModel<EntityModel<CustomerAddress>> all(){
		
		List<EntityModel<CustomerAddress>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(CustomerAddressRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/customerAddresses")
	public ResponseEntity<?> create(@RequestBody CustomerAddress newCustomerAddress)  throws URISyntaxException{
	    EntityModel<CustomerAddress> entity = assembler.toModel(repository.save(newCustomerAddress));	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/customerAddresses/{id}")
	public EntityModel<CustomerAddress> one(@PathVariable Long id){
		CustomerAddress entity = this.repository.findById(id).orElseThrow(()->new CustomerAddressNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/customerAddresses/{id}")
	public ResponseEntity<?> replace(@RequestBody CustomerAddress newCustomerAddress, @PathVariable Long id)throws URISyntaxException {
		CustomerAddress updated = repository.findById(id)
				.map(customerAddress -> {
						customerAddress.setCity(newCustomerAddress.getCity());
						customerAddress.setCountry(newCustomerAddress.getCountry());
						customerAddress.setCustomer(newCustomerAddress.getCustomer());
						customerAddress.setDescription(newCustomerAddress.getDescription());
						customerAddress.setExterior(newCustomerAddress.getExterior());
						customerAddress.setInterior(newCustomerAddress.getInterior());
						customerAddress.setStreet(newCustomerAddress.getStreet());
						customerAddress.setUrbanization(newCustomerAddress.getUrbanization());
						return repository.save(customerAddress);
				})
				.orElseGet(() -> {
						newCustomerAddress.setId(id);
						return repository.save(newCustomerAddress);
				});
		EntityModel<CustomerAddress> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/customerAddresses/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
