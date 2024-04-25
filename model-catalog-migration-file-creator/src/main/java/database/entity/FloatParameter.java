package database.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A FloatParameter. */
@Entity
@Table(name = "float_parameter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloatParameter extends BaseTypeParameter {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "default_value")
  private Double defaultValue;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "floatParameter")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<FloatParameterRange> floatParameterRanges = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public FloatParameter() {}

  public Double getDefaultValue() {
    return this.defaultValue;
  }

  public FloatParameter defaultValue(Double defaultValue) {
    this.setDefaultValue(defaultValue);
    return this;
  }

  public void setDefaultValue(Double defaultValue) {
    this.defaultValue = defaultValue;
  }

  public Set<FloatParameterRange> getFloatParameterRanges() {
    return this.floatParameterRanges;
  }

  public void setFloatParameterRanges(Set<FloatParameterRange> floatParameterRanges) {
    if (this.floatParameterRanges != null) {
      this.floatParameterRanges.forEach(i -> i.setFloatParameter(null));
    }
    if (floatParameterRanges != null) {
      floatParameterRanges.forEach(i -> i.setFloatParameter(this));
    }
    this.floatParameterRanges = floatParameterRanges;
  }

  public FloatParameter floatParameterRanges(Set<FloatParameterRange> floatParameterRanges) {
    this.setFloatParameterRanges(floatParameterRanges);
    return this;
  }

  public FloatParameter addFloatParameterRange(FloatParameterRange floatParameterRange) {
    this.floatParameterRanges.add(floatParameterRange);
    floatParameterRange.setFloatParameter(this);
    return this;
  }

  public FloatParameter removeFloatParameterRange(FloatParameterRange floatParameterRange) {
    this.floatParameterRanges.remove(floatParameterRange);
    floatParameterRange.setFloatParameter(null);
    return this;
  }

  @Override
  public String toString() {
    return "FloatParameter{" + "defaultValue=" + defaultValue + '}';
  }
}
