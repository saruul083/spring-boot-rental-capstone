package com.rental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rental.model.Author;
import com.rental.repository.AuthorRepository;

@Service
public class AuthorService {
	private final AuthorRepository authorRepository;
	
	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}
	
	public List<Author> findAllAuthors() {
		return authorRepository.findAll();
	}
	
	public Author findAuthorById(Long id) {
		return authorRepository.findById(id).orElseThrow();
	}
	
	public Author createAuthor(Author author) {
		return authorRepository.save(author);
	}
	
	public Author updateAuthor(Long id, Author newAuthor) {
		Author findAuthor = authorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Author not found with ID: " + id));
		
		findAuthor.setFirstName(newAuthor.getFirstName());
		findAuthor.setLastName(newAuthor.getLastName());
		findAuthor.setBio(newAuthor.getBio());
		
		return authorRepository.save(findAuthor);
	}
	
	public void deleteAuthor(Long id) {
		authorRepository.deleteById(id);
		
	}
}
