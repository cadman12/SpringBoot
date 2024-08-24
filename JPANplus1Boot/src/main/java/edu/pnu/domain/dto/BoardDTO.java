package edu.pnu.domain.dto;

import java.util.Date;

import edu.pnu.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
	private Long id;
	private String title;
	private String content;
	private Date regiDate;
	private String username;
	private Boolean enabled; // 이 필드가 없으면 N+1이 발생안함.
							// username은 이미 Board테이블에 있기 때문
	
	public static BoardDTO of(Board board) {
		return BoardDTO.builder()
				.id(board.getId())
				.title(board.getTitle())
				.content(board.getContent())
				.regiDate(board.getRegiDate())
				.username(board.getMember().getUsername())
				.enabled(board.getMember().getEnabled())
				.build();
	}
}
