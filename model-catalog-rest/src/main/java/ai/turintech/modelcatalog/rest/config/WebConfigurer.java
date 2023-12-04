package ai.turintech.modelcatalog.rest.config;

import ai.turintech.modelcatalog.rest.errors.ExceptionTranslator;
import ai.turintech.modelcatalog.rest.support.constants.ApplicationProfiles;
import ai.turintech.modelcatalog.rest.support.database.h2.H2ConfigurationHelper;
import ai.turintech.modelcatalog.rest.support.errors.ReactiveWebExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
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

  public WebConfigurer(Environment env) {

    if (env.acceptsProfiles(Profiles.of(ApplicationProfiles.SPRING_PROFILE_DEVELOPMENT))) {
      try {
        H2ConfigurationHelper.initH2Console();
      } catch (Exception e) {
        // Console may already be running on another app or after a refresh.
        e.printStackTrace();
      }
    }
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // TODO- Apply or provide this configuration using application properties file
    CorsConfiguration config = new CorsConfiguration();
    if (!CollectionUtils.isEmpty(config.getAllowedOrigins())
        || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
      log.debug("Registering CORS filter");
      source.registerCorsConfiguration("/api/**", config);
      source.registerCorsConfiguration("/management/**", config);
      source.registerCorsConfiguration("/v3/api-docs", config);
      source.registerCorsConfiguration("/swagger-ui/**", config);
    }
    return source;
  }
  
  /**
   * Cors configuration.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
      // @formatter:off
      registry
      .addMapping("/**")
          .allowedOrigins("*")
          .maxAge(3600)
          .allowedHeaders( "X-HTTP-Method-Override", "Cache-Control", "Content-Type",
              "Content-Length", "Content-Disposition", "Location", "Transfer-Encoding", "Upload-Offset", "Upload-Metadata",
              "Upload-Checksum", "Upload-Length", "Upload-Expires", "Upload-Defer-Length", "Upload-Concat", "Tus-Version",
              "Tus-Resumable", "Tus-Extension", "Tus-Max-Size", "Tus-Checksum-Algorithm", "X-Forwarded-For", "Location" )
          .exposedHeaders("X-HTTP-Method-Override", "Cache-Control", "Content-Type",
              "Content-Length", "Content-Disposition", "Location", "Transfer-Encoding", "Upload-Offset", "Upload-Metadata",
              "Upload-Checksum", "Upload-Length", "Upload-Expires", "Upload-Defer-Length", "Upload-Concat", "Tus-Version",
              "Tus-Resumable", "Tus-Extension", "Tus-Max-Size", "Tus-Checksum-Algorithm", "X-Forwarded-For", "Location")
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
  @Order(
      -2) // The handler must have precedence over WebFluxResponseStatusExceptionHandler and Spring
  // Boot's ErrorWebExceptionHandler
  public WebExceptionHandler problemExceptionHandler(
      ObjectMapper mapper, ExceptionTranslator problemHandling) {
    return new ReactiveWebExceptionHandler(problemHandling, mapper);
  }
}
