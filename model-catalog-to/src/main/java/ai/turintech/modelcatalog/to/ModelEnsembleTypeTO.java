package ai.turintech.modelcatalog.to;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.ModelEnsembleType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelEnsembleTypeTO implements Serializable {

    private UUID id;

    //@NotNull(message = "must not be null")
    private String name;

    private ModelTO models;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelTO getModels() {
        return models;
    }

    public void setModels(ModelTO models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelEnsembleTypeTO)) {
            return false;
        }

        ModelEnsembleTypeTO modelEnsembleTypeDTO = (ModelEnsembleTypeTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, modelEnsembleTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModelEnsembleTypeDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", models=" + getModels() +
            "}";
    }
}
