package edu.pnu.controller;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class MyLocaleData {
	private String country;
	private String name;
	private String number;
	private String currency;
	private String datetime;
	private String date;
	private String time;
}

@RestController
public class TestController {

	private MyLocaleData getMyLocaleDataObject(Locale locale, int number, Date date, Boolean df) {
		
		// <<Date>>
		// FULL   : "2024년 10월 21일 월요일"
		// LONG   : "2024년 10월 21일"
		// MEDIUM : "2024. 10. 21."
		// SHORT  : "24. 10. 21."
		// <<Time>>
		// FULL   : "오후 1시 44분 52초 대한민국 표준시"
		// LONG   : "오후 1시 44분 15초 KST"
		// MEDIUM : "오후 1:45:18"
		// SHORT  : "오후 1:45"
		int style = DateFormat.FULL;
		
		return MyLocaleData.builder()
							.country((df?"기본 로케일 - ":"") + locale.getDisplayCountry())
							.name(locale.getDisplayName())
							.number(NumberFormat.getInstance(locale).format(number))
							.currency(NumberFormat.getCurrencyInstance(locale).format(number))
							.datetime(DateFormat.getDateTimeInstance(style, style, locale).format(date))
							.date(DateFormat.getDateInstance(style, locale).format(date))
							.time(DateFormat.getTimeInstance(style, locale).format(date))
							.build();
	}
	
	// Locale을 이용해서 숫자, 금액, 날짜, 시간을 표시하는 예제
	@GetMapping("/testlocale")
	public ResponseEntity<?> testlocale(Integer number) {

		if (number == null) number = 1000000;
		
		List<MyLocaleData> list = new ArrayList<>();

		Date date = new Date();
		
		Locale locale = LocaleContextHolder.getLocale();
		list.add(getMyLocaleDataObject(locale, number, date, true));
		
		Locale locales[] = { Locale.KOREA, Locale.US, Locale.FRANCE, Locale.JAPAN, Locale.CHINA };

		for (int i = 0 ; i < locales.length ; i++) {
			list.add(getMyLocaleDataObject(locales[i], number, date, false));
		}
		
		return ResponseEntity.ok(list);
	}
}
