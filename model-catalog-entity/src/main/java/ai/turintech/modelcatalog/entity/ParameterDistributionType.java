package ai.turintech.modelcatalog.entity;

import ai.turintech.components.data.common.entity.AbstractUUIDIdentityEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/** A ParameterDistributionType. */
@Entity
@Table(name = "parameter_distribution_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Audited
public class ParameterDistributionType extends AbstractUUIDIdentityEntity {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "distribution")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<ParameterTypeDefinition> definitions = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public String getName() {
    return this.name;
  }

  public ParameterDistributionType name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<ParameterTypeDefinition> getDefinitions() {
    return this.definitions;
  }

  public void setDefinitions(Set<ParameterTypeDefinition> parameterTypeDefinitions) {
    if (this.definitions != null) {
      this.definitions.forEach(i -> i.setDistribution(null));
    }
    if (parameterTypeDefinitions != null) {
      parameterTypeDefinitions.forEach(i -> i.setDistribution(this));
    }
    this.definitions = parameterTypeDefinitions;
  }

  public ParameterDistributionType definitions(
      Set<ParameterTypeDefinition> parameterTypeDefinitions) {
    this.setDefinitions(parameterTypeDefinitions);
    return this;
  }

  public ParameterDistributionType addDefinitions(ParameterTypeDefinition parameterTypeDefinition) {
    this.definitions.add(parameterTypeDefinition);
    parameterTypeDefinition.setDistribution(this);
    return this;
  }

  public ParameterDistributionType removeDefinitions(
      ParameterTypeDefinition parameterTypeDefinition) {
    this.definitions.remove(parameterTypeDefinition);
    parameterTypeDefinition.setDistribution(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParameterDistributionType)) {
      return false;
    }
    return getId() != null && getId().equals(((ParameterDistributionType) o).getId());
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
    return "ParameterDistributionType{" + "id=" + getId() + ", name='" + getName() + "'" + "}";
  }
}
