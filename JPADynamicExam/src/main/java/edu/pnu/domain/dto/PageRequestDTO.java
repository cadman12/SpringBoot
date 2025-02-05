package edu.pnu.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageRequestDTO {
	private Integer pageNumber;
	private Integer pageSize;
	private Boolean asc;
	private String field;
}
