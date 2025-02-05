package edu.pnu.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.pnu.domain.Board;

@Mapper
public interface BoardMapper {

	List<Board> getBoards(String title, String content, String author);
	void insertBoard(Board board);
	void deleteBoard(Long id);
}
