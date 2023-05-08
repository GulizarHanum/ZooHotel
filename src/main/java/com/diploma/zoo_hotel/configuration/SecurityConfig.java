package com.diploma.zoo_hotel.configuration;

import com.diploma.zoo_hotel.service.AccessTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Конфигурация безопасности приложения
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String REGISTER_ADMIN_ENDPOINT = "/users/register/admin";
    public static final String DELETE_ENDPOINT = "/users/delete";

    public static final String PUBLIC_ENDPOINT = "/public/**";
    public static final String SWAGGER_UI_ENDPOINT = "/swagger-ui/**";
    public static final String SWAGGER_API_DOCS_ENDPOINT = "/v3/**";

    private final AccessTokenProvider accessTokenProvider;

    public SecurityConfig(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(PUBLIC_ENDPOINT, SWAGGER_UI_ENDPOINT, SWAGGER_API_DOCS_ENDPOINT);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                //т.к. авторизация пользователя по токену, не нужно создавать и хранить для него сессию
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // обработчик случая, когда запрос не авторизован
                //.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                //.and()

                // добавляем фильтр проверяющий токен во всех запросах
                //.addFilterBefore(new RequestFilter(accessTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //.antMatchers(PUBLIC_ENDPOINT, SWAGGER_UI_ENDPOINT, SWAGGER_API_DOCS_ENDPOINT).permitAll() // доступ разрешен всем
                //.antMatchers(REGISTER_ADMIN_ENDPOINT, DELETE_ENDPOINT).hasAuthority(User.Role.ADMIN.name()) // доступ только администраторам
                .anyRequest().permitAll(); // все остальные эндпоинты под защитой

    }

    //Бин менеджера аутентификации
    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}