package edu.pnu.config.emul.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 클라이언트가 BE에 주기적으로 보고하는 데이터 정의
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
	private int intValue;
	private long longValue;
	private float floatValue;
	private double doubleValue;
	private String stringValue;
	private Date dateValue;
}
