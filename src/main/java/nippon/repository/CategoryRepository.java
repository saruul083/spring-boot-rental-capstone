package nippon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nippon.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	boolean existsByName(String name);
}
