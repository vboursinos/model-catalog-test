package ai.turintech.modelcatalog.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameterTO implements Serializable {

    private UUID parameterTypeDefinitionId;

    private Integer defaultValue;

    private List<IntegerParameterValueTO> integerParameterValues = new ArrayList<>();

    public UUID getParameterTypeDefinitionId() {
        return parameterTypeDefinitionId;
    }

    public void setParameterTypeDefinitionId(UUID parameterTypeDefinitionId) {
        this.parameterTypeDefinitionId = parameterTypeDefinitionId;
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Integer defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<IntegerParameterValueTO> getIntegerParameterValues() {
        return integerParameterValues;
    }

    public void setIntegerParameterValues(List<IntegerParameterValueTO> integerParameterValues) {
        this.integerParameterValues = integerParameterValues;
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
        if (this.parameterTypeDefinitionId == null) {
            return false;
        }
        return Objects.equals(this.parameterTypeDefinitionId, integerParameterDTO.parameterTypeDefinitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.parameterTypeDefinitionId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegerParameterDTO{" +
            "parameterTypeDefinitionId" + getParameterTypeDefinitionId() +
            ", defaultValue=" + getDefaultValue() +
            "}";
    }
}
