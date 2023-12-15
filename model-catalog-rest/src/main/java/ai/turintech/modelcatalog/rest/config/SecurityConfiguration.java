package ai.turintech.modelcatalog.rest.config;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedHeaders(List.of("*"));
    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
    corsConfiguration.setAllowedMethods(List.of("*"));
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setExposedHeaders(List.of("*"));

    http.securityMatcher(
            new NegatedServerWebExchangeMatcher(
                new OrServerWebExchangeMatcher(
                    pathMatchers("/app/**", "/i18n/**", "/content/**", "/swagger-ui/**"))))
        .csrf(csrf -> csrf.disable())
        .headers(
            headers ->
                headers
                    .contentSecurityPolicy(csp -> csp.reportOnly(true))
                    .referrerPolicy(
                        referrer ->
                            referrer.policy(
                                ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.UNSAFE_URL))
                    .permissionsPolicy(
                        permissions ->
                            permissions.policy(
                                "camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")))
        .requestCache(cache -> cache.requestCache(NoOpServerRequestCache.getInstance()))
        .cors(cors -> cors.configurationSource(request -> corsConfiguration))
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
                    // Graphql gui
                    .pathMatchers("/**")
                    .permitAll()
                    .pathMatchers("/graphiql/**", "/vendor/**")
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
