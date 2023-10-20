package ai.turintech.modelcatalog.to;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.IntegerParameterValue} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameterValueTO implements Serializable {

    private Long id;

    //@NotNull(message = "must not be null")
    private Integer left;

    //@NotNull(message = "must not be null")
    private Integer right;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegerParameterValueTO)) {
            return false;
        }

        IntegerParameterValueTO integerParameterValueDTO = (IntegerParameterValueTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, integerParameterValueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegerParameterValueDTO{" +
            "id=" + getId() +
            ", left=" + getLeft() +
            ", right=" + getRight() +
            "}";
    }
}
