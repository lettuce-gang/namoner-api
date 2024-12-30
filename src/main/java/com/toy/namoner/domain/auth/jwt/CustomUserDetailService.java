package com.toy.namoner.domain.auth.jwt;

import com.toy.namoner.common.exceptions.EntityNotFoundException;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
