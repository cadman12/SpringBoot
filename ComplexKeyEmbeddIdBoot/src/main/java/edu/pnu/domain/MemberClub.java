package edu.pnu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class MemberClub {
	@EmbeddedId
    private MemberClubId id;

	@JsonIgnore
	@ToString.Exclude
    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username")
    private Member member;

	@JsonIgnore
	@ToString.Exclude
    @ManyToOne
    @MapsId("clubId")
    @JoinColumn(name = "club_id")
    private Club club;

	public MemberClub(Member member, Club club) {
		this.member = member;
		this.club = club;
		this.id = new MemberClubId(member.getUsername(), club.getId());
	}

    
}
