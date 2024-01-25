package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A CategoricalParameterValue. */
@Entity
@Table(name = "categorical_parameter_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameterValue extends AbstractUUIDIdentityEntity {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "value", nullable = false)
  private String value;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parameter_type_definition_id", referencedColumnName = "id")
  private CategoricalParameter categoricalParameter;

  // jhipster-needle-entity-add-field - JHipster will add fields here

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

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
