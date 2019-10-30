package com.santana.java.back.end.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.santana.java.back.end.dto.ProductDTO;
import com.santana.java.back.end.exception.ProductNotFoundException;
import com.santana.java.back.end.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
		
	@GetMapping("/product")
	public List<ProductDTO> getUsers() {		
		List<ProductDTO> usuarios = productService.getAll();		
		return usuarios;
	}
	
	@GetMapping("/product/{id}")
	ProductDTO findById(@PathVariable Long id) {
	    return productService.findById(id);
	}
	
	@PostMapping("/newProduct")
	ProductDTO newUser(@RequestBody ProductDTO userDTO) {		
	    return productService.save(userDTO);
	}
		
	@DeleteMapping("/product/{id}")
	ProductDTO delete(@PathVariable Long id) throws ProductNotFoundException {
	    return productService.delete(id);
	}
	
}
