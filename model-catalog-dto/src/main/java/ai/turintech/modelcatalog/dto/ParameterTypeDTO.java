package ai.turintech.modelcatalog.dto;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.ParameterType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterTypeDTO implements Serializable {

    private static final long serialVersionUID = -6017445921327931645L;

	private UUID id;

    //@NotNull(message = "must not be null")
    private String name;

    private ParameterDTO parameter;

    private ParameterTypeDefinitionDTO parameterTypeDefinition;

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

    public ParameterDTO getParameter() {
        return parameter;
    }

    public void setParameter(ParameterDTO parameter) {
        this.parameter = parameter;
    }

    public ParameterTypeDefinitionDTO getParameterTypeDefinition() {
        return parameterTypeDefinition;
    }

    public void setParameterTypeDefinition(ParameterTypeDefinitionDTO parameterTypeDefinition) {
        this.parameterTypeDefinition = parameterTypeDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParameterTypeDTO)) {
            return false;
        }

        ParameterTypeDTO parameterTypeDTO = (ParameterTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parameterTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParameterTypeDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", parameter=" + getParameter() +
            ", parameterTypeDefinition=" + getParameterTypeDefinition() +
            "}";
    }
}
