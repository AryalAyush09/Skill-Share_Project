package com.project.skill_share.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.skill_share.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	    boolean existsByCategoryName(String Categoryname); 
//	    List<Category> findByCategoryName(String categoryName);
	    Optional<Category> findByCategoryName(String categoryName);
}
