# Entity 간 연관관계를 맺은 뒤 객체를 삭제 시 Cascade

# Member, Board

	- Member:Board = 1:N

# 단방향인 경우

	. Board 클래스에서 @ManyToOne으로 설정하는 경우
  
	. Board 객체를 삭제하면 정상 삭제 가능
	
	. Member 객체를 삭제하는 경우 연결된 Board 객체가 있으면 삭제 거부됨. Board에 설정된 JoinColumn에서 foreignKey 설정을 생략한 경우 Default인 RESTRICT로 설정되어 있기 때문임.
  
	. Member를 삭제하고자 하면 우선 연결된 Board 객체를 모두 삭제하여야만 가능

	. 만약 Board 객체 삭제없이 Member 객체를 삭제하고자 하는 경우에는 아래와 같은 설정 옵션을 추가해야 함.

 		>> Member 객체와 연결된 Board 객체의 username 필드를 NULL로 설정하고자 하는 경우
			.@JoinColumn(name = "username",
   				foreignKey = @ForeignKey(name = "fk_board_member",
       							foreignKeyDefinition = "FOREIGN KEY (username) REFERENCES member(username) ON DELETE SET NULL"))
  		>> Member 객체와 연결된 Board 객체를 모두 같이 삭제하고자하는 경우
			.@JoinColumn(name = "username",
   				foreignKey = @ForeignKey(name = "fk_board_member",
       							foreignKeyDefinition = "FOREIGN KEY (username) REFERENCES member(username) ON DELETE CASCADE"))
	
# 양방향인 경우 

	. Board 클래스에서 @ManyToOne으로 설정하고 Member 클래스에 @OneToMany로 설정하는 경우
  
	. Board 겍체를 삭제하면 정상 삭제 가능
	
	. Member 객체를 삭제하는 경우 설정된 옵션에 따라 Board 객체가 자동 삭제됨.
  
	. Member 객체를 삭제하면서 연결된 Board 객체를 삭제하지 않으려면 위 단방향인 경우 @JoinColumn 설정하는 것과 같이 설정을 변경해 주어야 함.
