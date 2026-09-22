package nippon.dto;

import nippon.model.Role;

public record UserResponse (
		Long id,
		String fistName,
		String lastName,
		String email,
		String password,
		String phone,
		Role role,
		boolean enabled
		)
	{
}
