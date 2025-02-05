package edu.pnu.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.pnu.domain.Board;

@Mapper
public interface BoardMapper {

	Board getBoard(Long id);
	List<Board> getBoards(Board board);
	void insertBoard(Board board);
	void updateBoard(Board board);
	void deleteBoard(Long id);
}
