package edu.pnu.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//BE가 연결된 FE에게 전달하는 데이터 클래스 정의
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushDTO {
	private int intValue;
	private long longValue;
	private float floatValue;
	private double doubleValue;
	private String stringValue;
	private Date dateValue;
}
