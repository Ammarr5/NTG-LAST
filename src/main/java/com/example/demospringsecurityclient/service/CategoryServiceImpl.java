package com.example.demospringsecurityclient.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demospringsecurityclient.error.CategoryNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demospringsecurityclient.entity.Category;
import com.example.demospringsecurityclient.entity.Product;
import com.example.demospringsecurityclient.model.CategoryAdd;
import com.example.demospringsecurityclient.model.CategoryModel;
import com.example.demospringsecurityclient.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private  CategoryRepository categoryRepository;

	@Override
	public List<CategoryModel> getAllCategories() {
		
		List<Category> category = (List<Category>) categoryRepository.findAll();
		List<CategoryModel> categoryModel = null;
		
		if(!category.isEmpty()) {
			categoryModel = new ArrayList<>();
			CategoryModel catDto = null;
			for (Category cat : category) {
				catDto = new CategoryModel();
				BeanUtils.copyProperties(cat, catDto);
				categoryModel.add(catDto);
			}
		}
		
		return categoryModel;
	}

	@Override
	public CategoryAdd createNewCategory(CategoryAdd newcategory) {
		if (newcategory != null) {
			Category category =new Category();
			BeanUtils.copyProperties(newcategory, category);
			 categoryRepository.save(category);
			 return newcategory;
		}
		return null;
	}

	@Override
	public CategoryModel getCategoryById(Long categoryId) throws CategoryNotFoundException {
		Optional<Category> category =  categoryRepository.findById(categoryId);
		if (!category.isPresent()) {
			throw new CategoryNotFoundException("Category not found");
		}
		CategoryModel categoryModel = new CategoryModel();
	     BeanUtils.copyProperties(category, categoryModel);
	
		return categoryModel;
	}

	
	
	}



