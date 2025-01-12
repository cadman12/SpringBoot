package edu.pnu.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "M2M_TABLE1")
public class M2MTable1 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE1_ID")
	private Long id;
	
	private String username;
	private String val1;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "TABLE1_TABLE2",
		joinColumns = @JoinColumn(name = "TABLE1_ID"),
		inverseJoinColumns = @JoinColumn(name = "TABLE2_ID")
	)
	private Set<M2MTable2> table2set = new HashSet<>();
	
	public void setTable2set(M2MTable2 table) {
		table2set.add(table);
		table.getTable1set().add(this);
	}
}
