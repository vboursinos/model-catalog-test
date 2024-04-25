package database.dto;

import java.util.UUID;

/** A DTO for the Metric entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetricDTO {

  private UUID id;
  private String metric;

  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "MetricDTO{" + "metric='" + metric + '\'' + '}';
  }
}
