package com.hoit.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hoit.login.mapper.LoginMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("USER_ID", username);
        
        Map<String, Object> userMap = loginMapper.getUserById(paramMap);
        if (userMap == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        String userId = (String) userMap.get("user_id");
        String userPw = (String) userMap.get("user_pw");
        return User.builder()
                .username(userId)
                .password(userPw)
                .roles("USER")
                .build();
    }
}
