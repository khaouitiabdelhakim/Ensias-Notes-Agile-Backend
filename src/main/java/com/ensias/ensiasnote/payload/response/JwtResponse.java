package com.ensias.ensiasnote.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Response payload for JWT authentication.
 */
@Getter
@Setter
public class JwtResponse {
    private String token;           // JWT access token
    private String type = "Bearer"; // Token type
    private String refreshToken;    // Refresh token
    private Long id;                // User ID
    private String nom;        // Nom
    private String prenom;   //Prenom
    private String email;           // Email
    private List<String> roles;     // User roles

    public JwtResponse(String accessToken, String refreshToken, Long id, String prenom,String nom, String email, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.roles = roles;
    }
}
