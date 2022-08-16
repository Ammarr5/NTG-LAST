package com.example.demospringsecurityclient.service;

import java.util.List;
import java.util.Optional;

import com.example.demospringsecurityclient.error.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demospringsecurityclient.entity.Product;
import com.example.demospringsecurityclient.model.ProductAdd;
import com.example.demospringsecurityclient.repository.CategoryRepository;
import com.example.demospringsecurityclient.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private  CategoryRepository categoryRepository;
	
	@Override
	public List<Product> getAllProducts() {
		
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public ProductAdd createNewProduct(ProductAdd newproduct) {
		if (newproduct != null) {
			Product product=new Product();
			product.setName(newproduct.getName());
			product.setDescription(newproduct.getDescription());
			product.setPrice(newproduct.getPrice());
			product.setImageurl(newproduct.getImageurl());
			product.setCategory(categoryRepository.findById(newproduct.getCategory_id()).get());
			productRepository.save(product);
		return newproduct;
		}
		return null;
	}

	@Override
	public boolean deleteProductById(Long id) {
		if (id != null) {
			productRepository.deleteById(id);
			return true;
		}

		return false;
	}

	@Override
	public Product getProductById(Long productId) throws ProductNotFoundException {
		Optional<Product> product = productRepository.findById(productId);
		if (!product.isPresent()) {
			throw new ProductNotFoundException("Product doesn't exist");
		}
		return product.get();
	}

//	@Override
//	public Product createNewProduct(Product newproduct) {
//		if (newproduct != null) {
//			return productRepository.save(newproduct);
//		}
//		return null;
//	}

}
