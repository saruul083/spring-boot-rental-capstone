package com.rental.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	boolean existsByIsbn(String isbn);

	boolean existsByIsbnAndIdNot(String isbn, Long id);

	Page<Book> findByActiveTrue(Pageable pageable);

	Page<Book> findByActiveTrueAndTitleContainingIgnoreCase(String keyword, Pageable pageable);

	Page<Book> findByActiveTrueAndCategoryId(Long categoryId, Pageable pageable);

	// category id, title
	Page<Book> findByActiveTrueAndTitleContainingIgnoreCaseAndCategoryId(String keyword, Long categoryId,
			Pageable pageable);
/**
 * BookResponse record 
 * id, title, isbn, price, stockQuantity, active, categoryId, categoryName, authorId, authorFirstName,
 * authorLastName
 */

	/**
	 * 
	 * Book 1 ISBN = ABC123
	 * 
	 * Book 2 ISBN = XYZ999
	 * 
	 * // book 1 => ISBN = ABC123
	 * 
	 * SELECT EXISTS ( SELECT 1 FROM books WHERE isbn = 'ABC123' AND id <> 1 );
	 */
}
