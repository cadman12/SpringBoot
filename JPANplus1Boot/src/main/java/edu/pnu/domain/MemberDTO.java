package edu.pnu.domain;

import java.util.List;
import java.util.stream.Collectors;

import edu.pnu.domain.dto.BoardDTO;
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
public class MemberDTO {

	private String username;
	private String password;
	private String role;
	private Boolean enabled;
	private List<BoardDTO> boardList;
	
	public static MemberDTO of(Member member) {
		return MemberDTO.builder()
				.username(member.getUsername())
				.password(member.getPassword())
				.role(member.getRole())
				.enabled(member.getEnabled())
				//.boardList(new ArrayList<Board>(member.getBoardList()))
				//.boardList(member.getBoardList().stream().collect(Collectors.toList()))
				.boardList(member.getBoardList().stream().map(b-> {
					return BoardDTO.builder()
							.id(b.getId())
							.title(b.getTitle())
							.content(b.getContent())
							.regiDate(b.getRegiDate())
							.username(member.getUsername())
							.build();
				}).collect(Collectors.toList()))
				.build();
	}	
}
