package com.tecno_comfenalco.pa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tecno_comfenalco.pa.security.domain.UserEntity;
import com.tecno_comfenalco.pa.security.dto.UserDto;
import com.tecno_comfenalco.pa.security.dto.requests.EditUserRequestDto;
import com.tecno_comfenalco.pa.security.dto.requests.LoginRequestDto;
import com.tecno_comfenalco.pa.security.dto.requests.RegisterUserRequestDto;
import com.tecno_comfenalco.pa.security.dto.responses.DisableUserResponseDto;
import com.tecno_comfenalco.pa.security.dto.responses.ListUserResponseDto;
import com.tecno_comfenalco.pa.security.dto.responses.LoginResponseDto;
import com.tecno_comfenalco.pa.security.dto.responses.RegisterUserResponseDto;
import com.tecno_comfenalco.pa.security.dto.responses.UserResponseDto;
import com.tecno_comfenalco.pa.security.repository.IUserRepository;
import com.tecno_comfenalco.pa.shared.utils.jwt.JwtUtils;
import com.tecno_comfenalco.pa.shared.utils.result.Result;

@Service
public class AuthenticationService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.expiration-ms}")
    private Long expirationMs;

    public Result<RegisterUserResponseDto, Exception> registerUser(RegisterUserRequestDto request) {
        boolean userExists = userRepository.existsByUsername(request.username());
        if (userExists) {
            return Result.error(
                    new Exception("Username already taken"));
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(request.username());
        newUser.setPassword(new BCryptPasswordEncoder().encode(request.password()));
        newUser.setRoles(request.roles());
        newUser.setEnabled(request.enabled());
        userRepository.save(newUser);

        return Result.ok(new RegisterUserResponseDto("User registered successfully", newUser.getId()));
    }

    public Result<LoginResponseDto, Exception> loginUser(LoginRequestDto request) {

        Long expirationTime = request.rememberMe() ? 7 * 24 * 60 * 60 * 1000L : expirationMs;

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String token = jwtUtils.encode(authentication.getName(), expirationTime);

        return Result.ok(new LoginResponseDto("Usuario exitosamente autenticado", token));
    }

    public Result<DisableUserResponseDto, Exception> disableUser(Long userId) {
        var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Result.error(new Exception("User not found"));
        }

        UserEntity user = userOpt.get();
        user.setEnabled(false);
        userRepository.save(user);

        return Result.ok(new DisableUserResponseDto("User disabled successfully", userId));
    }

    public Result<UserResponseDto, Exception> getUserDetails(String username) {
        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return Result.error(new Exception("User not found"));
        }

        UserEntity user = userOpt.get();
        UserDto userDto = new UserDto(user.getUsername(), user.getRoles(), user.isEnabled());

        UserResponseDto userResponse = new UserResponseDto(userDto, "User obtained successfully");

        return Result.ok(userResponse);
    }

    public Result<ListUserResponseDto, Exception> listUsers() {
        var users = userRepository.findAll().stream().map(user -> {
            UserDto userDto = new UserDto(user.getUsername(), user.getRoles(), user.isEnabled());
            return new UserResponseDto(userDto, "User obtained successfully");
        }).toArray(UserResponseDto[]::new);

        return Result.ok(new ListUserResponseDto("Users retrieved successfully", users));
    }

    public Result<UserResponseDto, Exception> editUser(Long userId, EditUserRequestDto request) {
        var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Result.error(new Exception("User not found"));
        }

        UserEntity user = userOpt.get();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setRoles(request.roles());
        user.setEnabled(request.enabled());
        userRepository.save(user);

        UserDto userDto = new UserDto(user.getUsername(), user.getRoles(), user.isEnabled());
        UserResponseDto userResponse = new UserResponseDto(userDto, "User edited successfully");

        return Result.ok(userResponse);
    }

}
