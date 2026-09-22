package nippon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nippon.dto.CategoryCreateRequest;
import nippon.dto.CategoryResponse;
import nippon.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	List<CategoryResponse> findAllCategories() {
		return categoryService.findAllCategories();
	}
	
	@GetMapping("/{id}")
	public CategoryResponse findById(@PathVariable Long id) {
		return categoryService.findCategoryById(id);
}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryResponse create(@RequestBody CategoryCreateRequest request) {
		return categoryService.createCategory(request);
	}
	
	@PutMapping("/{id}")
	public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryCreateRequest request) {
		return categoryService.updateCategory(id, request);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		categoryService.deleteCategory(id);
	}
}
