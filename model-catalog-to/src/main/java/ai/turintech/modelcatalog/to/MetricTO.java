package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractUUIDIdentityTO;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class MetricTO extends AbstractUUIDIdentityTO<UUID> implements Serializable {

  private static final long serialVersionUID = 4206079281795273477L;
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
    if (!(o instanceof MetricTO)) {
      return false;
    }

    MetricTO metricDTO = (MetricTO) o;
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
