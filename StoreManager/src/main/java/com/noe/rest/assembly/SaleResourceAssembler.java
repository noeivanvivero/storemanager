package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.Sale;
import com.noe.rest.controller.SaleRestController; 

@Component
public class SaleResourceAssembler implements RepresentationModelAssembler<Sale, EntityModel<Sale>>{

	@Override
	public EntityModel<Sale> toModel(Sale entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(SaleRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(SaleRestController.class).all()).withRel("sales")
		);
	}

}
