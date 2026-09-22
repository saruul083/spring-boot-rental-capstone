package nippon.service;

import java.util.List;
import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import nippon.dto.RegisterRequest;
import nippon.dto.UserCreateRequest;
import nippon.dto.UserResponse;
import nippon.exception.DuplicateResourceException;
import nippon.model.Role;
import nippon.model.User;
import nippon.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void registerCustomer(RegisterRequest request) {
		String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
		
		if(userRepository.existsByEmail(email)) {
			throw new DuplicateResourceException("Email already exists: " + email);
		}
		
		User user = new User();
		
		user.setFirstName(request.getFirstName().trim());
		
		user.setLastName(request.getLastName().trim());
		
		user.setEmail(request.getEmail());
		
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		user.setPhone(request.getPhone());
		
		user.setRole(Role.CUSTOMER);
		
		user.setEnabled(true);
		
		userRepository.save(user);
	}
	
	public UserResponse createUser(UserCreateRequest request) {
		String email = request.email().trim().toLowerCase(Locale.ROOT);
		
		if(userRepository.existsByEmail(email)) {
			throw new DuplicateResourceException("Email already exists: " + email);
		}
		
		User user = new User();
		
		user.setFirstName(request.firstName().trim());
		
		user.setLastName(request.lastName().trim());
		
		user.setEmail(request.email());
		
		user.setPassword(passwordEncoder.encode(request.password()));
		
		user.setPhone(request.phone().trim());
		
		user.setRole(request.role());
		
		user.setEnabled(true);
		
		User savedUser = userRepository.save(user);
		
		return toResponse(savedUser);
	}
	
	public List<UserResponse> findAllUsers() {
		return userRepository.findAll().stream().map(this::toResponse).toList();
	}
	
	public UserResponse toResponse(User user) {
		return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getPassword(), user.getPhone(), user.getRole(), user.isEnabled());
	}
}
