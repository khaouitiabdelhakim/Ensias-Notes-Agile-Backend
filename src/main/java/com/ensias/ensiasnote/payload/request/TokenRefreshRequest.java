package com.ensias.ensiasnote.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request payload for token refresh.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
}
