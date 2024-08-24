package edu.pnu.config.emul;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import edu.pnu.config.emul.dto.ReportDTO;

// 스케쥴에 따라 부트 서버에 데이터를 전송한다.
// Cron 설정은 구글에서 "스프링부트 Cron 주기설정"으로 검색하면 됨. 
//@Scheduled(cron = "1/5 * * * * *")	// 매분 1초부터 5초마다 실행  (1, 6, 11, 16 ...)
//@Scheduled(cron = "5,10,15,20,25,30 * * * * *")	// 매분 5,10,15,20,25,30초가 되면 실행
@Component
public class AppEmulator {

	private RestTemplate restTemplate = new RestTemplate();
	private static List<ReportDTO> data = new ArrayList<>();
	private static int idx = 0;

	// 임의의 보고 데이터 생성 ==> 실제 데이터를 이용해서 객체를 만들도록 수정하면 됨.
	public AppEmulator() {
		Random rd = new Random();
		for (int i = 0; i < 3 ; i++) {
			data.add(ReportDTO.builder()
					.intValue(rd.nextInt(10))
					.longValue(rd.nextLong(100L))
					.floatValue(rd.nextFloat(50.0f))
					.doubleValue(rd.nextDouble(100.0))
					.stringValue(String.valueOf(rd.nextLong(10000, 50000)))
					.dateValue(new Date())
					.build());
		}
	};
	
	@Scheduled(fixedRate = 10000)		// scheduler 시작하는 시간 기준으로 10초 간격으로 실행
	public void scheduledPostReportingwithSync() throws IOException {
		String ret = restTemplate.postForObject("http://localhost:8080/postEmul", data.get(idx), String.class);
		System.out.println("ret:" + idx + ":" + ret);
		if (data.size() <= ++idx) idx = 0;
	}
}
