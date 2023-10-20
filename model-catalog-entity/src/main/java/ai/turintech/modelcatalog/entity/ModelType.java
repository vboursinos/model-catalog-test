package ai.turintech.modelcatalog.entity;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;

/**
 * A ModelType.
 */
@Table("model_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelType implements Serializable, Persistable<UUID> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private UUID id;

    @NotNull(message = "must not be null")
    @Column("name")
    private String name;

    @Transient
    private boolean isPersisted;

    @Transient
    private Model models;

    @Column("models_id")
    private UUID modelsId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ModelType id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ModelType name(String name) {
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

    public ModelType setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Model getModels() {
        return this.models;
    }

    public void setModels(Model model) {
        this.models = model;
        this.modelsId = model != null ? model.getId() : null;
    }

    public ModelType models(Model model) {
        this.setModels(model);
        return this;
    }

    public UUID getModelsId() {
        return this.modelsId;
    }

    public void setModelsId(UUID model) {
        this.modelsId = model;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelType)) {
            return false;
        }
        return getId() != null && getId().equals(((ModelType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModelType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
