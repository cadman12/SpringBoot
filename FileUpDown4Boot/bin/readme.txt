# PostMan에서 테스트하는 방법

- 파일 한 개 업로드 
	. POST http://localhost:8080/upload
	. Parameter
		. Body - form-data
		. key : file ==> key 오른쪽 끝에 [Text/File] 선택에서 [File]을 선택
		. value : [select file] ==> 업로드 대상 파일 선택
	. [Send]
		. application.properties
			. spring.servlet.multipart.location에 지정한 폴더에 선택한 파일이 저장되었는지 확인
			. 만약 지정한 폴더가 없으면 자동으로 생성한다.
			
- 파일 두 개 이상 업로드 
	. POST http://localhost:8080/uploads
	. Parameter
		. Body - form-data
		. key : files ==> key 오른쪽 끝에 [Text/File] 선택에서 [File]을 선택
		. value : [select file] ==> 업로드 대상 파일 선택
		. key : files ==> key 오른쪽 끝에 [Text/File] 선택에서 [File]을 선택
		. value : [select file] ==> 업로드 대상 파일 선택
	. [Send]
		. application.properties
			. spring.servlet.multipart.location에 지정한 폴더에 선택한 파일이 저장되었는지 확인
			. 만약 지정한 폴더가 없으면 자동으로 생성한다.

- JSON 필드 설정 테스트
	. Parameter
		. Body - form-data
		. key : file ==> key 오른쪽 끝에 [Text/File] 선택에서 [Text]을 선택
		. value : {"name1":"user","name2":"abcd"}
		. Content-Type : application/json 로 설정
