package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.InventoryAdjustment;
import com.noe.rest.controller.InventoryAdjustmentRestController; 

@Component
public class InventoryAdjustmentResourceAssembler implements RepresentationModelAssembler<InventoryAdjustment, EntityModel<InventoryAdjustment>>{

	@Override
	public EntityModel<InventoryAdjustment> toModel(InventoryAdjustment entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(InventoryAdjustmentRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(InventoryAdjustmentRestController.class).all()).withRel("inventoryAjustments")
		);
	}

}
