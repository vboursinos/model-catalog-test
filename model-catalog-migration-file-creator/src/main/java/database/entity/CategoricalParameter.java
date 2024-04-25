package database.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** A CategoricalParameter. */
@Entity
@Table(name = "categorical_parameter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoricalParameter extends BaseTypeParameter {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "default_value")
  private String defaultValue;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "categoricalParameter")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<CategoricalParameterValue> categoricalParameterValues = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CategoricalParameter() {}

  public String getDefaultValue() {
    return this.defaultValue;
  }

  public CategoricalParameter defaultValue(String defaultValue) {
    this.setDefaultValue(defaultValue);
    return this;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public Set<CategoricalParameterValue> getCategoricalParameterValues() {
    return this.categoricalParameterValues;
  }

  public void setCategoricalParameterValues(
      Set<CategoricalParameterValue> categoricalParameterValues) {
    if (this.categoricalParameterValues != null) {
      this.categoricalParameterValues.forEach(i -> i.setCategoricalParameter(null));
    }
    if (categoricalParameterValues != null) {
      categoricalParameterValues.forEach(i -> i.setCategoricalParameter(this));
    }
    this.categoricalParameterValues = categoricalParameterValues;
  }

  public CategoricalParameter categoricalParameterValues(
      Set<CategoricalParameterValue> categoricalParameterValues) {
    this.setCategoricalParameterValues(categoricalParameterValues);
    return this;
  }

  public CategoricalParameter addCategoricalParameterValue(
      CategoricalParameterValue categoricalParameterValue) {
    this.categoricalParameterValues.add(categoricalParameterValue);
    categoricalParameterValue.setCategoricalParameter(this);
    return this;
  }

  public CategoricalParameter removeCategoricalParameterValue(
      CategoricalParameterValue categoricalParameterValue) {
    this.categoricalParameterValues.remove(categoricalParameterValue);
    categoricalParameterValue.setCategoricalParameter(null);
    return this;
  }

  @Override
  public String toString() {
    return "CategoricalParameter{" + "defaultValue='" + defaultValue + '\'' + '}';
  }
}
