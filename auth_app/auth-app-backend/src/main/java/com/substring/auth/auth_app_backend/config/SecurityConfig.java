package com.substring.auth.auth_app_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.substring.auth.auth_app_backend.dtos.ApiError;
import com.substring.auth.auth_app_backend.security.JwtAuthenticationFilter;
import io.jsonwebtoken.lang.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthenticationSuccessHandler successHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationSuccessHandler successHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(AppConstants.AUTH_PUBLIC_URLS).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 ->
                        oauth2.successHandler(successHandler)
                                .failureHandler(null)
                )
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, e) -> {
                    //error message
//                    e.printStackTrace();
                    response.setStatus(401);
                    response.setContentType("application/json");
                    String message = e.getMessage();
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                        message = error;
                    }
//                    Map<String, Object> errorMap = Map.of("message", message, "statusCode",404);
                    var apiError = ApiError.of(HttpStatus.UNAUTHORIZED.value(), "Unauthorized Access", message, request.getRequestURI(), true);
                    var objectMapper = new ObjectMapper();
                    response.getWriter().write(objectMapper.writeValueAsString(apiError));
                }))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService users() {
//        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//        UserDetails user1 = userBuilder.username("ankit").password("abc").roles("ADMIN").build();
//        UserDetails user2 = userBuilder.username("shiva").password("xyz").roles("ADMIN").build();
//        UserDetails user3 = userBuilder.username("durgesh").password("").roles("USER").build();
//        return new InMemoryUserDetailsManager(user1, user2, user3);
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.front-end-url}") String corsUrls
    ) {

        String[] urls = corsUrls.trim().split(",");

        var config= new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(urls));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH", "HEAD"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        var source= new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;


    }

}
