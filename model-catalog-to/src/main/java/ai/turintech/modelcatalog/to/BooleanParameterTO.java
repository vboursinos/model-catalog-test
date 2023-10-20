package ai.turintech.modelcatalog.to;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TO for the {@link ai.turintech.catalog.domain.BooleanParameter} resource.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BooleanParameterTO implements Serializable {

    private Long id;

    private Boolean defaultValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BooleanParameterTO)) {
            return false;
        }

        BooleanParameterTO booleanParameterDTO = (BooleanParameterTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, booleanParameterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BooleanParameterDTO{" +
            "id=" + getId() +
            ", defaultValue='" + getDefaultValue() + "'" +
            "}";
    }
}
