package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	
//	@JoinColumn(name = "username", foreignKey = @ForeignKey(name = "fk_board_member", foreignKeyDefinition = "FOREIGN KEY (username) REFERENCES member(username) ON DELETE CASCADE"))
//	@JoinColumn(name = "username", foreignKey = @ForeignKey(name = "fk_board_member", foreignKeyDefinition = "FOREIGN KEY (username) REFERENCES member(username) ON DELETE SET NULL"))
	@ManyToOne
	@JoinColumn(name = "username")
	private Member member;
}
