package ai.turintech.modelcatalog.to;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.IntegerParameter} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameterTO implements Serializable {

    private Long id;

    private Integer defaultValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Integer defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegerParameterTO)) {
            return false;
        }

        IntegerParameterTO integerParameterDTO = (IntegerParameterTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, integerParameterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegerParameterDTO{" +
            "id=" + getId() +
            ", defaultValue=" + getDefaultValue() +
            "}";
    }
}
