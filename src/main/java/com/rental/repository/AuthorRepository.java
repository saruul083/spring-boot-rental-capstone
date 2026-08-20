package com.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

}
