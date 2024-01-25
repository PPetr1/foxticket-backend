package org.gfa.avusfoxticketbackend.config.services.impl;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import org.gfa.avusfoxticketbackend.config.models.RefreshToken;
import org.gfa.avusfoxticketbackend.config.repositories.RefreshTokenRepository;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.config.services.RefreshTokenService;
import org.gfa.avusfoxticketbackend.dtos.authdtos.AuthenticationResponse;
import org.gfa.avusfoxticketbackend.dtos.authdtos.RefreshTokenRequest;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;

  private final UserRepository userRepository;

  private static final Dotenv dotenv = Dotenv.configure().load();

  private static final String REFRESH_EXPIRATION_TIME = dotenv.get("REFRESH_EXPIRATION_TIME");

  private final JwtService jwtService;

  @Autowired
  public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtService jwtService) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
    this.jwtService = jwtService;
  }

  @Override public RefreshToken createRefreshToken(String email) {
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      RefreshToken refreshToken = new RefreshToken
              (UUID.randomUUID().toString(),
              new Date(System.currentTimeMillis() + Integer.parseInt(REFRESH_EXPIRATION_TIME)),
              user);
      refreshTokenRepository.save(refreshToken);
      return refreshToken;
    } else {
      throw new ApiRequestException("/api","Unknown Error");
    }
  }

  @Override public Optional<RefreshToken> findByToken(String token){
    return refreshTokenRepository.findByToken(token);
  }

  @Override public Optional<RefreshToken> findByUser(User user) {
    return refreshTokenRepository.findByUser(user);
  }

  @Override public RefreshToken verifyExpiration(RefreshToken token){
    if(token.getExpiryDate().before(new Date())){
      refreshTokenRepository.delete(token);
      refreshTokenRepository.flush();
      throw new ApiRequestException("/api/refresh-token",token.getToken() + " Refresh token is expired. Please make a new login..!");
    }
    return token;
  }

  @Override public void saveRefreshToken(RefreshToken refreshToken) {
    refreshTokenRepository.save(refreshToken);
  }

  @Override public void deleteRefreshToken(RefreshToken refreshToken) {
    refreshTokenRepository.delete(refreshToken);
  }

  @Override public AuthenticationResponse generateNewToken(RefreshTokenRequest refreshTokenRequest) {
    return findByToken(refreshTokenRequest.getToken())
            .map(this::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(User -> {
              String accessToken = jwtService.generateToken(User);
              return new AuthenticationResponse("ok",refreshTokenRequest.getToken(),accessToken);
            }).orElseThrow(() -> new ApiRequestException("/api/refresh-token","Refresh Token does not exist!"));
  }

  @Override public int getExpirationTime() {
    return Integer.parseInt(REFRESH_EXPIRATION_TIME);
  }
}
