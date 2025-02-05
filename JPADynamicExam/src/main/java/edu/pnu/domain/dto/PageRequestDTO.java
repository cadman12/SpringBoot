package edu.pnu.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageRequestDTO {
	private Integer pageNumber = 1;
	private Integer pageSize = 5;
	private Boolean asc = true;
	private String field;
}
