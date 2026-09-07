package com.rental.security;

import java.util.Locale;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rental.model.User;
import com.rental.repository.UserRepository;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public BookstoreUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String normalizedStringEmail = email.toLowerCase(Locale.ROOT);

		User user = userRepository.findByEmail(normalizedStringEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + normalizedStringEmail));

		return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword()).roles(user.getRole().name()).disabled(!user.isEnabled()).build();
	}

}
