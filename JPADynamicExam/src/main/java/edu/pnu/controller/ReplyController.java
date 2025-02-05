package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Board;
import edu.pnu.service.ReplyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/reply")
    public List<Board> getReplys(Board board) {
        return replyService.getReplys(board);
    }
    
    @PostMapping("/reply")
    public Board insertReply(Long pid, Board board) {
    	return replyService.insertReply(pid, board);
    }
    
    @PutMapping("/reply")
    public Board updateReply(Board board) {
    	return replyService.updateReply(board);
    }
    
    @DeleteMapping("/reply")
    public void deleteReply(Board board) {
    	replyService.deleteReply(board);
    }
}
