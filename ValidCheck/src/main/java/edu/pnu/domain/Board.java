package edu.pnu.domain;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

	@NotBlank(message = "작성자는 필수 항목입니다.")
	private String writer;
	
	@NotBlank(message = "타이틀은 필수 항목입니다.")
	private String title;

	@Builder.Default
	private String content = "defaultContent";

	@Builder.Default
	private Date date = new Date();
}
