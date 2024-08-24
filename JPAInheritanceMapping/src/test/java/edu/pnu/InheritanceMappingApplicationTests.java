package edu.pnu;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Album;
import edu.pnu.domain.Book;
import edu.pnu.domain.Movie;
import edu.pnu.persistence.AlbumRepository;
import edu.pnu.persistence.BookRepository;
import edu.pnu.persistence.MovieRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class InheritanceMappingApplicationTests {

	@Autowired private MovieRepository movieRepo;
	@Autowired private AlbumRepository albumRepo;
	@Autowired private BookRepository bookRepo;
	
	@Order(1)
	@Test
	void movieAppend() {

		for (int i = 1 ; i <= 10 ; i++) {
			Movie m = new Movie();
			m.setName("name" + i);
			m.setPrice(1000 + i);
			m.setDirector("director" + i);
			m.setActor("actor" + i);
			
			movieRepo.save(m);
		}
	}

	@Order(2)
	@Test
	void albumAppend() {
		
		for (int i = 1 ; i <= 10 ; i++) {
			Album a = new Album();
			a.setName("name" + i);
			a.setPrice(2000 + i);
			a.setArtist("artist" + i);
			
			albumRepo.save(a);
		}
	}
	
	@Order(3)
	@Test
	void bookAppend() {
		
		for (int i = 1 ; i <= 10 ; i++) {
			Book b = new Book();
			b.setName("name" + i);
			b.setPrice(1000 + i);
			b.setAuthor("author" + i);
			b.setIsbn("isbn0" + i);
			
			bookRepo.save(b);
		}
	}	
}
