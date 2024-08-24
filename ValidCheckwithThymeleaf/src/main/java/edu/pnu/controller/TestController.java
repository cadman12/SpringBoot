package edu.pnu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
class LoginUser {
    @NotBlank(message = "이름은 필수 항목입니다")
    private String username;
    @NotBlank(message = "암호는 필수 항목입니다")
    private String password;
}

@Controller
public class TestController {

	@GetMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("greeting", "Hello Thymeleaf~~");
		return "hello";
	}	
	
	@GetMapping("/test")
	public String showForm() {
		System.out.println("Test is called");
		return "test";
	}
	
	@PostMapping("/test")
    public String processForm(@Valid LoginUser loginUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
        	List<ObjectError> errs = bindingResult.getAllErrors();
        	for (ObjectError err : errs) {
        		FieldError ferr = (FieldError)err;
        		String field = ferr.getField();
        		String msg = ferr.getDefaultMessage();
        		model.addAttribute(field, msg);
        	}
            return "test";
        }
        return "success";
    }	
}
