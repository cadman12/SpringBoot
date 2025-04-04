# di : web, lombok, validation

##@Valid
---
- @Valid없이 파라미터를 받는 경우


- public ResponseEntity<?> login(@RequestBody LoginForm loginForm, BindingResult bindingResult)

	. @Valid 없이 파라미터를 받는 경우에는 BindingResult가 있든 없든 상관이 없다. 아무런 error가 입력되지 않은채로 넘어오기 때문임.
	. LginForm 클래스의 필드 변수를 사용할 때는 null인지 확인을 하고 사용해야 함.
	. PostMan으로 호출할 때 @RequestBody로 매개변수를 받기 때문에 JSON으로 전송해야 함.
		1.만약 매개 변수를 전달하지 않으면
			- 매개변수 loginForm이 null이 되고, 핸들러 호출전에 예외가 발생해서 "400 Bad Requst"가 발생함.
		2.만약 JSON key를 다른 이름으로 전송하는 경우
			- 즉, "username"을 "username1"로 변경해서 호출하는 경우
			- "username"에 대한 null 체크를 하지 않고 사용하면 NullPointExcetpion 예외가 발생
			- 결국 "500 Internal Server Error"가 발생하게 된다.
		3. 예외처리를 위해 @RestControllerAdvice를 등록해서 별도로 처리하는 것이 좋음.
---
- @Valid를 설정하고 BindingResult 파라미터가 없는 경우


- public ResponseEntity<?> login1(@Valid @RequestBody LoginForm loginForm)

	. PostMan으로 호출할 때 @RequestBody로 매개변수를 받기 때문에 JSON으로 전송해야 함.
		1.만약 매개 변수를 전달하지 않으면
			- 매개변수 loginForm이 null이 되고, @Valid여부와 상관없이 핸들러 호출전에 예외가 발생해서 "400 Bad Requst"가 발생함.
		2.만약 JSON key를 다른 이름으로 전송하는 경우
			- 즉, "username"을 "username1"로 변경해서 호출하는 경우
			- @Valid이 설정된 클래스(LoginForm)의 제약사항을 점검하고 점검 결과 제약 사항에 걸리면 "400 Bad Requst"가 발생함.
		3. 예외처리를 위해 @RestControllerAdvice를 등록해서 별도로 처리하는 것이 좋음.
---
- @Valid를 설정하고 BindingResult 파라미터를 받는 경우


- public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult)

	. PostMan으로 호출할 때 @RequestBody로 매개변수를 받기 때문에 JSON으로 전송해야 함.
		1.만약 매개 변수를 전달하지 않으면
			- 매개변수 loginForm이 null이 되고, @Valid여부와 상관없이 핸들러 호출전에 예외가 발생해서 "400 Bad Requst"가 발생함.
		2.만약 JSON key를 다른 이름으로 전송하는 경우
			- 즉, "username"을 "username1"로 변경해서 호출하는 경우
			- @Valid이 설정된 클래스(LoginForm)의 제약사항을 점검하고 점검 결과가 bindingResult에 설정되어서 넘어온다.
			- 이 경우에는 @NotBlank(message = "이름은 필수 항목입니다.") 설정 때문에 bindingResult의 error에 "이름은 필수 항목입니다."가 설정되어 넘어온다.

---
##BindingResult bindingResult

- 예외 내용을 객체로 만들어서 호출 메소드의 파라미터로 보내준다.
- 에러가 있을 경우 객체의 필드에 설정된 메시지를 보내준다.
- 하나 이상 제약 사항을 어겼다면 모든 메시지가 설정된다.
- 이 파라미터도 설정하고 동시에 위의 예외처리도 설정되어 있을 경우 이 파라미터가 우선권을 가진다.
	
---
##예외처리

- @ExceptionHandler(Exception.class)

	. e.getMessage()를 호출하면 예외 관련 모든 정보를 되돌려 준다.
	
- @ExceptionHandler(MethodArgumentNotValidException.class)
	
	. e.getDefaultMessage()를 호출하면 필드에 설정해둔 메시지를 되돌려 준다.
			
---
##관련 Annotations

- @Size(min=8, max=12)						=> 입력값 길이 지정
- @NotNull(message = "작성자는 필수 항목입니다.")	=> null 허용하지 않음. "", " " 는 허용.
- @NotEmpty(message = "작성자는 필수 항목입니다.")	=> null, "" 허용하지 않음. " " 는 허용.
- @NotBlank(message = "작성자는 필수 항목입니다.")	=> null, "", " " 모두 허용하지 않음.
- @Email(message = "잘못된 이메일입니다.")		=> Email 형식이 아니면 예외
- @Min(10)									=> 10 이상 허용
- @Max(100)									=> 100 이하 허용
