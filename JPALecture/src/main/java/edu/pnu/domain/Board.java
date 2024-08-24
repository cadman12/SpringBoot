package edu.pnu.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Board {
	@Id
	@GeneratedValue
	private Long seq;
	private String title;
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date postDate = new Date();
	private Long cnt = 0L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Member member;
	
	public Board() {
	}

	public Board(String title, String content, Member member) {
		this.title = title;
		this.content = content;
		this.member = member;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String toString() {
		return "Board [seq=" + seq + ", title=" + title + ", content=" + content + ", postDate=" + postDate + ", cnt="
				+ cnt + ", member=" + member.toString() + "]";
	}	
}
