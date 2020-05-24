package com.noe.rest.assembly;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.EntityModel; //Resource
import org.springframework.hateoas.server.RepresentationModelAssembler; //ResourceAssembler
import org.springframework.stereotype.Component;

import com.noe.data.Inventory;
import com.noe.rest.controller.InventoryRestController; 

@Component
public class InventoryResourceAssembler implements RepresentationModelAssembler<Inventory, EntityModel<Inventory>>{

	@Override
	public EntityModel<Inventory> toModel(Inventory entity) {
		return new EntityModel<>(entity, 
				linkTo(methodOn(InventoryRestController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(InventoryRestController.class).all()).withRel("inventories")
		);
	}

}
