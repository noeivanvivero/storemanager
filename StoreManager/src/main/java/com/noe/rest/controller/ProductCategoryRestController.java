package com.noe.rest.controller;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; 
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noe.data.ProductCategory;
import com.noe.repository.IRepositoryProductCategory;
import com.noe.rest.assembly.ProductCategoryResourceAssembler;
import com.noe.rest.exception.ProductCategoryNotFoundException;


@RestController
@RequestMapping("api")
public class ProductCategoryRestController {
	@Autowired
	private IRepositoryProductCategory repository;
	
	@Autowired 
	private ProductCategoryResourceAssembler assembler;
	
	@GetMapping("/productCategories")
	public CollectionModel<EntityModel<ProductCategory>> all(){
		
		List<EntityModel<ProductCategory>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(ProductCategoryRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/productCategories")
	public ResponseEntity<?> create(@RequestBody ProductCategory newProductCategory)  throws URISyntaxException{
	    EntityModel<ProductCategory> entity = assembler.toModel(repository.save(newProductCategory));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/productCategories/{id}")
	public EntityModel<ProductCategory> one(@PathVariable Long id){
		ProductCategory entity = this.repository.findById(id).orElseThrow(()->new ProductCategoryNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/productCategories/{id}")
	public ResponseEntity<?> replace(@RequestBody ProductCategory newProductCategory, @PathVariable Long id)throws URISyntaxException {
		ProductCategory updated = repository.findById(id)
				.map(productCategory -> {
					productCategory.setCode(newProductCategory.getCode());
					productCategory.setDescription(newProductCategory.getDescription());
					return repository.save(productCategory);
				})
				.orElseGet(() -> {
					newProductCategory.setId(id);
					return repository.save(newProductCategory);
				});
		EntityModel<ProductCategory> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/productCategories/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
