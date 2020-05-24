package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.UnitOfMeasure;
import com.noe.rest.controller.UnitOfMeasureRestController; 

@Component
public class UnitOfMeasureResourceAssembler implements RepresentationModelAssembler<UnitOfMeasure, EntityModel<UnitOfMeasure>>{

	@Override
	public EntityModel<UnitOfMeasure> toModel(UnitOfMeasure entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(UnitOfMeasureRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UnitOfMeasureRestController.class).all()).withRel("unitsOfMeasure")
		);
	}

}
