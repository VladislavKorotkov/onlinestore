package bsuir.korotkov.onlinestore.config;

import bsuir.korotkov.onlinestore.services.AccountDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig{
    private final AccountDetailsService accountDetailsService;

    private final JWTFilter jwtFilter;

    public SecurityConfig(AccountDetailsService accountDetailsService, JWTFilter jwtFilter) {
        this.accountDetailsService = accountDetailsService;
        this.jwtFilter = jwtFilter;
    }

    private final String[] BLACK_LIST_POST = {"/api/brands","/api/types", "/api/appliances"};
    private final String[] BLACK_LIST_PUT = {"/api/brands/{id}","/api/types/{id}", "/api/appliances/{id}"};
    private final String[] BLACK_LIST_DELETE = {"/api/brands/{id}","/api/types/{id}", "/api/appliances/{id}"};

    private final String[] AUTHORIZED_LIST = {"/api/cart/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/**")
                .authorizeHttpRequests(req ->
                        req.requestMatchers(HttpMethod.POST, BLACK_LIST_POST)
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, BLACK_LIST_DELETE)
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, BLACK_LIST_PUT)
                                .hasRole("ADMIN")
                                .requestMatchers(AUTHORIZED_LIST)
                                .authenticated()
                                .anyRequest()
                                .permitAll()
                )
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
