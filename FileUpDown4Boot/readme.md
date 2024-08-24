# File Upload/Download를 위한 기본 설정

##application.properties

		- 전송 대상 파일의 최대 사이즈			: spring.servlet.multipart.max-file-size=10MB
		- 한번에 전송할 파일들의 전체 합 최대 사이즈	: spring.servlet.multipart.max-request-size=50MB
		- 전송되어온 파일을 저장할 폴더			: spring.servlet.multipart.location=d:/Temp/uploads/

			==> 만약 지정한 폴더가 없으면 자동으로 생성한다. 이때 폴더 depth가 깊게 작성되어 있어도 모두 생성해 준다.

---

# 파일 업로드 API 호출

##POST http://localhost:8080/api/upload

		==> PostMan에서 테스트
	
	- public ResponseEntity<?> uploadFile(@RequestPart(value="jsonData", required=false) TestVal tv,
										@RequestPart(value="testDto", required=false) TestDTO td,
										@RequestPart("files") MultipartFile[] files)

		. json 파라미터는 required가 false로 설정되어 있기 때문에 api를 호출할 때 입력되지 않으면 null로 설정된다.
		. required는 기본값이 true이므로 설정을 하지 않고 호출할 때 입력되지 않으면 "400 Bad Request" 예외 오류가 발생한다.
	
		. MultipartFile은 @RequestParam으로 변경해도 문제없이 실행되지만,
		. JSON 데이터는 @RequeatParam으로 변경하면 500 에러가 발생하므로 @RequestPart로만 사용해야 함.
	
	- Parameter
		. Body - form-data
		
		. Upload file 설정
			. key : "files"
				==> key 오른쪽 끝에 [Text/File] 선택에서 [File] 선택
				. value : [select file] ==> 업로드 대상 파일을 1개 이상 선택
				. Content-Type : Auto 또는 application/octet-stream로 설정
			. 같은 key명으로 여러 개를 입력 할 수 있다.

		. JSON Parameter 설정
			. key : "jsonData"
				==> key 오른쪽 끝에 [Text/File] 선택에서 [Text]을 선택
				. value :
					{
						"namename":"user",
						"str":"abcd",
						"val":"100"
					}
				. Content-Type : application/json 로 설정
			. key : "testDto"
				==> key 오른쪽 끝에 [Text/File] 선택에서 [Text]을 선택
				. value :
					{
						"name1":"user",
						"name2":"abcd"
					}
				. Content-Type : application/json 로 설정

	- File Upload
	
		. MultipartFile의 Method인 transferTo를 사용하거나
		. FileOutputStream을 이용해서 직접 쓰거나 할 수 있다.

---

# 파일 다운로드 API 호출

	- GET http://localhost:8080/api/download/test1.txt
		==> 브라우저에서 호출

		
---

# 파일 전송 API 호출

	- GET http://localhost:8080/api/upload/test1.txt
		==> 브라우저에서 호출
		
	- 부트 서버에서 다른 서버에게 파일 및 JSON 데이터를 전송하고자 할 때 호출
	
	- 테스트를 위해 자기 자신을 호출하는 것으로 테스트

		