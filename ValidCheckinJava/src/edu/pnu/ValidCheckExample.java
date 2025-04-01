package edu.pnu;

import java.util.Set;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class User {
    
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @Email(message = "Invalid email format")
    private String email;
}

public class ValidCheckExample {

	public static void main(String[] args) {
		User user = new User("", 20, "abcd");
		
		try {
			ValidatorFactory factory = Validation.byDefaultProvider()
	                .configure()
	                .messageInterpolator(new ParameterMessageInterpolator())
	                .buildValidatorFactory();
			Validator validator = factory.getValidator();
			
			Set<ConstraintViolation<User>> violations = validator.validate(user);
			if (violations.isEmpty()) {
				System.out.println("user is valid!");
				return;
			}
	
			for(ConstraintViolation<User> violation : violations) {
				System.out.println(violation.getPropertyPath() + ":" + violation.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Done~~");
	}
	
}
