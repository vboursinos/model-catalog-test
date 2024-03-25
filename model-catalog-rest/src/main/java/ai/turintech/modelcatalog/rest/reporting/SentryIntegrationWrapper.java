package ai.turintech.modelcatalog.rest.reporting;

public interface SentryIntegrationWrapper {
  public void captureException(Throwable throwable);
}
