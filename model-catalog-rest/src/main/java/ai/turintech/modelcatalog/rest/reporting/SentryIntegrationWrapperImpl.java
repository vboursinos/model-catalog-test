package ai.turintech.modelcatalog.rest.reporting;

import io.sentry.Sentry;
import org.springframework.stereotype.Component;

/** Bean implementation just to wrap Sentry client invocation. */
@Component
public class SentryIntegrationWrapperImpl implements SentryIntegrationWrapper {
  public void captureException(Throwable throwable) {
    Sentry.captureException(throwable);
  }
}
