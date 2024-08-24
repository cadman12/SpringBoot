package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
