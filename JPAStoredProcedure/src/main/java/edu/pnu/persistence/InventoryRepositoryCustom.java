package edu.pnu.persistence;

import java.util.Map;

public interface InventoryRepositoryCustom {
	Map<String, Object> film_in_stock(Integer filmId, Integer storeId);
}
