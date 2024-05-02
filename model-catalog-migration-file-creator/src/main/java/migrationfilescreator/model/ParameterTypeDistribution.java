package migrationfilescreator.model;

public class ParameterTypeDistribution {
  private String parameterType;
  private String parameterDistribution;
  private String parameterName;

  private FloatSet floatSet;

  private IntegerSet integerSet;

  private CategoricalSet categoricalSet;

  private Object defaultValue;

  public ParameterTypeDistribution() {}

  public ParameterTypeDistribution(String parameterType, String parameterDistribution) {
    this.parameterType = parameterType;
    this.parameterDistribution = parameterDistribution;
  }

  public ParameterTypeDistribution(
      String parameterType, String parameterDistribution, String parameterName) {
    this.parameterType = parameterType;
    this.parameterDistribution = parameterDistribution;
    this.parameterName = parameterName;
  }

  public ParameterTypeDistribution(
      String parameterType,
      String parameterDistribution,
      String parameterName,
      FloatSet floatSet,
      Object defaultValue) {
    this.parameterType = parameterType;
    this.parameterDistribution = parameterDistribution;
    this.parameterName = parameterName;
    this.floatSet = floatSet;
    this.defaultValue = defaultValue;
  }

  public ParameterTypeDistribution(
      String parameterType,
      String parameterDistribution,
      String parameterName,
      IntegerSet integerSet,
      Object defaultValue) {
    this.parameterType = parameterType;
    this.parameterDistribution = parameterDistribution;
    this.parameterName = parameterName;
    this.integerSet = integerSet;
    this.defaultValue = defaultValue;
  }

  public ParameterTypeDistribution(
      String parameterType,
      String parameterDistribution,
      String parameterName,
      CategoricalSet categoricalSet,
      Object defaultValue) {
    this.parameterType = parameterType;
    this.parameterDistribution = parameterDistribution;
    this.parameterName = parameterName;
    this.categoricalSet = categoricalSet;
    this.defaultValue = defaultValue;
  }

  public String getParameterType() {
    return parameterType;
  }

  public void setParameterType(String parameterType) {
    this.parameterType = parameterType;
  }

  public String getParameterDistribution() {
    return parameterDistribution;
  }

  public void setParameterDistribution(String parameterDistribution) {
    this.parameterDistribution = parameterDistribution;
  }

  public String getParameterName() {
    return parameterName;
  }

  public void setParameterName(String parameterName) {
    this.parameterName = parameterName;
  }

  public FloatSet getFloatSet() {
    return floatSet;
  }

  public void setFloatSet(FloatSet floatSet) {
    this.floatSet = floatSet;
  }

  public IntegerSet getIntegerSet() {
    return integerSet;
  }

  public void setIntegerSet(IntegerSet integerSet) {
    this.integerSet = integerSet;
  }

  public CategoricalSet getCategoricalSet() {
    return categoricalSet;
  }

  public void setCategoricalSet(CategoricalSet categoricalSet) {
    this.categoricalSet = categoricalSet;
  }

  public Object getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }
}
