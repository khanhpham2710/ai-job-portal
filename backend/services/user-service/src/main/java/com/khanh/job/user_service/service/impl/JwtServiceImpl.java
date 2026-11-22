package com.khanh.job.user_service.service.impl;

import com.khanh.job.common.CommonAppProperties;
import com.khanh.job.security.JwtClaims;
import com.khanh.job.user_service.config.UserAppProperties;
import com.khanh.job.user_service.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  private final CommonAppProperties.Token access;
  private final CommonAppProperties.Token refresh;
  private final SecretKey accessKey;
  private final SecretKey refreshKey;

  public JwtServiceImpl(UserAppProperties props) {
    this.access = props.getSecurity().getJwt().getAccess();
    this.refresh = props.getRefresh().getToken();
    this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(access.getSecret()));
    this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refresh.getSecret()));
  }

  @Override
  public String generateAccessToken(UUID userId, UserDetails userDetails) {
    String email = userDetails.getUsername();

    List<String> roles =
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return base(userId, access, accessKey)
        .claim(JwtClaims.EMAIL, email)
        .claim(JwtClaims.ROLES, roles)
        .compact();
  }

  @Override
  public String generateRefreshToken(UUID userId) {
    return base(userId, refresh, refreshKey)
        .claim(JwtClaims.TYPE, JwtClaims.TYPE_REFRESH)
        .compact();
  }

  @Override
  public UUID parseRefreshToken(String token) {
    try {
      Claims claims =
          Jwts.parser()
              .verifyWith(refreshKey)
              .requireIssuer(refresh.getIssuer())
              .build()
              .parseSignedClaims(token)
              .getPayload();

      if (!JwtClaims.TYPE_REFRESH.equals(claims.get(JwtClaims.TYPE, String.class))) {
        throw new BadCredentialsException("Not a refresh token");
      }
      String userId = claims.get(JwtClaims.USER_ID, String.class);
      if (userId == null || userId.isBlank()) {
        throw new BadCredentialsException("Token has no userId");
      }
      return UUID.fromString(userId);
    } catch (JwtException | IllegalArgumentException ex) {
      throw new BadCredentialsException("Invalid or expired refresh token", ex);
    }
  }

  private JwtBuilder base(UUID userId, CommonAppProperties.Token cfg, SecretKey key) {
    Instant now = Instant.now();
    return Jwts.builder()
        .issuer(cfg.getIssuer())
        .subject(userId.toString())
        .claim(JwtClaims.USER_ID, userId.toString())
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(cfg.getExpiration())))
        .signWith(key);
  }
}
