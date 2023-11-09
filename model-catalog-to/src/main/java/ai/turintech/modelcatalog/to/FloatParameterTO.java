package ai.turintech.modelcatalog.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloatParameterTO implements Serializable {

    private UUID parameterTypeDefinitionId;

    private Double defaultValue;

    private List<FloatParameterRangeTO> floatParameterRanges = new ArrayList<>();

    public Double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public UUID getParameterTypeDefinitionId() {
        return parameterTypeDefinitionId;
    }

    public void setParameterTypeDefinitionId(UUID parameterTypeDefinitionId) {
        this.parameterTypeDefinitionId = parameterTypeDefinitionId;
    }

    public List<FloatParameterRangeTO> getFloatParameterRanges() {
        return floatParameterRanges;
    }

    public void setFloatParameterRanges(List<FloatParameterRangeTO> floatParameterRanges) {
        this.floatParameterRanges = floatParameterRanges;
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
        if (this.parameterTypeDefinitionId == null) {
            return false;
        }
        return Objects.equals(this.parameterTypeDefinitionId, floatParameterDTO.parameterTypeDefinitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.parameterTypeDefinitionId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloatParameterDTO{" +
            "parameterTypeDefinitionId=" + getParameterTypeDefinitionId() +
            ", defaultValue=" + getDefaultValue() +
            "}";
    }
}
