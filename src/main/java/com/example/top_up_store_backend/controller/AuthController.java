package com.example.top_up_store_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.top_up_store_backend.dto.LoginPayload;
import com.example.top_up_store_backend.dto.RegisterPayload;
import com.example.top_up_store_backend.enums.Role;
import com.example.top_up_store_backend.models.AppUser;
import com.example.top_up_store_backend.repository.AppUserRepository;
import com.example.top_up_store_backend.utils.ApiResponse;
import com.example.top_up_store_backend.utils.JWTService;
import com.example.top_up_store_backend.utils.ResponseFormat;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private JWTService jwtService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterPayload payload) {
      try {          
        AppUser appUser = new AppUser();
        if (appUserRepository.checkExistingEmailAndUsername(payload.username(), payload.email())) {
          return ResponseFormat.generateErrorResponse("User with that username or email already exist", null, HttpStatus.CONFLICT);
        }
        appUser.setPassword(hashPassword(payload.password()));
        appUser.setUsername(payload.username());
        appUser.setEmail(payload.email());
        appUser.setRole(payload.role());
        AppUser userRegistered = appUserRepository.save(appUser);        
        Map<String, Object> response = new HashMap<>();    
        response.put("username", userRegistered.getUsername());            
        return ResponseFormat.generateSuccessResponse("Register successfull, please continue login", response, HttpStatus.CREATED);
      } catch (Exception e) {        
        return ResponseFormat.generateErrorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginPayload loginPayload) {
      try {   
        Map<String,Object> userData = new HashMap<>();
        Map<String,Object> response = new HashMap<>();
        String username = loginPayload.getUsername();
        String password = loginPayload.getPassword();
        if (username == null || password == null) {
          return ResponseFormat.generateErrorResponse("Invalid request", password, HttpStatus.BAD_REQUEST);
        }
        AppUser user = appUserRepository.findByUsername(username).orElse(null);
        if (user == null) {            
          return ResponseFormat.generateErrorResponse("Username not found", null , HttpStatus.UNAUTHORIZED);
        }          
        if (!checkPassword(password, user.getPassword())) {
          return ResponseFormat.generateErrorResponse("Incorrect password", null, HttpStatus.UNAUTHORIZED);
        }       
        if (user.getRole() != Role.ADMIN) {
          return ResponseFormat.generateErrorResponse("Unauthorized user for this page", null, HttpStatus.UNAUTHORIZED);
        }   
        userData.put("id", user.getId());
        userData.put("email", user.getEmail());          
        userData.put("role", user.getRole());                 
        String jwtToken = jwtService.generateToken(userData, user);   
        response.put("token", jwtToken);       
        return ResponseFormat.generateSuccessResponse("Login successfull", response , HttpStatus.OK);
      } catch (Exception e) {
        return ResponseFormat.generateErrorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  public String hashPassword(String password) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(password);
  }

  public Boolean checkPassword (String password, String dbPassword) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(password, dbPassword);
  }
}
