package edu.pnu.controller;

import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class TestController {

	@GetMapping("/test")
	public ResponseEntity<?> webClientTest(Integer type) {
		
//		일반 인증키(Encoding)	
//		String key1 = "O%2B0crpjQYDzudPhuKAAWy3QHNfuOMEbaTDW96QH0bqFp83bkX0q8T97lf7Cxo7iqX3Kl%2B6CdTjiA0kZPSJLQww%3D%3D";
//		일반 인증키(Decoding)	
		String key2 = "O+0crpjQYDzudPhuKAAWy3QHNfuOMEbaTDW96QH0bqFp83bkX0q8T97lf7Cxo7iqX3Kl+6CdTjiA0kZPSJLQww==";

		WebClient webClient = WebClient.create();
		
		try {
			URI uri = new URI("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
					   + "?serviceKey=" + URLEncoder.encode(key2, "UTF-8")
					   + "&pageNo=1"
					   + "&numOfRows=1000"
					   + "&dataType=JSON"
					   + "&base_date="+ LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
					   + "&base_time=1700"
					   + "&nx=60"
					   + "&ny=127"); 

			System.out.println("URI:" + uri.toString());
			
			String rets = webClient.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
			return ResponseEntity.ok(rets);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok("Error");
	}	
}
