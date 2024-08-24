package edu.pnu;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.domain.MemberID;

public class JPAClient {

	private void printTitleLine(String title) {
		System.out.println("=".repeat(80));
		System.out.println(title);
		System.out.println("-".repeat(80));
	}	
	
	public void insertData(EntityManager em) {
		
		printTitleLine("InsertData");
		
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();

		Member m1 = new Member(1L, "홍길동", "1234", "hong@kildong", "ROLE_USER");
		em.persist(m1);
		Member m2 = new Member(2L, "이순신", "1234", "lee@sunsin", "ROLE_MANAGER");
		em.persist(m2);

		for (int i = 1 ; i <= 5 ; i++)
			em.persist(Board.builder()
							.title("제목1"+i)
							.content("내용1"+i)
							.member(m1)
							.build());

		for (int i = 1 ; i <= 5 ; i++)
			em.persist(Board.builder()
					.title("제목2"+i)
					.content("내용2"+i)
					.member(m2)
					.build());
		
		tx.commit();
	}

	public void jpqltest(EntityManager em) {
		
		printTitleLine("jpqltest");	
		
		// 복합키를 이용한 검색 방법
		Member m = em.find(Member.class, MemberID.builder().id(1L).username("홍길동").build());

		if (m == null) {
			System.out.println("==> No Data!!");
			return;
		}
		System.out.println(m.toString());
		
		List<Member> rlist = em.createQuery("select m from Member m where email like :email", Member.class)
				.setParameter("email", "%@k%")
				.getResultList();

		for (Member tm : rlist) {
			System.out.println(tm.toString());
		}		
	}

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATest");
		EntityManager em = emf.createEntityManager();

		JPAClient j = new JPAClient();

		j.insertData(em);
		j.jpqltest(em);
		
		em.close();
		emf.close();
	}

}
