package edu.pnu.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "M2M_MEMBER")
public class M2MMember {
	@Id
	private String username;
	private String password = "1234";
	private String role = "ROLE_USER";
	private boolean enabled = true;
	
	@ToString.Exclude
	@JsonIgnore
	@ManyToMany(mappedBy = "followers", cascade = CascadeType.ALL)
    private Set<M2MMember> following = new HashSet<>();

	@ToString.Exclude
	@JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
   		name = "follow_mapping",
   		joinColumns = @JoinColumn(name = "FOLLOWER_USERNAME"),
   		inverseJoinColumns = @JoinColumn(name = "FOLLOWING_USERNAME")
	)
    private Set<M2MMember> followers = new HashSet<>();
    
    public void setFollowing(M2MMember member) {
    	following.add(member);
    	member.getFollowers().add(this);
    }
}