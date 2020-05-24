package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.UnitType;
import com.noe.rest.controller.UnitTypeRestController; 

@Component
public class UnitTypeResourceAssembler implements RepresentationModelAssembler<UnitType, EntityModel<UnitType>>{

	@Override
	public EntityModel<UnitType> toModel(UnitType entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(UnitTypeRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UnitTypeRestController.class).all()).withRel("unitTypes")
		);
	}

}
