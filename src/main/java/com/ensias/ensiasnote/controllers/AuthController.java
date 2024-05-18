package com.ensias.ensiasnote.controllers;

import com.ensias.ensiasnote.exception.TokenRefreshException;
import com.ensias.ensiasnote.models.RefreshToken;
import com.ensias.ensiasnote.models.User;
import com.ensias.ensiasnote.payload.request.SignInRequest;
import com.ensias.ensiasnote.payload.request.SignUpRequest;
import com.ensias.ensiasnote.payload.request.TokenRefreshRequest;
import com.ensias.ensiasnote.payload.response.JwtResponse;
import com.ensias.ensiasnote.payload.response.MessageResponse;
import com.ensias.ensiasnote.payload.response.TokenRefreshResponse;
import com.ensias.ensiasnote.repository.UserRepository;
import com.ensias.ensiasnote.security.jwt.JwtUtils;
import com.ensias.ensiasnote.security.services.RefreshTokenService;
import com.ensias.ensiasnote.security.services.UserDetailsImpl;
import com.ensias.ensiasnote.security.services.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for handling user authentication and registration.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;

  @PostMapping("/sign-in")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest loginRequest) {
    // Authenticate user
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(userDetails.getId());

    Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

    return ResponseEntity.ok(new JwtResponse(
            jwt,
            refreshToken.getToken(),
            userDetails.getId(),
            user.get().getNom(),
            user.get().getPrenom(),
            userDetails.getEmail(),
            roles
    ));
  }


  @PostMapping("/sign-up")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User();
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));
    user.setNom(signUpRequest.getNom());
    user.setPrenom(signUpRequest.getPrenom());
    user.setRole(signUpRequest.getRole().toUpperCase());

    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }


  @PostMapping("/sign-out")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've been signed out!"));
  }


  @PostMapping("/refresh-token")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<TokenRefreshResponse> refreshment(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUserId)
            .map(userId -> {
              UserDetails userDetails = userDetailsService.loadUserById(userId); // Assuming userService has a method to fetch user details by ID
              String token = jwtUtils.generateTokenFromUserId(userId, userDetails.getAuthorities());
              return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
            })
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                    "Refresh token is not valid or expired!"));

  }

}
