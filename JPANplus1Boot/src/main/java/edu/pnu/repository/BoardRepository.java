package edu.pnu.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	@Query("select b from Board b join fetch b.member")
	List<Board> boardTest01();
	
	@Query("select b from Board b left join fetch b.member where b.id<10 order by b.id asc")
	List<Board> boardTest02(PageRequest pagable);
	
}
