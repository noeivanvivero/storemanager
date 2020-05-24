package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.ComercialUnit;
import com.noe.rest.controller.ComercialUnitRestController; 

@Component
public class ComercialUnitResourceAssembler implements RepresentationModelAssembler<ComercialUnit, EntityModel<ComercialUnit>>{

	@Override
	public EntityModel<ComercialUnit> toModel(ComercialUnit entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(ComercialUnitRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(ComercialUnitRestController.class).all()).withRel("comercialUnits")
		);
	}

}
