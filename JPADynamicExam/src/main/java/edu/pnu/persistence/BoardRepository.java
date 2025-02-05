package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("select b from Board b where b.board.id=:id")
	List<Board> findReplyByParentid(Long id);
}
