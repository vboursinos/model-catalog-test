package ai.turintech.modelcatalog.entity;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A ParameterDistributionType.
 */
@Table("parameter_distribution_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterDistributionType implements Serializable, Persistable<UUID> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private UUID id;

    //@NotNull(message = "must not be null")
    @Column("name")
    private String name;

    @Transient
    private boolean isPersisted;

    @Transient
    private Parameter parameter;

    @Column("parameter_id")
    private UUID parameterId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ParameterDistributionType id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public ParameterDistributionType setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Parameter getParameter() {
        return this.parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
        this.parameterId = parameter != null ? parameter.getId() : null;
    }

    public ParameterDistributionType parameter(Parameter parameter) {
        this.setParameter(parameter);
        return this;
    }

    public UUID getParameterId() {
        return this.parameterId;
    }

    public void setParameterId(UUID parameter) {
        this.parameterId = parameter;
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParameterDistributionType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
