package edu.pnu.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "M2M_TABLE2")
public class M2MTable2 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE2_ID")
	private Long id;
	
	private String username;
	private String val2;

	@ToString.Exclude
	@JsonIgnore
	@ManyToMany(mappedBy = "table2set", cascade = CascadeType.ALL)
	private Set<M2MTable1> table1set = new HashSet<>();
}
