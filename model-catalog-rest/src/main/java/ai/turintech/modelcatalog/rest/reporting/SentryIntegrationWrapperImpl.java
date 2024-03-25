package ai.turintech.modelcatalog.rest.reporting;

import ai.turintech.modelcatalog.rest.support.constants.ApplicationProfiles;
import io.sentry.Sentry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** Bean implementation just to wrap Sentry client invocation. */
@Profile(ApplicationProfiles.SENTRY)
@Component
public class SentryIntegrationWrapperImpl implements SentryIntegrationWrapper {
  public void captureException(Throwable throwable) {
    Sentry.captureException(throwable);
  }
}
