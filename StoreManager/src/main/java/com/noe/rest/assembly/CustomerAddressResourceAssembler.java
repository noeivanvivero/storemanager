package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.CustomerAddress;
import com.noe.rest.controller.CustomerAddressRestController; 

@Component
public class CustomerAddressResourceAssembler implements RepresentationModelAssembler<CustomerAddress, EntityModel<CustomerAddress>>{

	@Override
	public EntityModel<CustomerAddress> toModel(CustomerAddress entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(CustomerAddressRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(CustomerAddressRestController.class).all()).withRel("customerAddresses")
		);
	}

}
