package database.entity;

import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A IntegerParameterValue. */
@Entity
@Table(name = "integer_parameter_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameterValue {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "lower", nullable = false)
  private Integer lower;

  @Column(name = "upper", nullable = false)
  private Integer upper;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parameter_type_definition_id", referencedColumnName = "id")
  private IntegerParameter integerParameter;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Integer getLower() {
    return this.lower;
  }

  public IntegerParameterValue lower(Integer lower) {
    this.setLower(lower);
    return this;
  }

  public void setLower(Integer lower) {
    this.lower = lower;
  }

  public Integer getUpper() {
    return this.upper;
  }

  public IntegerParameterValue upper(Integer upper) {
    this.setUpper(upper);
    return this;
  }

  public void setUpper(Integer upper) {
    this.upper = upper;
  }

  public IntegerParameter getIntegerParameter() {
    return this.integerParameter;
  }

  public void setIntegerParameter(IntegerParameter integerParameter) {
    this.integerParameter = integerParameter;
  }

  public IntegerParameterValue integerParameter(IntegerParameter integerParameter) {
    this.setIntegerParameter(integerParameter);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IntegerParameterValue)) {
      return false;
    }
    return getId() != null && getId().equals(((IntegerParameterValue) o).getId());
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
    return "IntegerParameterValue{"
        + "id="
        + getId()
        + ", lower="
        + getLower()
        + ", upper="
        + getUpper()
        + "}";
  }
}
