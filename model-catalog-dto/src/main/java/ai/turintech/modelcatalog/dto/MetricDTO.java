package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractDTO;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/** A DTO for the Metric entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetricDTO extends AbstractDTO {

  private UUID id;

  private String metric;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MetricDTO)) {
      return false;
    }

    MetricDTO metricDTO = (MetricDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, metricDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "MetricDTO{" + "id='" + getId() + "'" + ", metric='" + getMetric() + "'" + "}";
  }
}
