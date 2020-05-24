package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.AdjustmentType;
import com.noe.rest.controller.AdjustmentTypeRestController; 

@Component
public class AdjustmentTypeResourceAssembler implements RepresentationModelAssembler<AdjustmentType, EntityModel<AdjustmentType>>{

	@Override
	public EntityModel<AdjustmentType> toModel(AdjustmentType entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(AdjustmentTypeRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(AdjustmentTypeRestController.class).all()).withRel("adjustmentTypes")
		);
	}

}
