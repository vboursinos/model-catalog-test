package database.entity;

import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A CategoricalParameterValue. */
@Entity
@Table(name = "categorical_parameter_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameterValue {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "value", nullable = false)
  private String value;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parameter_type_definition_id", referencedColumnName = "id")
  private CategoricalParameter categoricalParameter;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getValue() {
    return this.value;
  }

  public CategoricalParameterValue value(String value) {
    this.setValue(value);
    return this;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public CategoricalParameter getCategoricalParameter() {
    return this.categoricalParameter;
  }

  public void setCategoricalParameter(CategoricalParameter categoricalParameter) {
    this.categoricalParameter = categoricalParameter;
  }

  public CategoricalParameterValue categoricalParameter(CategoricalParameter categoricalParameter) {
    this.setCategoricalParameter(categoricalParameter);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CategoricalParameterValue)) {
      return false;
    }
    return getId() != null && getId().equals(((CategoricalParameterValue) o).getId());
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
    return "CategoricalParameterValue{" + "id=" + getId() + ", value='" + getValue() + "'" + "}";
  }
}
