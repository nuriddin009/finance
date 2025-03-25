package uz.aloqabank.template.config.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import uz.aloqabank.template.dto.User;
import uz.aloqabank.template.exception.BadRequestException;
import uz.aloqabank.template.exception.ExceptionHandler;
import uz.aloqabank.template.exception.SessionExpiredException;
import uz.aloqabank.template.model.MUser;
import uz.aloqabank.template.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static uz.aloqabank.template.constants.Constants.ALGORITHM_SECRET;

@Component
public class TokenProvider implements ExceptionHandler {

    private static final String AUTHORITIES_KEY = "auth";
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${security.authentication.jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;
    private final UserRepository userRepository;

    public TokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createToken(Authentication authentication, HttpServletRequest request) {
        Date expiryDate = Date.from(Instant.now().plusSeconds(tokenValidityInSeconds));
        return JWT
            .create()
            .withSubject(authentication.getName())
            .withClaim(AUTHORITIES_KEY, authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .withExpiresAt(expiryDate).sign(getAlgorithm());
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT jwt = decodedJWT(token);

        Collection<? extends GrantedAuthority> authorities = jwt.getClaim(AUTHORITIES_KEY) == null ? Collections.EMPTY_LIST :
            Arrays.stream(jwt.getClaim(AUTHORITIES_KEY).asArray(String.class))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Optional<MUser> optUser = userRepository.findByUsername(jwt.getSubject());
        MUser mUser = optUser.orElseThrow();
        User user = new User(mUser.getUsername(), mUser.getPassword(), authorities);
        user.setId(mUser.getId());
        user.setFirstName(mUser.getFirstName());
        user.setLastName(mUser.getLastName());
        user.setToken(token);
        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            DecodedJWT jwt = decodedJWT(authToken);
            String username = jwt.getSubject();
            if (!StringUtils.isEmpty(username)) {
                Optional<MUser> mUserOpt = userRepository.findByUsernameAndDeletedFalse(username);
                return mUserOpt.isPresent();
            }
        } catch (IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }

    public DecodedJWT decodedJWT(String token) {
        try {
            JWTVerifier tokenVerifier = JWT.require(getAlgorithm()).build();
            return tokenVerifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new SessionExpiredException("Token Expiried");
        } catch (RuntimeException e) {
            throw new BadRequestException("Invalid Token");
        }
    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(ALGORITHM_SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
