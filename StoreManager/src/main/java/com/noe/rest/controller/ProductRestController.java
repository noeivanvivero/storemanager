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

import com.noe.data.Product;
import com.noe.repository.IRepositoryProduct;
import com.noe.rest.assembly.ProductResourceAssembler;
import com.noe.rest.exception.ProductNotFoundException;


@RestController
@RequestMapping("api")
public class ProductRestController {
	@Autowired
	private IRepositoryProduct repository;
	
	@Autowired 
	private ProductResourceAssembler assembler;
	
	@GetMapping("/products")
	public CollectionModel<EntityModel<Product>> all(){
		
		List<EntityModel<Product>> collection = this.repository.findAll()
				.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return new CollectionModel<>(collection, 
				linkTo(methodOn(ProductRestController.class).all()).withSelfRel());
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> create(@RequestBody Product newProduct)  throws URISyntaxException{
	    EntityModel<Product> entity = assembler.toModel(repository.save(newProduct));
	    
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}
	
	@GetMapping("/products/{id}")
	public EntityModel<Product> one(@PathVariable Long id){
		Product entity = this.repository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
		return assembler.toModel(entity);
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> replace(@RequestBody Product newProduct, @PathVariable Long id)throws URISyntaxException {
		Product updated = repository.findById(id)
				.map(product -> {
					product.setBuyPrice(newProduct.getBuyPrice());
					product.setCategory(newProduct.getCategory());
					product.setName(newProduct.getName());
					product.setSalePrice(newProduct.getSalePrice());
					product.setTaxes(newProduct.getTaxes());
					product.setUom(newProduct.getUom());
					return repository.save(product);
				})
				.orElseGet(() -> {
					newProduct.setId(id);
					return repository.save(newProduct);
				});
		EntityModel<Product> entity = assembler.toModel(updated);
		return ResponseEntity
				.created(new URI(entity.getRequiredLink("self").getHref()))
				.body(entity);
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		 repository.deleteById(id);
		 return ResponseEntity.noContent().build();
	}
}
