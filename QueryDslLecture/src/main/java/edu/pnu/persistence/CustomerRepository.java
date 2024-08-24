package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
