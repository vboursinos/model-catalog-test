package ai.turintech.modelcatalog.dto;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.FloatParameterRange} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloatParameterRangeDTO implements Serializable {

    private Long id;

    //@NotNull(message = "must not be null")
    private Boolean isLeftOpen;

    //@NotNull(message = "must not be null")
    private Boolean isRightOpen;

    //@NotNull(message = "must not be null")
    private Float left;

    //@NotNull(message = "must not be null")
    private Float right;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsLeftOpen() {
        return isLeftOpen;
    }

    public void setIsLeftOpen(Boolean isLeftOpen) {
        this.isLeftOpen = isLeftOpen;
    }

    public Boolean getIsRightOpen() {
        return isRightOpen;
    }

    public void setIsRightOpen(Boolean isRightOpen) {
        this.isRightOpen = isRightOpen;
    }

    public Float getLeft() {
        return left;
    }

    public void setLeft(Float left) {
        this.left = left;
    }

    public Float getRight() {
        return right;
    }

    public void setRight(Float right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloatParameterRangeDTO)) {
            return false;
        }

        FloatParameterRangeDTO floatParameterRangeDTO = (FloatParameterRangeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, floatParameterRangeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloatParameterRangeDTO{" +
            "id=" + getId() +
            ", isLeftOpen='" + getIsLeftOpen() + "'" +
            ", isRightOpen='" + getIsRightOpen() + "'" +
            ", left=" + getLeft() +
            ", right=" + getRight() +
            "}";
    }
}
