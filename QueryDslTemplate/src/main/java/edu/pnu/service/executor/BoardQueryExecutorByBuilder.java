package edu.pnu.service.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;

import edu.pnu.domain.Board;
import edu.pnu.domain.QBoard;
import edu.pnu.domain.query.SearchConditionForBoard;
import edu.pnu.domain.query.type.QueryTypeNumeric;
import edu.pnu.domain.query.type.QueryTypeString;
import edu.pnu.persistence.BoardRepository;

@Service
public class BoardQueryExecutorByBuilder {

	@Autowired
	private BoardRepository boardRepo;

	private void titleBuild(BooleanBuilder builder, String title, QueryTypeString qts) {
		QBoard qboard = QBoard.board;
		
		if (qts == QueryTypeString.LIKE)
			builder.and(qboard.title.like("%" + title + "%"));
		else
			builder.and(qboard.title.eq(title));
	}

	private void contentBuild(BooleanBuilder builder, String content, QueryTypeString qts) {
		QBoard qboard = QBoard.board;
		
		if (qts == QueryTypeString.LIKE)
			builder.and(qboard.content.like("%" + content + "%"));
		else
			builder.and(qboard.content.eq(content));
	}	

	private void dateBuild(BooleanBuilder builder, Date date, Date to, QueryTypeNumeric qtn) {
		QBoard qboard = QBoard.board;

		switch(qtn) {
		case LT:	builder.and(qboard.date.lt(date));	break;
		case GT:	builder.and(qboard.date.gt(date));	break;
		case BW:	if (to != null)	builder.and(qboard.date.between(date, to));
					else			builder.and(qboard.date.eq(date));
					break;
		default:	builder.and(qboard.date.eq(date));	break;
		}
	}
	
	public List<Board> query(SearchConditionForBoard searchCond) {

		if (searchCond.isNullQuery())
			return boardRepo.findAll();
	
		BooleanBuilder builder = new BooleanBuilder();

		// title 조건
		QueryTypeString qts = searchCond.getTitleQueryType();
		String title = searchCond.getTitle();
		if(title != null)	titleBuild(builder, title, qts);

		// content 조건
		qts = searchCond.getContentQueryType();
		String content = searchCond.getContent();
		if(content != null)	contentBuild(builder, content, qts);

		// date 조건
		QueryTypeNumeric qtn = searchCond.getDateQueryType();
		Date date = searchCond.getDate();
		Date to = searchCond.getToDate();
		if (date != null)	dateBuild(builder, date, to, qtn);

		// Query & parsing
		Iterable<Board> iter = boardRepo.findAll(builder);
		List<Board> list = new ArrayList<>();
		iter.forEach(i->list.add(i));
		return list;
	}
}
