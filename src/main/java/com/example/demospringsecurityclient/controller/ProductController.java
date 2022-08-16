package com.example.demospringsecurityclient.controller;


import java.util.List;

import com.example.demospringsecurityclient.error.CategoryNotFoundException;
import com.example.demospringsecurityclient.error.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demospringsecurityclient.entity.Category;
import com.example.demospringsecurityclient.entity.Product;
import com.example.demospringsecurityclient.model.CategoryAdd;
import com.example.demospringsecurityclient.model.CategoryModel;
import com.example.demospringsecurityclient.model.ProductAdd;
import com.example.demospringsecurityclient.service.CategoryService;
import com.example.demospringsecurityclient.service.ProductService;



@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;
	
	@GetMapping(value = "/products/all")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping(value = "/categories/all")
	public List<CategoryModel> getAllCategories() {
		return categoryService.getAllCategories();
	}
	
	@GetMapping(value = "/getproductbyid/{productId}")
	public Product getProductById(@PathVariable Long productId) throws ProductNotFoundException {
		return productService.getProductById(productId);
	}
	
	@GetMapping(value = "/getcategorybyid/{categoryId}")
	public CategoryModel getCategoryById(@PathVariable Long categoryId) throws CategoryNotFoundException {
		return categoryService.getCategoryById(categoryId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = "/add/product")
	public ProductAdd createNewProduct(@RequestBody ProductAdd newproduct) {
		return productService.createNewProduct(newproduct);
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = "/add/category")
	public CategoryAdd createNewCategory(@RequestBody CategoryAdd newcategory) {
		return categoryService.createNewCategory(newcategory);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "/deleteproduct/{productId}")
	public boolean deleteProductById(@PathVariable(value = "productId") Long id) {
		return productService.deleteProductById(id);
	}
	
}
