package com.example.huellitas_felices.security;

            import lombok.RequiredArgsConstructor;
            import org.springframework.context.annotation.Bean;
            import org.springframework.context.annotation.Configuration;
            import org.springframework.http.HttpMethod;
            import org.springframework.security.authentication.AuthenticationManager;
            import org.springframework.security.authentication.AuthenticationProvider;
            import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
            import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
            import org.springframework.security.config.annotation.web.builders.HttpSecurity;
            import org.springframework.security.config.http.SessionCreationPolicy;
            import org.springframework.security.core.userdetails.UserDetailsService;
            import org.springframework.security.crypto.password.PasswordEncoder;
            import org.springframework.security.web.SecurityFilterChain;
            import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
            import org.springframework.web.cors.CorsConfiguration;
            import org.springframework.web.cors.CorsConfigurationSource;
            import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

            import java.util.Arrays;

            @Configuration
            @RequiredArgsConstructor
            public class SecurityConfig {

                private final JwtAuthFilter jwtAuthFilter;
                private final UserDetailsService userDetailsService;
                private final PasswordEncoder passwordEncoder;

                @Bean
                public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                    http
                        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                        .csrf(csrf -> csrf.disable())
                        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html","/auth/login","/auth/register/adopter").permitAll()
                            .requestMatchers(HttpMethod.GET, "/pets/**").permitAll()
                            .anyRequest().authenticated()
                        )
                        .authenticationProvider(authenticationProvider())
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                    return http.build();
                }

                @Bean
                public CorsConfigurationSource corsConfigurationSource() {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000",
                        "http://localhost:5173",
                        "http://localhost:5174",
                        "http://127.0.0.1:5173",
                        "http://127.0.0.1:3000",
                        "http://127.0.0.1:5174"
                    ));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Origin", "Accept"));
                    configuration.setAllowCredentials(true);
                    configuration.setMaxAge(3600L);

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    return source;
                }

                @Bean
                public AuthenticationProvider authenticationProvider() {
                    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                    provider.setUserDetailsService(userDetailsService);
                    provider.setPasswordEncoder(passwordEncoder);
                    return provider;
                }

                @Bean
                public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
                    return cfg.getAuthenticationManager();
                }
            }