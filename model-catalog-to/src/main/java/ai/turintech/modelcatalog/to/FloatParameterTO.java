package ai.turintech.modelcatalog.to;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.FloatParameter} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloatParameterTO implements Serializable {

    private Long id;

    private Float defaultValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Float defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloatParameterTO)) {
            return false;
        }

        FloatParameterTO floatParameterDTO = (FloatParameterTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, floatParameterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloatParameterDTO{" +
            "id=" + getId() +
            ", defaultValue=" + getDefaultValue() +
            "}";
    }
}
