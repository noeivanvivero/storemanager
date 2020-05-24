package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.Tax;
import com.noe.rest.controller.TaxRestController; 

@Component
public class TaxResourceAssembler implements RepresentationModelAssembler<Tax, EntityModel<Tax>>{

	@Override
	public EntityModel<Tax> toModel(Tax entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(TaxRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(TaxRestController.class).all()).withRel("taxes")
		);
	}

}
