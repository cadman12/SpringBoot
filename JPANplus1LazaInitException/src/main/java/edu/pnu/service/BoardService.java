package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Board;
import edu.pnu.repository.BoardRepository;
import jakarta.transaction.Transactional;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepo;

	public List<Board> boards() {
		return boardRepo.findAll();
	}

	@Transactional
	public Board board(Long id) {
		return boardRepo.findById(id).orElseThrow();
	}
}
