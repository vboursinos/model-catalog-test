package ai.turintech.modelcatalog.dto;

import ai.turintech.components.data.common.dto.AbstractUUIDIdentityDTO;
import java.util.Objects;

/** A DTO for the Metric entity. */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetricDTO extends AbstractUUIDIdentityDTO {

  private String metric;

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
    if (this.getId() == null) {
      return false;
    }
    return Objects.equals(this.getId(), metricDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "MetricDTO{" + "id='" + getId() + "'" + ", metric='" + getMetric() + "'" + "}";
  }
}
