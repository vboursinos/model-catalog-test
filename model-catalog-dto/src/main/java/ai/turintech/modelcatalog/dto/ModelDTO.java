package ai.turintech.modelcatalog.dto;

//import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link ai.turintech.catalog.domain.Model} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModelDTO implements Serializable {

    private UUID id;

    //@NotNull(message = "must not be null")
    private String name;

    //@NotNull(message = "must not be null")
    private String displayName;

    private String description;

    private String advantages;

    private String disadvantages;

    //@NotNull(message = "must not be null")
    private Boolean enabled;

    //@NotNull(message = "must not be null")
    private Boolean decistionTree;

    private Set<ModelGroupTypeDTO> groups = new HashSet<>();

    private Set<MetricDTO> incompatibleMetrics = new HashSet<>();

    private ParameterDTO parameters;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdvantages() {
        return advantages;
    }

    public void setAdvantages(String advantages) {
        this.advantages = advantages;
    }

    public String getDisadvantages() {
        return disadvantages;
    }

    public void setDisadvantages(String disadvantages) {
        this.disadvantages = disadvantages;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getDecistionTree() {
        return decistionTree;
    }

    public void setDecistionTree(Boolean decistionTree) {
        this.decistionTree = decistionTree;
    }

    public Set<ModelGroupTypeDTO> getGroups() {
        return groups;
    }

    public void setGroups(Set<ModelGroupTypeDTO> groups) {
        this.groups = groups;
    }

    public Set<MetricDTO> getIncompatibleMetrics() {
        return incompatibleMetrics;
    }

    public void setIncompatibleMetrics(Set<MetricDTO> incompatibleMetrics) {
        this.incompatibleMetrics = incompatibleMetrics;
    }

    public ParameterDTO getParameters() {
        return parameters;
    }

    public void setParameters(ParameterDTO parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelDTO)) {
            return false;
        }

        ModelDTO modelDTO = (ModelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, modelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModelDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", description='" + getDescription() + "'" +
            ", advantages='" + getAdvantages() + "'" +
            ", disadvantages='" + getDisadvantages() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", decistionTree='" + getDecistionTree() + "'" +
            ", groups=" + getGroups() +
            ", incompatibleMetrics=" + getIncompatibleMetrics() +
            ", parameters=" + getParameters() +
            "}";
    }
}
