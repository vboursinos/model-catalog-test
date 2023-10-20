package ai.turintech.modelcatalog.to;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.ParameterDistributionType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterDistributionTypeTO implements Serializable {

    private UUID id;

    //@NotNull(message = "must not be null")
    private String name;

    private ParameterTO parameter;

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

    public ParameterTO getParameter() {
        return parameter;
    }

    public void setParameter(ParameterTO parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParameterDistributionTypeTO)) {
            return false;
        }

        ParameterDistributionTypeTO parameterDistributionTypeDTO = (ParameterDistributionTypeTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parameterDistributionTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParameterDistributionTypeDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", parameter=" + getParameter() +
            "}";
    }
}
