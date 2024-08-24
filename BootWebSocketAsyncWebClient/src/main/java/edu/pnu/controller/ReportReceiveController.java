package edu.pnu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.config.AppWebSocketConfig;
import edu.pnu.config.emul.dto.ReportDTO;
import edu.pnu.domain.dto.PushDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ReportReceiveController {

	private final AppWebSocketConfig appWebSocketConfig;
	
	// Post Method를 이용해서 Body에 보고 데이터가 전달되어 옴.
	@PostMapping("/postEmul")
	public String postEmulator(@RequestBody ReportDTO reportDto) {
		log.info(reportDto.toString());
		
		try {
			// 동기화 테스트를 위해 일부러 응답을 늦춤
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 보고 데이터 처리 후 FE에 전달해야할 정보가 있는 경우 Push
		appWebSocketConfig.sendPushMessage(PushDTO.builder()
						.intValue(reportDto.getIntValue())
						.longValue(reportDto.getLongValue())
						.floatValue(reportDto.getFloatValue())
						.doubleValue(reportDto.getDoubleValue())
						.stringValue(reportDto.getStringValue())
						.dateValue(reportDto.getDateValue())
						.build());
		return "ok";
	}
}
