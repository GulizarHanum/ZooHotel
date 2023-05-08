package com.diploma.zoo_hotel.service;

import com.diploma.zoo_hotel.dto.AuthTokensDto;
import com.diploma.zoo_hotel.entities.RefreshToken;
import com.diploma.zoo_hotel.entities.User;
import com.diploma.zoo_hotel.exceptions.BadRefreshTokenException;
import com.diploma.zoo_hotel.exceptions.InvalidJwtTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Поставщик access-токенов
 */
@Component
public class AccessTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.access-token.expirationMs}")
    private long accessTokenExpirationMs;

    private final UserService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public AccessTokenProvider(UserService userDetailsService, RefreshTokenService refreshTokenService) {
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    /*
    После инициализации компонента шифруем наш Секрет в кодировку base64
     */
    @PostConstruct
    protected void init() {
        SECRET = Base64.getEncoder().encodeToString(SECRET.getBytes(UTF_8));
    }

    /**
     * Получить новые access + refresh токены
     *
     * @param email эл. почта пользователя
     *
     * @return токены
     */
    public AuthTokensDto generateAllTokens(String email) {
        long actuality = this.getAccessTokenExpireDateInMs();
        String accessToken = makeAccessToken(email, actuality);
        UUID refreshToken = refreshTokenService.createToken(email).getToken();

        return new AuthTokensDto(accessToken, refreshToken, actuality);
    }

    /**
     * Получить новые access + refresh токены на основе refresh-токена
     *
     * @param refreshToken refresh-токен
     *
     * @return токены
     */
    public AuthTokensDto regenerateAllTokens(UUID refreshToken) {
        RefreshToken entity = this.logoutUser(refreshToken);

        String email = entity.getUser().getEmail();
        long actuality = this.getAccessTokenExpireDateInMs();

        String accessToken = makeAccessToken(email, actuality);
        UUID newRefreshToken = refreshTokenService.createToken(email).getToken();

        return new AuthTokensDto(accessToken, newRefreshToken, actuality);
    }

    /**
     * Получить внутренние данные авторизации для спринга
     *
     * @param token токен
     *
     * @return данные авторизации для спринга
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    /**
     * Извлечь токен из запроса
     *
     * @param req http запрос
     *
     * @return токен
     */
    public String parseTokenHeader(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null) {
            if (bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            } else {
                throw new InvalidJwtTokenException(String.format("Получен некорректный заголовок токена: %s", bearerToken));
            }
        } else {
            throw new InvalidJwtTokenException("Access token отсутствует в заголовке запроса");
        }
    }

    /**
     * Проверка на целостность и актуальность токена
     *
     * @param token токен
     *
     * @return хороший/плохой токен
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtTokenException(String.format("Истек срок жизни access-токена: %s", token));
        } catch (JwtException e) {
            throw new InvalidJwtTokenException(String.format("Получено некорректное тело токена: %s", token));
        }
    }

    /**
     * Выполнить деаутентификацию пользователя из системы
     *
     * @param refreshToken токен
     *
     * @return удаленный токен
     */
    public RefreshToken logoutUser(UUID refreshToken) {
        RefreshToken entity = refreshTokenService.findByUUIDToken(refreshToken);
        if (!refreshTokenService.isTokenActual(entity)) {
            throw new BadRefreshTokenException("Вы были неактивны долгое время. Пожалуйста, войдите в систему снова.");
        }

        refreshTokenService.deleteToken(entity.getId());
        return entity;
    }

    private List<String> getRoleNames(Set<User.Role> userRoles) {
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> result.add(role.toString()));
        return result;
    }

    /**
     * Извлечь никнейм из токена
     *
     * @param token токен
     *
     * @return никнейм
     */
    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Создать access-токен
     *
     * @param email эмейл
     *
     * @return jwt токен
     */
    private String makeAccessToken(String email, long accessTokenExpireDateInMs) {
        User user = userDetailsService.findUserByEmail(email);

        Claims claims = Jwts.claims()
                .setIssuer("backend") // кто выдал токен
                .setSubject(email); // кому выдали
        claims.put("roles", getRoleNames(user.getRoles()));
        claims.put("username", user.getEmail());
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("email", user.getEmail());

        Date validity = new Date(accessTokenExpireDateInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date()) // когда выдано
                .setExpiration(validity) // когда протухнет
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    /**
     * Получить время деактивации access-токена в миллисекундах
     *
     * @return время деактивации access-токена в виде "1502305985425"
     */
    private Long getAccessTokenExpireDateInMs() {
        Date now = new Date();
        return now.getTime() + accessTokenExpirationMs;
    }
}
