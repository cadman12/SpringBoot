package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Member {

	@Id
	private String username;
	private String password;
	@Builder.Default
	private String role = "user";
	@Builder.Default
	private Boolean enabled = true;
}
