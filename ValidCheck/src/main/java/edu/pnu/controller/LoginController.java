package edu.pnu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
class LoginForm{
	@NotBlank(message = "이름은 필수 항목입니다.")
	private String username;

	@NotBlank(message = "암호는 필수 항목입니다.")
	private String password;

	@Min(10)
	@Max(100)
	private int val;
}

@RestController
public class LoginController {

    // 로그인 인증을 실행한다.
	private Boolean loginProc(String username, String password) {
		// 데이터베이스에서 검증하는 과정은 이 예제에서는 생략한다.
        if (username.equals("user") && password.equals("abcd")) {
    		return true;
        }
   		return false;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult) {

		System.out.println("login - 1");
		
		// BindingResult 파라미터 없이 호출하면 스프링부트가 자동으로 브라우저에게 예외를 던져버림 (400) 
		// 파라미터를 추가하고 아래와 같이 리턴하면 에러메시지(LoginForm에서 정의된 message)를 브라우저에게 돌려줄 수 있다.
		if (bindingResult.hasErrors()) {
			System.out.println("login - 2.Error");
			
			// 유효성 검사 오류가 발생한 경우 필요한 메시지를 클라이언트에게 반환하거나 오류 처리를 수행한다.
			String errorMessage = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(errorMessage + " BindingResult");
		}

		System.out.println("login - 2.Normal");
		
		// 로그인을 위해 사용자명과 암호를 읽는다.
		String username = loginForm.getUsername();
		String password = loginForm.getPassword();

		// 로그인 인증을 실행하고 결과를 ResponseEntity에 담아서 되돌려 준다.
		if (loginProc(username, password)) {
			System.out.println("login - Successs");
			return ResponseEntity.ok("login - Successs");
			}
		System.out.println("login - Unauthorized");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login - Unauthorized");
	}
	@PostMapping("/login1")
	public ResponseEntity<?> login1(@Valid @RequestBody LoginForm loginForm) {

		System.out.println("login1 - 1");
		
		// 로그인을 위해 사용자명과 암호를 읽는다.
		String username = loginForm.getUsername();
		String password = loginForm.getPassword();

		// 로그인 인증을 실행하고 결과를 ResponseEntity에 담아서 되돌려 준다.
		if (loginProc(username, password)) {
			System.out.println("login - Successs");
			return ResponseEntity.ok("login - Successs");
		}
		System.out.println("login - Unauthorized");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login - Unauthorized");
    }
}
