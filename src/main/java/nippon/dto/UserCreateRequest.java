package nippon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import nippon.model.Role;

public record UserCreateRequest(
		@NotBlank
		@Size(max = 100)
		String firstName,
		
		@NotBlank
		@Size(max = 100)
		String lastName,
		
		@NotBlank
		@Size(max = 150)
		String email,
		
		@NotBlank
		@Size(max = 150, min = 6)
		String password,
		
		@NotBlank
		@Size(max = 150, min = 8)
		String phone,
		
		@NotNull
		Role role
		) {

}
