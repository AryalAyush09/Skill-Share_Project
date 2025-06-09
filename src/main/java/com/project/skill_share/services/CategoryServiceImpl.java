package com.project.skill_share.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.skill_share.GlobalErrorHandler.AlreadyExistsException;
import com.project.skill_share.GlobalErrorHandler.ResourceNotFoundException;
import com.project.skill_share.entities.Category;
import com.project.skill_share.repository.CategoryRepository;
import com.project.skill_share.response.GenericResponse;

@Service
	public class CategoryServiceImpl implements CategoryService {

	    @Autowired 
	    private CategoryRepository catRepo;

	    @Override
	    public Optional<Category> findById(long id) {
	        return catRepo.findById(id);
	    }

	    @Override
	    public GenericResponse getAllCategories() {
	        List<Category> categories = catRepo.findAll();
	        if (categories.isEmpty()) {
	            throw new ResourceNotFoundException("Categories not found!");
	        }
	        return new GenericResponse(true, "Categories fetched successfully", categories);
	    }

	    @Override 
	    public GenericResponse registerCat(Category category) {
	        if (catRepo.existsByCategoryName(category.getCategoryName())) {
	            throw new AlreadyExistsException("Category already exists!");
	        }
	        Category saved = catRepo.save(category);
	        return new GenericResponse(true, "Added Successfully!!", saved);
	    }
	}

