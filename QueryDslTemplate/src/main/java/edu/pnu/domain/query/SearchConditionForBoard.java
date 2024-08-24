package edu.pnu.domain.query;

import java.util.Date;

import edu.pnu.domain.Board;
import edu.pnu.domain.query.type.QueryTypeNumeric;
import edu.pnu.domain.query.type.QueryTypeString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchConditionForBoard extends Board {

	private QueryTypeString titleQueryType;
	private QueryTypeString contentQueryType;
	private QueryTypeNumeric dateQueryType;

	// from은 Board.date를 이용
	private Date toDate;
	
	public boolean isNullQuery() {
		if (isNullBoard()				&&
			titleQueryType		== null	&&
			contentQueryType	== null	&&
			dateQueryType		== null	&&
			toDate				== null)
			return true;
		return false;
	}
}
