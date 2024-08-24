package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
