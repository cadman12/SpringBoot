package edu.pnu.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "M2O_MEMBER")
public class M2OMember {
	@Id
	private String username;
	private String password = "1234";
	private String role = "ROLE_USER";
	private boolean enabled = true;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<M2OBoard> list = new ArrayList<>();
}