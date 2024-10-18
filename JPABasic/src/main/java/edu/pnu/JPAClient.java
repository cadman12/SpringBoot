package edu.pnu;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;

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

		Member m1 = new Member("홍길동", "hong@kildong", "010-1224-5677");
		em.persist(m1);
		Member m2 = new Member("이순신", "lee@sunsin", "010-9999-9999");
		em.persist(m2);

		for (int i = 1 ; i <= 5 ; i++)
			em.persist(new Board("제목1"+i, "내용1"+i, m1));

		for (int i = 1 ; i <= 5 ; i++)
			em.persist(new Board("제목2"+i, "내용2"+i, m2));
		
		tx.commit();
	}

	public void jpqltest(EntityManager em) {

		printTitleLine("jpqltest");		
		
		Member m = em.find(Member.class, 1L);
		if (m == null) {
			System.out.println("==> No Data!!");
			return;
		}
		System.out.println(m.toString());
		List<Board> list = m.getList();
		for (Board b : list) {
			System.out.println(b.toString());
		}

		List<Member> rlist = em.createQuery("select m from Member m where email like :email", Member.class)
				.setParameter("email", "%kil%")
				.getResultList();

		for (Member tm : rlist) {
			System.out.println(tm.toString());
		}		
	}
	
	@SuppressWarnings("unchecked")
	public void nativequerytest(EntityManager em) {
		
		printTitleLine("nativequerytest");
		
		Query query = em.createNativeQuery("select b.*, m.EMAIL,m.NAME,m.PHONE from Board b join Member m on b.MEMBER_SEQ=m.SEQ");

		try {
			List<Object[]> list = query.getResultList();
			for (Object[] objs : list) {
				for (Object obj : objs)
					System.out.print(obj.toString() + " ");
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATest");
		EntityManager em = emf.createEntityManager();

		JPAClient j = new JPAClient();

		j.insertData(em);
		j.jpqltest(em);
		j.nativequerytest(em);
		
		em.close();
		emf.close();
	}
}
