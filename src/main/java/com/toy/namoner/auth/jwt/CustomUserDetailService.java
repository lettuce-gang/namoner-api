package com.toy.namoner.auth.jwt;

import com.toy.namoner.common.exceptions.CommonBadRequestException;
import com.toy.namoner.common.exceptions.CommonResponseCode;
import com.toy.namoner.user.domain.User;
import com.toy.namoner.user.repository.UserRepository;
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

        User user = userRepository.findById(userId).orElseThrow(() -> new CommonBadRequestException(CommonResponseCode.ENTITY_NOT_FOUND, "User Not Found"));

        return new UserPrincipal(user);
    }
}
