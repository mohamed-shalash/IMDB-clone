package com.example.springWorkShop.service;

import com.example.springWorkShop.dao.auth.request.AuthenticationRequest;
import com.example.springWorkShop.dao.auth.request.RegisterRequest;
import com.example.springWorkShop.dao.auth.request.UpdateAccountRequest;
import com.example.springWorkShop.dao.auth.response.AuthenticationResponse;
import com.example.springWorkShop.dao.auth.response.UpdateAccountResponse;
import com.example.springWorkShop.dao.auth.response.UserResponse;
import com.example.springWorkShop.dto.User;
import com.example.springWorkShop.handeler.error.InvalidCredentialsException;
import com.example.springWorkShop.handeler.error.UserNotFoundException;
import com.example.springWorkShop.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final JwtService jwtService;
    //private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .userName(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAsAdmin(RegisterRequest request) {
        var user = User.builder()
                .userName(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_ADMIN")
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var user = userRepo.findByUserName(request.getUsername())
                .orElseThrow();

        log.info("User authenticated: {}", user.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public List<UserResponse> findAll(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepo.findAll(pageable)
                .stream()
                //.filter(user -> !user.getUsername().equals(username))
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getRole()))
                .collect(Collectors.toList());
    }

    public UserResponse findByUsername(String username) {
        var user = userRepo.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserResponse(user.getId(), user.getUsername(), user.getRole());
    }

    public UserResponse findById(Long id) {
        var user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserResponse(user.getId(), user.getUsername(), user.getRole());
    }



    @Transactional
    public UpdateAccountResponse updateAccount( UpdateAccountRequest updateRequest) {
        var user = userRepo.findById(updateRequest.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + updateRequest.getId()));

        if (updateRequest.getUserName() != null && !updateRequest.getUserName().isBlank()) {
            user.setUserName(updateRequest.getUserName());
        }
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        userRepo.save(user);

        String newToken = jwtService.generateToken(user);

        return new UpdateAccountResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                newToken
        );
    }

    public void deleteAccount(Long id) {
        var user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepo.delete(user);
    }


    public Long findCount() {
        return userRepo.count();
    }
}
