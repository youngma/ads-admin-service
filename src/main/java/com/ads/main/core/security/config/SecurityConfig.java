package com.ads.main.core.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ads.main.core.security.fillter.ExceptionHandlerFilter;
import com.ads.main.core.security.fillter.FilterSkipMatcher;
import com.ads.main.core.security.fillter.JWTAuthenticationFilter;
import com.ads.main.core.security.fillter.JWTAuthorizationFilter;
import com.ads.main.core.security.handle.CustomAuthenticationFailureHandler;
import com.ads.main.core.security.handle.CustomAuthenticationProvider;
import com.ads.main.core.security.handle.JwtAccessDeniedHandler;
import com.ads.main.core.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityProperties securityProperties = new SecurityProperties();
    private final ObjectMapper objectMapper;
    private final CustomUserDetailsService userDetailsService;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    /**
     * 1. 정적 자원(Resource)에 대해서 인증된 사용자가  정적 자원의 접근에 대해 ‘인가’에 대한 설정을 담당하는 메서드이다.
     *
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web
                .ignoring()
                .requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "admin/xauth/**",
                "/mgmt/**",
                "/error"
                )
                ;
    }

    /**
     * 2. HTTP에 대해서 ‘인증’과 ‘인가’를 담당하는 메서드이며 필터를 통해 인증 방식과 인증 절차에 대해서 등록하며 설정을 담당하는 메서드이다.
     *
     * @param httpSecurity
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        JWTAuthenticationFilter jWTAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager(), securityProperties, objectMapper);
        jWTAuthenticationFilter.setFilterProcessesUrl("/**/login");
        jWTAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        httpSecurity.csrf().disable()       // [STEP1] 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않는다.
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .cors()
                .and()
                .httpBasic().disable()  // [STEP2] form 기반의 로그인에 대해 비 활성화하며 커스텀으로 구성한 필터를 사용한다.
                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/api/admin/**").hasAnyAuthority(Role.ADMIN.getRoleName());
                    auth.requestMatchers(new FilterSkipMatcher(skipPaths(), "/**")).permitAll();
                    auth.anyRequest().authenticated();
                })// // [STEP3] 토큰을 활용하는 경우 모든 요청에 대해 '인가'에 대해서 사용.

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // [STEP5] Session 기반의 인증기반을 사용하지 않고 추후 JWT를 이용하여서 인증 예정
                .and()
                .addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)  // Filter ExceptionHandle
                .addFilter(jWTAuthenticationFilter)  // [STEP6] Spring Security JWT Filter Load
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), securityProperties, userDetailsService, objectMapper));

        return httpSecurity.build();  // [STEP7] 최종 구성한 값을 사용함.
    }


    /**
     * 3. authenticate 의 인증 메서드를 제공하는 매니져로'Provider'의 인터페이스를 의미합니다.
     * - 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
     *
     * @return AuthenticationManager
     */
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(authProvider());
    }


    /**
     * 4. '인증' 제공자로 사용자의 이름과 비밀번호가 요구됩니다.
     * - 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
     *
     * @return CustomAuthenticationProvider
     */
    @Bean
    protected CustomAuthenticationProvider authProvider() {
        return new CustomAuthenticationProvider(userDetailsService, SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
    }

    /**
     * 5. 비밀번호를 암호화하기 위한 BCrypt 인코딩을 통하여 비밀번호에 대한 암호화를 수행합니다.
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    protected BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getStrength());
    }

    /**
     * 6. 인증 실패 처리를 위한 AuthenticationFailureHandler
     *
     * @return AuthenticationFailureHandler
     */
    @Bean
    protected AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler(objectMapper);
    }


    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

             corsConfiguration.setAllowedOrigins(List.of("*"));
            corsConfiguration.setAllowedMethods(List.of(new String[]{"POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD"}));
            corsConfiguration.setAllowedHeaders(List.of(new String[]{"Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"}));
            corsConfiguration.setExposedHeaders(List.of(new String[]{"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization", "Content-Disposition"}));
            corsConfiguration.setMaxAge(3600L);

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return (CorsConfigurationSource) urlBasedCorsConfigurationSource;
    }


    List<String> skipPaths() {
        return List.of(
                "GET,/favicon.ico",
                "GET,/static",
                "GET,/static",
                "GET,/resources/**"
                );
    }

}
