package com.project.skill_share.services;
import java.util.Optional;

import com.project.skill_share.entities.Category;
import com.project.skill_share.response.GenericResponse;

//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.project.skill_share.entities.Category;
//import com.project.skill_share.repository.CategoryRepository;
//import com.project.skill_share.response.GenericResponse;
//
//@Service
//public class CategoryService {
//   @Autowired
//        private CategoryRepository CatRepo;
//   
//   public GenericResponse getAllCategories() {
//       List<Category> categories = CatRepo.findAll();
//       if(categories.isEmpty()) {
//    	   throw new IllegalArgumentException("Categoriers not Found!!");
//       }
//       return new GenericResponse(true, "Categories fetched successfully", categories);
//   }
//  
//   public GenericResponse registerCat(Category category) {
//	   if(CatRepo.existsByCategoryName(category.getCategoryName())){
//		   throw new IllegalArgumentException("Category already exists!" );
//	   }
//	   Category saved = CatRepo.save(category);
//	   return new GenericResponse(true ,"Added Successfully!!",saved);   
//   }


public interface CategoryService {
    Optional<Category> findById(long id); // used in SkillService
    GenericResponse getAllCategories();  // for controller use
    GenericResponse registerCat(Category category); //controller use 
}
