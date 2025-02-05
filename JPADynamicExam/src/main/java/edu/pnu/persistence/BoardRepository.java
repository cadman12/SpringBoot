package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
