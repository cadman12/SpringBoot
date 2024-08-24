package edu.pnu.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
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
@Entity
@NamedEntityGraph (
	name = "Member.boardList",
	attributeNodes = @NamedAttributeNode("boardList")
)
public class Member {

	@Id
	private String username;
	private String password;
	@Builder.Default
	private String role = "user";
	@Builder.Default
	private Boolean enabled = true;
	
	@BatchSize(size = 5)
	@JsonIgnore
	@ToString.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<Board> boardList = new ArrayList<>();
}
