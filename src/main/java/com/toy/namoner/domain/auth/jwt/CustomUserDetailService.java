package com.toy.namoner.domain.auth.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toy.namoner.common.error.exceptions.EntityNotFoundException;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	/**
	 * userId로 조회
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not Found"));

		return new UserPrincipal(user);
	}
}
