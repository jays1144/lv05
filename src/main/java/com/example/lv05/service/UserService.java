package com.example.lv05.service;

import com.example.lv05.dto.SignupRequestDto;
import com.example.lv05.entity.User;
import com.example.lv05.entity.UserRoleEnum;
import com.example.lv05.jwt.JwtUtil;
import com.example.lv05.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public User signup(SignupRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> isValueUsername = userRepository.findByUsername(username);
        if(isValueUsername.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()){
            if(requestDto.getAdminToken().equals(ADMIN_TOKEN)){
                role = UserRoleEnum.ADMIN;
            }
        }

        User user = new User(username,password,role);
        return userRepository.save(user);
    }
}
