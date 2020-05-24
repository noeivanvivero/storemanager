package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.Customer;
import com.noe.rest.controller.CustomerRestController; 

@Component
public class CustomerResourceAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>>{

	@Override
	public EntityModel<Customer> toModel(Customer entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(CustomerRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(CustomerRestController.class).all()).withRel("customers")
		);
	}

}
