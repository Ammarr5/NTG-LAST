package com.example.demospringsecurityclient.service;

import java.util.List;

import com.example.demospringsecurityclient.entity.Category;
import com.example.demospringsecurityclient.entity.Product;
import com.example.demospringsecurityclient.error.ProductNotFoundException;
import com.example.demospringsecurityclient.model.ProductAdd;

public interface ProductService {

	List<Product> getAllProducts();

	ProductAdd createNewProduct(ProductAdd newproduct);

	boolean deleteProductById(Long id);

	Product getProductById(Long productId) throws ProductNotFoundException;

	//Product createNewProduct(Product newproduct);

	

}
