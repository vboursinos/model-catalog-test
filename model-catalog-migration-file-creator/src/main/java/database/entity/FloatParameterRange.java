package database.entity;

import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A FloatParameterRange. */
@Entity
@Table(name = "float_parameter_range")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloatParameterRange {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "is_left_open", nullable = false)
  private Boolean isLeftOpen;

  @Column(name = "is_right_open", nullable = false)
  private Boolean isRightOpen;

  @Column(name = "lower", nullable = false)
  private Double lower;

  @Column(name = "upper", nullable = false)
  private Double upper;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parameter_type_definition_id", referencedColumnName = "id")
  private FloatParameter floatParameter;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Boolean getIsLeftOpen() {
    return this.isLeftOpen;
  }

  public FloatParameterRange isLeftOpen(Boolean isLeftOpen) {
    this.setIsLeftOpen(isLeftOpen);
    return this;
  }

  public void setIsLeftOpen(Boolean isLeftOpen) {
    this.isLeftOpen = isLeftOpen;
  }

  public Boolean getIsRightOpen() {
    return this.isRightOpen;
  }

  public FloatParameterRange isRightOpen(Boolean isRightOpen) {
    this.setIsRightOpen(isRightOpen);
    return this;
  }

  public void setIsRightOpen(Boolean isRightOpen) {
    this.isRightOpen = isRightOpen;
  }

  public Double getLower() {
    return this.lower;
  }

  public FloatParameterRange lower(Double lower) {
    this.setLower(lower);
    return this;
  }

  public void setLower(Double lower) {
    this.lower = lower;
  }

  public Double getUpper() {
    return this.upper;
  }

  public FloatParameterRange upper(Double upper) {
    this.setUpper(upper);
    return this;
  }

  public void setUpper(Double upper) {
    this.upper = upper;
  }

  public FloatParameter getFloatParameter() {
    return this.floatParameter;
  }

  public void setFloatParameter(FloatParameter floatParameter) {
    this.floatParameter = floatParameter;
  }

  public FloatParameterRange floatParameter(FloatParameter floatParameter) {
    this.setFloatParameter(floatParameter);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FloatParameterRange)) {
      return false;
    }
    return getId() != null && getId().equals(((FloatParameterRange) o).getId());
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "FloatParameterRange{"
        + "id="
        + getId()
        + ", isLeftOpen='"
        + getIsLeftOpen()
        + "'"
        + ", isRightOpen='"
        + getIsRightOpen()
        + "'"
        + ", lower="
        + getLower()
        + ", upper="
        + getUpper()
        + "}";
  }
}
