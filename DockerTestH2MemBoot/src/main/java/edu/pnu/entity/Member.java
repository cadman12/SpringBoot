package edu.pnu.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class Member {

	@Id
	private String username;
	private String password;
	@Builder.Default
	private String role = "ROLE_MEMBER";
	@Builder.Default
	@Temporal(TemporalType.TIMESTAMP)
	private Date joinDate = new Date();
}
