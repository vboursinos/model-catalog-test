package ai.turintech.modelcatalog.to;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.ParameterType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParameterTypeTO implements Serializable {

    private static final long serialVersionUID = -6017445921327931645L;

	private UUID id;

    //@NotNull(message = "must not be null")
    private String name;

    private ParameterTO parameter;

    private ParameterTypeDefinitionTO parameterTypeDefinition;

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

    public ParameterTypeDefinitionTO getParameterTypeDefinition() {
        return parameterTypeDefinition;
    }

    public void setParameterTypeDefinition(ParameterTypeDefinitionTO parameterTypeDefinition) {
        this.parameterTypeDefinition = parameterTypeDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParameterTypeTO)) {
            return false;
        }

        ParameterTypeTO parameterTypeDTO = (ParameterTypeTO) o;
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
