package ai.turintech.modelcatalog.entity;

//import jakarta.validation.constraints.*;
import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;

/**
 * A IntegerParameterValue.
 */
@Table("integer_parameter_value")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegerParameterValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("jhi_left")
    private Integer left;

    @NotNull(message = "must not be null")
    @Column("jhi_right")
    private Integer right;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntegerParameterValue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLeft() {
        return this.left;
    }

    public IntegerParameterValue left(Integer left) {
        this.setLeft(left);
        return this;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getRight() {
        return this.right;
    }

    public IntegerParameterValue right(Integer right) {
        this.setRight(right);
        return this;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegerParameterValue)) {
            return false;
        }
        return getId() != null && getId().equals(((IntegerParameterValue) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegerParameterValue{" +
            "id=" + getId() +
            ", left=" + getLeft() +
            ", right=" + getRight() +
            "}";
    }
}
