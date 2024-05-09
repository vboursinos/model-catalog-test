package ai.turintech.modelcatalog.rest.config;

import ai.turintech.modelcatalog.rest.errors.ExceptionTranslator;
import ai.turintech.modelcatalog.rest.support.errors.ReactiveWebExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.WebExceptionHandler;

/** Configuration of web application with Servlet 3.0 APIs. */
@Configuration
public class WebConfigurer implements WebFluxConfigurer {

  private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // TODO- Apply or provide this configuration using application properties file
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedHeader("X-HTTP-Method-Override");
    config.addAllowedHeader("Cache-Control");
    config.addAllowedHeader("Content-Type");
    config.addAllowedHeader("Content-Length");
    config.addAllowedHeader("Content-Disposition");
    config.addAllowedHeader("Location");
    config.addAllowedHeader("Transfer-Encoding");
    config.addAllowedHeader("Upload-Offset");
    config.addAllowedHeader("Upload-Metadata");
    config.addAllowedHeader("Upload-Checksum");
    config.addAllowedHeader("Upload-Length");
    config.addAllowedHeader("Upload-Expires");
    config.addAllowedHeader("Upload-Defer-Length");
    config.addAllowedHeader("Upload-Concat");
    config.addAllowedHeader("Tus-Version");
    config.addAllowedHeader("Tus-Resumable");
    config.addAllowedHeader("Tus-Extension");
    config.addAllowedHeader("Tus-Max-Size");
    config.addAllowedHeader("Tus-Checksum-Algorithm");
    config.addAllowedHeader("X-Forwarded-For");
    config.addAllowedHeader("Location");
    config.setMaxAge(Duration.ofSeconds(3600L));
    config.addAllowedOrigin("*");
    config.addExposedHeader("X-HTTP-Method-Override");
    config.addExposedHeader("Cache-Control");
    config.addExposedHeader("Content-Type");
    config.addExposedHeader("Content-Length");
    config.addExposedHeader("Content-Disposition");
    config.addExposedHeader("Location");
    config.addExposedHeader("Transfer-Encoding");
    config.addExposedHeader("Upload-Offset");
    config.addExposedHeader("Upload-Metadata");
    config.addExposedHeader("Upload-Checksum");
    config.addExposedHeader("Upload-Length");
    config.addExposedHeader("Upload-Expires");
    config.addExposedHeader("Upload-Defer-Length");
    config.addExposedHeader("Upload-Concat");
    config.addExposedHeader("Tus-Version");
    config.addExposedHeader("Tus-Resumable");
    config.addExposedHeader("Tus-Extension");
    config.addExposedHeader("Tus-Max-Size");
    config.addExposedHeader("Tus-Checksum-Algorithm");
    config.addExposedHeader("X-Forwarded-For");
    config.addExposedHeader("Location");
    config.addAllowedMethod(HttpMethod.GET);
    config.addAllowedMethod(HttpMethod.POST.name());
    config.addAllowedMethod(HttpMethod.PUT.name());
    config.addAllowedMethod(HttpMethod.PATCH.name());
    config.addAllowedMethod(HttpMethod.DELETE.name());
    config.addAllowedMethod(HttpMethod.TRACE.name());
    config.addAllowedMethod(HttpMethod.OPTIONS.name());
    config.addAllowedMethod(HttpMethod.HEAD.name());
    // if (!CollectionUtils.isEmpty(config.getAllowedOrigins())
    // || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
    log.debug("Registering CORS filter");
    source.registerCorsConfiguration("/api/**", config);
    source.registerCorsConfiguration("/management/**", config);
    source.registerCorsConfiguration("/v3/api-docs", config);
    source.registerCorsConfiguration("/swagger-ui/**", config);
    source.registerCorsConfiguration("*", config);
    // }
    // source.registerCorsConfiguration("*", config);
    return source;
  }

  /** Cors configuration. */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // @formatter:off
    registry
        .addMapping("/**")
        .allowedOrigins("*")
        .maxAge(3600)
        .allowedHeaders(
            "X-HTTP-Method-Override",
            "Cache-Control",
            "Content-Type",
            "Content-Length",
            "Content-Disposition",
            "Location",
            "Transfer-Encoding",
            "Upload-Offset",
            "Upload-Metadata",
            "Upload-Checksum",
            "Upload-Length",
            "Upload-Expires",
            "Upload-Defer-Length",
            "Upload-Concat",
            "Tus-Version",
            "Tus-Resumable",
            "Tus-Extension",
            "Tus-Max-Size",
            "Tus-Checksum-Algorithm",
            "X-Forwarded-For",
            "Location")
        .exposedHeaders(
            "X-HTTP-Method-Override",
            "Cache-Control",
            "Content-Type",
            "Content-Length",
            "Content-Disposition",
            "Location",
            "Transfer-Encoding",
            "Upload-Offset",
            "Upload-Metadata",
            "Upload-Checksum",
            "Upload-Length",
            "Upload-Expires",
            "Upload-Defer-Length",
            "Upload-Concat",
            "Tus-Version",
            "Tus-Resumable",
            "Tus-Extension",
            "Tus-Max-Size",
            "Tus-Checksum-Algorithm",
            "X-Forwarded-For",
            "Location")
        .allowedMethods(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.TRACE.name(),
            HttpMethod.OPTIONS.name(),
            HttpMethod.HEAD.name());

    // @formatter:on
  }

  // TODO: remove when this is supported in spring-boot
  @Bean
  HandlerMethodArgumentResolver reactivePageableHandlerMethodArgumentResolver() {
    return new ReactivePageableHandlerMethodArgumentResolver();
  }

  // TODO: remove when this is supported in spring-boot
  @Bean
  HandlerMethodArgumentResolver reactiveSortHandlerMethodArgumentResolver() {
    return new ReactiveSortHandlerMethodArgumentResolver();
  }

  @Bean
  @Order(-2) // The handler must have precedence over WebFluxResponseStatusExceptionHandler
  // and Spring
  // Boot's ErrorWebExceptionHandler
  public WebExceptionHandler problemExceptionHandler(
      ObjectMapper mapper, ExceptionTranslator problemHandling) {
    return new ReactiveWebExceptionHandler(problemHandling, mapper);
  }
}
