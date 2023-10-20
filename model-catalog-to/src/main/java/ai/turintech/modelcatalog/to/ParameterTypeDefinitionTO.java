package ai.turintech.modelcatalog.to;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.ParameterTypeDefinition} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterTypeDefinitionTO implements Serializable {

    private UUID id;

    //@NotNull(message = "must not be null")
    private Integer ordering;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParameterTypeDefinitionTO)) {
            return false;
        }

        ParameterTypeDefinitionTO parameterTypeDefinitionDTO = (ParameterTypeDefinitionTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parameterTypeDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParameterTypeDefinitionDTO{" +
            "id='" + getId() + "'" +
            ", ordering=" + getOrdering() +
            "}";
    }
}
