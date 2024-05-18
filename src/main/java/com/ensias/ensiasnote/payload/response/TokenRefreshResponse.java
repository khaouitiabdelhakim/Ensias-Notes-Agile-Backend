package com.ensias.ensiasnote.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response payload for token refresh.
 */
@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshResponse {
    private String accessToken;     // Access token
    private String refreshToken;    // Refresh token
    private String tokenType = "Bearer"; // Token type

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
