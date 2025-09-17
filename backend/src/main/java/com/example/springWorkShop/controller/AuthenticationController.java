package com.example.springWorkShop.controller;

import com.example.springWorkShop.dao.auth.request.AuthenticationRequest;
import com.example.springWorkShop.dao.auth.request.RegisterRequest;
import com.example.springWorkShop.dao.auth.request.UpdateAccountRequest;
import com.example.springWorkShop.dao.auth.response.AuthenticationResponse;
import com.example.springWorkShop.dao.auth.response.UpdateAccountResponse;
import com.example.springWorkShop.dao.auth.response.UserResponse;
import com.example.springWorkShop.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AuthenticationResponse> adminRegister(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.registerAsAdmin(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<UserResponse>> findAll(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                       Authentication authentication) {
        String loggedInUsername = authentication.getName();
        List<UserResponse> userList = authenticationService.findAll(loggedInUsername, page, size);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/find/count")
    public ResponseEntity<Long> findcount(){
        Long count = authenticationService.findCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/find/user/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable String username) {
        UserResponse user = authenticationService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        UserResponse user = authenticationService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate( @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or #updateRequest.id == authentication.principal.id")
    public ResponseEntity<UpdateAccountResponse> updateAccount(
            @RequestBody UpdateAccountRequest updateRequest) {
        UpdateAccountResponse response = authenticationService.updateAccount( updateRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable Long id) {
        authenticationService.deleteAccount(id);
        return ResponseEntity.ok(Map.of("message", "Account deleted successfully"));
    }
}
