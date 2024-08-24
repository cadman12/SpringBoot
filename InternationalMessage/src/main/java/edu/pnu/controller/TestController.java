package edu.pnu.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class MyFormat {
	private String country;
	private String name;
	private String val1;
	private String val2;
}

@RestController
public class TestController {

	@Autowired
	MessageSource messageSource;	// messages.properties
	
	// messages.properties에서 읽어서 출력하는 예제
	@GetMapping("/test1")
	public String test1(String name) {
		Locale locale = LocaleContextHolder.getLocale();
		System.out.println(locale.getDisplayCountry());
		System.out.println(locale.getDisplayName());
		
		return messageSource.getMessage("greeting.message", new Object[] {name}, locale);
	}

	@GetMapping("/test2")
	public String test2(String name) {
		return messageSource.getMessage("greeting.message", new Object[] {name}, Locale.US);
	}
	
	// Locale을 이용해서 숫자와 금액을 표시하는 예제
	@GetMapping("/test3")
	public List<MyFormat> test3(Integer money) {
		
		List<MyFormat> list = new ArrayList<>();
		{
			Locale locale = LocaleContextHolder.getLocale();
			list.add(MyFormat.builder()
					.country(locale.getDisplayCountry())
					.name(locale.getDisplayName())
					.val1(NumberFormat.getInstance(locale).format(money))
					.val2(NumberFormat.getCurrencyInstance(locale).format(money))
					.build());
		}
		
		Locale locales[] = { Locale.JAPAN, Locale.US, Locale.FRANCE };

		for (int i = 0 ; i < locales.length ; i++) {
			list.add(MyFormat.builder()
					.country(locales[i].getDisplayCountry())
					.name(locales[i].getDisplayName())
					.val1(NumberFormat.getInstance(locales[i]).format(money))
					.val2(NumberFormat.getCurrencyInstance(locales[i]).format(money))
					.build());
		}
		
		return list;
	}
}
