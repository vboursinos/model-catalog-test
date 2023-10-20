package ai.turintech.modelcatalog.entity;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;

/**
 * A ParameterTypeDefinition.
 */
@Table("parameter_type_definition")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterTypeDefinition implements Serializable, Persistable<UUID> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private UUID id;

    @NotNull(message = "must not be null")
    @Column("ordering")
    private Integer ordering;

    @Transient
    private boolean isPersisted;

    @Transient
    private Set<Parameter> parameters = new HashSet<>();

    @Transient
    private Set<ParameterType> types = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ParameterTypeDefinition id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getOrdering() {
        return this.ordering;
    }

    public ParameterTypeDefinition ordering(Integer ordering) {
        this.setOrdering(ordering);
        return this;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public ParameterTypeDefinition setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Parameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
        if (this.parameters != null) {
            this.parameters.forEach(i -> i.setDefinitions(null));
        }
        if (parameters != null) {
            parameters.forEach(i -> i.setDefinitions(this));
        }
        this.parameters = parameters;
    }

    public ParameterTypeDefinition parameters(Set<Parameter> parameters) {
        this.setParameters(parameters);
        return this;
    }

    public ParameterTypeDefinition addParameter(Parameter parameter) {
        this.parameters.add(parameter);
        parameter.setDefinitions(this);
        return this;
    }

    public ParameterTypeDefinition removeParameter(Parameter parameter) {
        this.parameters.remove(parameter);
        parameter.setDefinitions(null);
        return this;
    }

    public Set<ParameterType> getTypes() {
        return this.types;
    }

    public void setTypes(Set<ParameterType> parameterTypes) {
        if (this.types != null) {
            this.types.forEach(i -> i.setParameterTypeDefinition(null));
        }
        if (parameterTypes != null) {
            parameterTypes.forEach(i -> i.setParameterTypeDefinition(this));
        }
        this.types = parameterTypes;
    }

    public ParameterTypeDefinition types(Set<ParameterType> parameterTypes) {
        this.setTypes(parameterTypes);
        return this;
    }

    public ParameterTypeDefinition addType(ParameterType parameterType) {
        this.types.add(parameterType);
        parameterType.setParameterTypeDefinition(this);
        return this;
    }

    public ParameterTypeDefinition removeType(ParameterType parameterType) {
        this.types.remove(parameterType);
        parameterType.setParameterTypeDefinition(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParameterTypeDefinition)) {
            return false;
        }
        return getId() != null && getId().equals(((ParameterTypeDefinition) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParameterTypeDefinition{" +
            "id=" + getId() +
            ", ordering=" + getOrdering() +
            "}";
    }
}
