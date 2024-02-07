package ai.turintech.modelcatalog.rest.reporting;

import io.sentry.Sentry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SentryIntegrationManagerImpl implements SentryIntegrationManager {

  @Autowired(required = false)
  private SentryIntegrationWrapper sentryWrapper;

  @Value("#{new Boolean('${SENTRY_ENABLED}')}")
  private Boolean isSentryEnabled;

  @Value("${sentry.dsn}")
  private String sentryDSN;

  @Value("${sentry.environment}")
  private String sentryEnv;

  @Value("#{new Double('${sentry.sample-rate}')}")
  private Double sentrySampleRate;

  public void captureException(Throwable throwable) {
    if (sentryWrapper != null) {
      sentryWrapper.captureException(throwable);
    }
  }

  @PostConstruct
  public void init() {
    if (!isSentryEnabled) {
      sentryDSN = "";
    }
    Sentry.init(
        options -> {
          options.setDsn(sentryDSN);
          options.setEnvironment(sentryEnv);
          options.setSampleRate(sentrySampleRate);
        });
  }
}
