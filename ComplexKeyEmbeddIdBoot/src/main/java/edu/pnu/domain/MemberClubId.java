package edu.pnu.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
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
@Embeddable
public class MemberClubId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
    private Long clubId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberClubId)) return false;
        MemberClubId that = (MemberClubId) o;
        return Objects.equals(username, that.username) &&
               Objects.equals(clubId, that.clubId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, clubId);
    }
}
