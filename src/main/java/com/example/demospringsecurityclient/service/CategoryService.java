package com.example.demospringsecurityclient.service;

import java.util.List;

import com.example.demospringsecurityclient.entity.Category;
import com.example.demospringsecurityclient.entity.Product;
import com.example.demospringsecurityclient.error.CategoryNotFoundException;
import com.example.demospringsecurityclient.model.CategoryAdd;
import com.example.demospringsecurityclient.model.CategoryModel;

public interface CategoryService {

	List<CategoryModel> getAllCategories();

	CategoryAdd createNewCategory(CategoryAdd newcategory);

	CategoryModel getCategoryById(Long categoryId) throws CategoryNotFoundException;

	

	



}
