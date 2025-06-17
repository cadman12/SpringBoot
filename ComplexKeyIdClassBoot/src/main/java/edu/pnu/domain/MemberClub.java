package edu.pnu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@IdClass(MemberClubId.class)
public class MemberClub {
	
	@Id
	private String username;
	
	@Id
	@Column(name="club_id")
	private Long clubId;
	
	@JsonIgnore
	@ToString.Exclude
    @ManyToOne
    // insertable = false, updatable = false 필수
    // ==> username 컬럼은 위에 있는 @Id 필드에서 처리하니, member 연관관계에서는 단지 참조만 하고, 실제 DB 쓰기 작업에는 관여하지 마라.
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private Member member;

	@JsonIgnore
	@ToString.Exclude
    @ManyToOne
    // insertable = false, updatable = false 필수
    // ==> clubId 컬럼은 위에 있는 @Id 필드에서 처리하니, club 연관관계에서는 단지 참조만 하고, 실제 DB 쓰기 작업에는 관여하지 마라.
    @JoinColumn(name = "club_id", insertable = false, updatable = false)
    private Club club;

	public MemberClub(Member member, Club club) {
		this.member = member;
		this.club = club;
		this.username = member.getUsername();
		this.clubId = club.getId();
	}

    
}
