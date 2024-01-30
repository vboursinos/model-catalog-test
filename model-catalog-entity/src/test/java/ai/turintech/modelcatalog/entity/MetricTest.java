package ai.turintech.modelcatalog.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MetricTest {

  private Metric metric;

  @BeforeEach
  public void setUp() {
    metric = new Metric();
  }

  @Test
  public void testMetric() {
    String expectedMetric = "TestMetric";
    metric.metric(expectedMetric);

    String actualMetric = metric.getMetric();

    assertNotNull(actualMetric, "Expected metric to be not null");
    assertEquals(expectedMetric, actualMetric, "Expected and actual metrics should be the same");
  }

  @Test
  public void testSetAndGetModels() {
    Set<Model> modelSet = new HashSet<>();
    Model model = new Model();
    modelSet.add(model);

    metric.setModels(modelSet);

    Set<Model> actualValue = metric.getModels();

    assertNotNull(actualValue, "Expected actual value to not be null");
    assertEquals(modelSet, actualValue, "Expected and actual values should be equal");
  }

  @Test
  public void testAddModel() {
    Model model = new Model();

    metric.addModel(model);

    assertTrue(metric.getModels().contains(model), "Added Model should be present");
  }

  @Test
  public void testRemoveModel() {
    Model model = new Model();
    metric.addModel(model);
    metric.removeModel(model);

    assertFalse(metric.getModels().contains(model), "Removed Model should not be present");
  }

  @Test
  public void testToString() {
    String metricVal = "TestMetric";
    metric.metric(metricVal);

    String expectedString =
        "Metric{" + "id=" + metric.getId() + ", metric='" + metricVal + "'" + "}";

    assertEquals(expectedString, metric.toString(), "Expected and actual toString should be same");
  }
}
