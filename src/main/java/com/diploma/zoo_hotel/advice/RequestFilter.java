package com.diploma.zoo_hotel.advice;

import com.diploma.zoo_hotel.exceptions.InvalidJwtTokenException;
import com.diploma.zoo_hotel.service.AccessTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Фильтр всех запросов к серверу с проверкой токена
 */
@AllArgsConstructor
@Slf4j
public class RequestFilter extends GenericFilterBean {

    private final AccessTokenProvider accessTokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        try {
            String token = accessTokenProvider.parseTokenHeader(request);
            //если токен верный - аутентифицируем пользователя
            if (accessTokenProvider.isTokenValid(token)) {
                Authentication auth = accessTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        } catch (InvalidJwtTokenException e) {
            log.info(e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(req, res);
    }
}
