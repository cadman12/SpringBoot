package edu.pnu.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

public class InventoryRepositoryImpl implements InventoryRepositoryCustom {

	private final EntityManager entityManager;

    public InventoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
  
    @Override
    public Map<String, Object> film_in_stock(Integer filmId, Integer storeId) {
        // DB에 정의된 StoredProcedure 사용
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("film_in_stock"); 

        // IN 파라미터 설정
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        
        // OUT 파라미터 설정
        query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT);

        // IN 파라미터 값 설정
        query.setParameter(1, filmId);
        query.setParameter(2, storeId);

        Map<String, Object> map = new HashMap<>();
        List<Object> list = new ArrayList<>();
        boolean isResultSet = query.execute();
        do {
	        if (isResultSet) {
	        	list.add(query.getResultList());
	        } else {
	        	map.put("updateCount", query.getUpdateCount());
	        }
	        isResultSet = query.hasMoreResults();
        } while(isResultSet || query.getUpdateCount() != -1);

    	map.put("result", list);
        map.put("p_film_count", (Integer)query.getOutputParameterValue(3));

        return map;
    }
}
