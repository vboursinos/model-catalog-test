package ai.turintech.modelcatalog.rest.reporting;

public interface SentryIntegrationManager {
  public void captureException(Throwable throwable);
}
