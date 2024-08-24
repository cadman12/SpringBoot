package edu.pnu.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class TestVal {
	private String username;
	private String str;
	private int val;
}

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
class TestDTO {
	private String name1;
	private String name2;
}

//
// PostMan에서 Body - form-data
//
// Key			Value						Content-Type
//-----------------------------------------------------------------------------------------------
// val	 - Text		{ "username":"user","str":"str","int":10}	application/json
// files - File		2 files selected				application/octet-stream
//

@RestController
public class FileController {

	@Value("${spring.servlet.multipart.location}")
	private String location;

	@PostMapping("/api/upload")
	public ResponseEntity<?> uploadFile(@RequestPart(value="jsonData", required=false) TestVal tv,
										@RequestPart(value="testDto", required=false) TestDTO td,
										@RequestPart("files") MultipartFile[] files) {
		System.out.println("tv:" + tv);
		System.out.println("td:" + td);
		
		StringBuffer sb = new StringBuffer();
		
		if (files != null && 0 < files.length) {
			try {
				for(MultipartFile file : files) {
					
					System.out.println("file:" + location + file.getOriginalFilename());
					
					// File의 Method를 이용한 방법
					file.transferTo(new File(location + file.getOriginalFilename()));
					
					// FileOutputStream 객체를 이용하는 방법
//					FileOutputStream fos = new FileOutputStream(location + file.getOriginalFilename());
//					fos.write(file.getBytes());
//					fos.close();					
				}
				sb.append(files.length + "개의 파일이 전송되었습니다.\n");
			} catch(Exception e) {
				return ResponseEntity.badRequest().body("Exception :" + e.getMessage());
			}
		}
		sb.append("JSON 데이터가 전송되었습니다.");
		return ResponseEntity.ok(sb.toString());
	}

	@GetMapping("/api/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

		// 다운로드 대상 파일
        String filePath = location + filename;
        File file = new File(filePath);

        if (!file.exists())	return ResponseEntity.notFound().build();

        // Resource 객체 생성
        Resource resource = new FileSystemResource(file);

        // 파일 전송 ==> 파일 이름만 제외하고 고정
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
	
	@GetMapping("/api/upload/{filename}")
	public ResponseEntity<?> sendFiles(@PathVariable String filename) {
		
		System.out.println("send:" + filename);
		
		try {
			// 복수개의 파일을 전송하고자 하는 경우에는 파일명을 리스트로 넘기면 된다.
			return sendFilesAndJSONData(Arrays.asList(filename), new TestDTO("name1", "name2"), new TestVal("username", "stringh", 1024));
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 복수개의 파일과 JSON 데이터 전송
	private ResponseEntity<?> sendFilesAndJSONData(List<String> fnames, TestDTO dto, TestVal val) throws IOException {
		
		// REST API 요청 객체 생성 (동기 방식)
		RestTemplate restTemplate = new RestTemplate();
		
        // 파일 전송 요청 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        
        // Content 타입 설정
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 전송 데이터를 담을 Body 객체 생성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // JSON data 준비 (testDto) 및 body에 저장
        if (dto != null) {
	        Map<String, Object> testDto = new HashMap<>();
	        testDto.put("name1", dto.getName1());
	        testDto.put("name2", dto.getName2());
	        body.add("testDto", testDto);
        }        
        // JSON data 준비 (jsonData) 및 body에 저장
        if (val != null) {
	        Map<String, Object> jsonData = new HashMap<>();
	        jsonData.put("username", val.getUsername());
	        jsonData.put("str", val.getStr());
	        jsonData.put("val", val.getVal());
	        body.add("jsonData", jsonData);
        }		
        // 전송 파일 준비
		for (String fname : fnames) {
	        // 파일을 바이트 배열로 로딩
	        byte[] fileBytes = Files.readAllBytes(Paths.get(location + fname));
	
	        // 로딩된 바이트로 리소스 객체 생성
	        ByteArrayResource fileResource = new ByteArrayResource(fileBytes) {
	            @Override
	            public String getFilename() {
	                return fname;
	            }
	        };
	        // 생성된 리소스 객체를 body에 저장 
	        body.add("files", fileResource);
		}

        // 요청할 파라미터 객체를 생성
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.exchange("http://localhost:8080/api/upload", HttpMethod.POST, requestEntity, String.class);
    }	
}
