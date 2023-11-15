package ai.turintech.modelcatalog.rest.config;

import ai.turintech.modelcatalog.rest.support.constants.ApplicationProfiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Hooks;

@Configuration
@Profile("!" + ApplicationProfiles.SPRING_PROFILE_PRODUCTION)
public class ReactorConfiguration {

  public ReactorConfiguration() {
    Hooks.onOperatorDebug();
  }
}
