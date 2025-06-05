package com.project.skill_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.entities.Category;
import com.project.skill_share.response.GenericResponse;
import com.project.skill_share.services.CategoryService;

@RestController
@RequestMapping("/api")

public class CategoryController {
	
   @Autowired
   private CategoryService CatService;
    
    
    @GetMapping("/category")
    public ResponseEntity<GenericResponse> getAllCategories() {
        return ResponseEntity.ok(CatService.getAllCategories());
    }
    
    @PostMapping("/add/category")
    public ResponseEntity<GenericResponse> createCategory(@RequestBody Category category) {    
        return ResponseEntity.ok(CatService.registerCat(category));
    }
}
