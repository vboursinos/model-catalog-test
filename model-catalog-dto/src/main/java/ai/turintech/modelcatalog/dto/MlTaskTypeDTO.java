package ai.turintech.modelcatalog.dto;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.MlTaskType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MlTaskTypeDTO implements Serializable {

    private UUID id;

    //@NotNull(message = "must not be null")
    private String name;

    private ModelDTO models;

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

    public ModelDTO getModels() {
        return models;
    }

    public void setModels(ModelDTO models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MlTaskTypeDTO)) {
            return false;
        }

        MlTaskTypeDTO mlTaskTypeDTO = (MlTaskTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mlTaskTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MlTaskTypeDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", models=" + getModels() +
            "}";
    }
}
