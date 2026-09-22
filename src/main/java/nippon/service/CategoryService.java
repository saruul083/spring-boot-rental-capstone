package nippon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import nippon.dto.CategoryCreateRequest;
import nippon.dto.CategoryResponse;
import nippon.exception.DuplicateResourceException;
import nippon.exception.ResourceNotFoundException;
import nippon.model.Category;
import nippon.repository.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<CategoryResponse> findAllCategories() {
		return categoryRepository.findAll().stream().map(this::toResponse).toList();
	}
	
	public CategoryResponse findCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with this ID: " + id));
		return toResponse(category);
	}
	
	public CategoryResponse createCategory(CategoryCreateRequest request) {
		String requestName = request.name().trim();
		
		if (categoryRepository.existsByName(requestName)) {	
			throw new DuplicateResourceException("Category already exists with name: " + requestName); 
		}
		
		Category category = new Category();
		category.setName(requestName);
		category.setDescription(request.description() != null ? request.description().trim() : null);
		
		Category savedCategory = categoryRepository.save(category);
		return toResponse(savedCategory);
	}
	
	public CategoryResponse updateCategory(Long id, CategoryCreateRequest request) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with this ID: " + id));
		
		String requestName = request.name().trim();
		
		category.setName(requestName);
		category.setDescription(request.description() != null ? request.description().trim() : null);
		
		Category updatedCategory = categoryRepository.save(category);
		return toResponse(updatedCategory);
	}
	
	public void deleteCategory(Long id) {
		Category foundCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with this ID: " + id));
		categoryRepository.delete(foundCategory);
	}
	
	public CategoryResponse toResponse(Category category) {
		return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
	}
}
