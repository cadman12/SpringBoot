package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryRepositoryCustom {
}
