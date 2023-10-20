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
 * A Parameter.
 */
@Table("parameter")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Parameter implements Serializable, Persistable<UUID> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private UUID id;

    @NotNull(message = "must not be null")
    @Column("name")
    private String name;

    @NotNull(message = "must not be null")
    @Column("label")
    private String label;

    @Column("description")
    private String description;

    @NotNull(message = "must not be null")
    @Column("enbled")
    private Boolean enbled;

    @NotNull(message = "must not be null")
    @Column("fixed_value")
    private Boolean fixedValue;

    @NotNull(message = "must not be null")
    @Column("ordering")
    private Integer ordering;

    @Transient
    private boolean isPersisted;

    @Transient
    private Set<Model> models = new HashSet<>();

    @Transient
    private Set<ParameterType> types = new HashSet<>();

    @Transient
    private Set<ParameterDistributionType> distributions = new HashSet<>();

    @Transient
    private ParameterTypeDefinition definitions;

    @Column("definitions_id")
    private UUID definitionsId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Parameter id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Parameter name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return this.label;
    }

    public Parameter label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return this.description;
    }

    public Parameter description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnbled() {
        return this.enbled;
    }

    public Parameter enbled(Boolean enbled) {
        this.setEnbled(enbled);
        return this;
    }

    public void setEnbled(Boolean enbled) {
        this.enbled = enbled;
    }

    public Boolean getFixedValue() {
        return this.fixedValue;
    }

    public Parameter fixedValue(Boolean fixedValue) {
        this.setFixedValue(fixedValue);
        return this;
    }

    public void setFixedValue(Boolean fixedValue) {
        this.fixedValue = fixedValue;
    }

    public Integer getOrdering() {
        return this.ordering;
    }

    public Parameter ordering(Integer ordering) {
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

    public Parameter setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Model> getModels() {
        return this.models;
    }

    public void setModels(Set<Model> models) {
        if (this.models != null) {
            this.models.forEach(i -> i.setParameters(null));
        }
        if (models != null) {
            models.forEach(i -> i.setParameters(this));
        }
        this.models = models;
    }

    public Parameter models(Set<Model> models) {
        this.setModels(models);
        return this;
    }

    public Parameter addModel(Model model) {
        this.models.add(model);
        model.setParameters(this);
        return this;
    }

    public Parameter removeModel(Model model) {
        this.models.remove(model);
        model.setParameters(null);
        return this;
    }

    public Set<ParameterType> getTypes() {
        return this.types;
    }

    public void setTypes(Set<ParameterType> parameterTypes) {
        if (this.types != null) {
            this.types.forEach(i -> i.setParameter(null));
        }
        if (parameterTypes != null) {
            parameterTypes.forEach(i -> i.setParameter(this));
        }
        this.types = parameterTypes;
    }

    public Parameter types(Set<ParameterType> parameterTypes) {
        this.setTypes(parameterTypes);
        return this;
    }

    public Parameter addType(ParameterType parameterType) {
        this.types.add(parameterType);
        parameterType.setParameter(this);
        return this;
    }

    public Parameter removeType(ParameterType parameterType) {
        this.types.remove(parameterType);
        parameterType.setParameter(null);
        return this;
    }

    public Set<ParameterDistributionType> getDistributions() {
        return this.distributions;
    }

    public void setDistributions(Set<ParameterDistributionType> parameterDistributionTypes) {
        if (this.distributions != null) {
            this.distributions.forEach(i -> i.setParameter(null));
        }
        if (parameterDistributionTypes != null) {
            parameterDistributionTypes.forEach(i -> i.setParameter(this));
        }
        this.distributions = parameterDistributionTypes;
    }

    public Parameter distributions(Set<ParameterDistributionType> parameterDistributionTypes) {
        this.setDistributions(parameterDistributionTypes);
        return this;
    }

    public Parameter addDistribution(ParameterDistributionType parameterDistributionType) {
        this.distributions.add(parameterDistributionType);
        parameterDistributionType.setParameter(this);
        return this;
    }

    public Parameter removeDistribution(ParameterDistributionType parameterDistributionType) {
        this.distributions.remove(parameterDistributionType);
        parameterDistributionType.setParameter(null);
        return this;
    }

    public ParameterTypeDefinition getDefinitions() {
        return this.definitions;
    }

    public void setDefinitions(ParameterTypeDefinition parameterTypeDefinition) {
        this.definitions = parameterTypeDefinition;
        this.definitionsId = parameterTypeDefinition != null ? parameterTypeDefinition.getId() : null;
    }

    public Parameter definitions(ParameterTypeDefinition parameterTypeDefinition) {
        this.setDefinitions(parameterTypeDefinition);
        return this;
    }

    public UUID getDefinitionsId() {
        return this.definitionsId;
    }

    public void setDefinitionsId(UUID parameterTypeDefinition) {
        this.definitionsId = parameterTypeDefinition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parameter)) {
            return false;
        }
        return getId() != null && getId().equals(((Parameter) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parameter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", enbled='" + getEnbled() + "'" +
            ", fixedValue='" + getFixedValue() + "'" +
            ", ordering=" + getOrdering() +
            "}";
    }
}
