package com.ensias.ensiasnote.security.services;


import com.ensias.ensiasnote.exception.TokenRefreshException;
import com.ensias.ensiasnote.models.RefreshToken;
import com.ensias.ensiasnote.models.User;
import com.ensias.ensiasnote.repository.RefreshTokenRepository;
import com.ensias.ensiasnote.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${sportnet.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createOrUpdateRefreshToken(Long userId) {
        // Check if a user with the given userId exists
        Optional<User> optionalUser = userRepository.findById(userId);

        User user = optionalUser.get();

        // Check if the user already has a refresh token
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserId(user.getId());

        RefreshToken refreshToken;

        if (optionalRefreshToken.isPresent()) {
            // If the user has an existing refresh token, update it
            refreshToken = optionalRefreshToken.get();
        } else {
            // If the user does not have an existing refresh token, create a new one
            refreshToken = new RefreshToken();
            refreshToken.setUserId(user.getId());
        }

        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;

    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }
}
