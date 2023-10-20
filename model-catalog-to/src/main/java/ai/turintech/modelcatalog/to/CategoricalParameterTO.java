package ai.turintech.modelcatalog.to;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.CategoricalParameter} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameterTO implements Serializable {

    private Long id;

    private String defaultValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoricalParameterTO)) {
            return false;
        }

        CategoricalParameterTO categoricalParameterDTO = (CategoricalParameterTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoricalParameterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoricalParameterDTO{" +
            "id=" + getId() +
            ", defaultValue='" + getDefaultValue() + "'" +
            "}";
    }
}
