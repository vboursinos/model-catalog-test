package ai.turintech.modelcatalog.rest.config;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.securityMatcher(
            new NegatedServerWebExchangeMatcher(
                new OrServerWebExchangeMatcher(
                    pathMatchers("/app/**", "/i18n/**", "/content/**", "/swagger-ui/**"))))
        .csrf(csrf -> csrf.disable())
        .headers(
            headers ->
                headers
                    .contentSecurityPolicy(
                        csp ->
                            csp.policyDirectives(
                                "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:"))
                    .frameOptions(frameOptions -> frameOptions.mode(Mode.DENY))
                    .referrerPolicy(
                        referrer ->
                            referrer.policy(
                                ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy
                                    .STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                    .permissionsPolicy(
                        permissions ->
                            permissions.policy(
                                "camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")))
        .requestCache(cache -> cache.requestCache(NoOpServerRequestCache.getInstance()))
        .authorizeExchange(
            authz ->
                // prettier-ignore
                authz
                    .pathMatchers("/api/authenticate")
                    .permitAll()
                    .pathMatchers("/api/admin/**")
                    .permitAll()
                    .pathMatchers("/api/**")
                    .permitAll()
                    // swagger-ui open public config
                    .pathMatchers("/swagger-ui/**")
                    .permitAll()
                    .pathMatchers("/webjars/swagger-ui/**")
                    .permitAll()
                    .pathMatchers("/v3/api-docs/**")
                    .permitAll()
                    .pathMatchers("/management/health")
                    .permitAll()
                    .pathMatchers("/management/health/**")
                    .permitAll()
                    .pathMatchers("/management/info")
                    .permitAll()
                    .pathMatchers("/management/prometheus")
                    .permitAll()
                    .pathMatchers("/actuator/**")
                    .permitAll()
                    .pathMatchers("/management/**")
                    .permitAll())
        .httpBasic(basic -> basic.disable());
    // .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
    return http.build();
  }
}
