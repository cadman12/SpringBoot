package edu.pnu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.jpa.impl.JPAQueryFactory;

import edu.pnu.domain.Customer;
import edu.pnu.domain.QCustomer;
import edu.pnu.util.TestUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class Test03_QueryDsl_JPAQueryFactory {

	@Autowired
	private EntityManagerFactory entityManagerFactory;	

	// CustomConfig에서 Bean으로 등록한 객체
	@Autowired
	private JPAQueryFactory queryFactory;
	
	@DisplayName("JPAQueryFactory - dataInsert")
	@Test
	@Order(1)
	// Transaction이 없이는 실행이 안되는데 @Transactional로는 반영안됨.
	// EntityManager 객체를 생성해서 Transaction을 설정한 뒤에 입력하면 정상적으로 됨.
	void dataInsert() {

		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		JPAQueryFactory qFac = new JPAQueryFactory(em);

		TestUtil.drawTitle("JPAQueryFactory - dataInsert");		

		List<String[]> splitUpNames = new ArrayList<>();
		List<String> nameList = Arrays.asList("Bob Smith", "Bob Wilson", "길동 홍");
		nameList.forEach(name->{
			splitUpNames.add(name.split(" "));
		});

		long result = 0L;
		QCustomer qcustomer = QCustomer.customer;
		for (String[] names : splitUpNames) {
			result += qFac.insert(QCustomer.customer)
						.columns(qcustomer.firstName, qcustomer.lastName)
						.values(names[0], names[1])
						.execute();
		}
		tx.commit();
		em.close();
		System.out.println(String.format("%d개의 행이 입력되었습니다.", result));		
	}

	@DisplayName("JPAQueryFactory - selectFromFetchAll")
	@Test
	@Order(2)
	void selectFromFetchAll() {

		TestUtil.drawTitle("JPAQueryFactory - selectFromFetchAll");

		QCustomer qcustomer = QCustomer.customer;
		List<Customer> customers = queryFactory.selectFrom(qcustomer).fetch();

		customers.forEach(cust->System.out.println(cust));
	}
	
	@DisplayName("JPAQueryFactory - selectFromFetchOne")
	@Test
	@Order(3)
	void selectFromFetchOne() {

		TestUtil.drawTitle("JPAQueryFactory - selectFromFetchOne");

		QCustomer qcustomer = QCustomer.customer;
		Customer customer = queryFactory.selectFrom(qcustomer).where(qcustomer.firstName.eq("길동")).fetchOne();

		System.out.println(customer);
	}
	
	@DisplayName("JPAQueryFactory - selectFromMultipleFilterTest")
	@Test
	@Order(4)
	void selectFromMultipleFilterTest() {

		TestUtil.drawTitle("JPAQueryFactory - selectFromMultipleFilterTest");

		QCustomer qcustomer = QCustomer.customer;
	
		List<Customer> list = queryFactory.selectFrom(qcustomer).where(qcustomer.firstName.eq("Bob"), qcustomer.lastName.eq("Wilson")).fetch();
		//List<Customer> list = queryFactory.selectFrom(qcustomer).where(qcustomer.firstName.eq("Bob").and(qcustomer.lastName.eq("Wilson"))).fetch();
		// 위 두 라인은 동일한 기능을 수행하는 코드임.

		list.forEach(c->System.out.println(c));
	}

	@DisplayName("JPAQueryFactory - updateData")
	@Test
	@Order(5)
	// Transaction이 없이는 실행이 안되는데 @Transactional로는 반영안됨.
	// EntityManager 객체를 생성해서 Transaction을 설정한 뒤에 입력하면 정상적으로 됨.
	void updateData() {
		
		TestUtil.drawTitle("JPAQueryFactory - updateData");
		
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		JPAQueryFactory qFac = new JPAQueryFactory(em);
		
		QCustomer qcustomer = QCustomer.customer;
		
		qFac.update(qcustomer)
					.set(qcustomer.firstName, "순신")
					.set(qcustomer.lastName, "이")
					.where(qcustomer.id.eq(1L))
					.execute();
		
		tx.commit();
		em.close();
		
		List<Customer> customers = queryFactory.selectFrom(qcustomer).fetch();

		customers.forEach(cust->System.out.println(cust));
	}
	
	@DisplayName("JPAQueryFactory - deleteData")
	@Test
	@Order(6)
	// Transaction이 없이는 실행이 안되는데 @Transactional로는 반영안됨.
	// EntityManager 객체를 생성해서 Transaction을 설정한 뒤에 입력하면 정상적으로 됨.
	void deleteData() {
		
		TestUtil.drawTitle("JPAQueryFactory - deleteData");
		
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		JPAQueryFactory qFac = new JPAQueryFactory(em);
		
		QCustomer qcustomer = QCustomer.customer;
		
		qFac.delete(qcustomer)
					.where(qcustomer.id.eq(2L))
					.execute();
		
		tx.commit();
		em.close();
		
		List<Customer> customers = queryFactory.selectFrom(qcustomer).fetch();

		customers.forEach(cust->System.out.println(cust));
	}	
}