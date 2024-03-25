package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MetricTOTest {

  private MetricTO metricTO;

  @BeforeEach
  public void setUp() {
    metricTO = new MetricTO();
    metricTO.setId(UUID.randomUUID());
    metricTO.setMetric("TestMetric");
  }

  @Test
  public void testEquals() {
    MetricTO sameIdMetricTO = new MetricTO();
    sameIdMetricTO.setId(metricTO.getId());
    sameIdMetricTO.setMetric("TestMetric");

    MetricTO differentIdMetricTO = new MetricTO();
    differentIdMetricTO.setId(UUID.randomUUID());
    differentIdMetricTO.setMetric("DifferentTestMetric");

    assertEquals(metricTO, sameIdMetricTO);
    assertNotEquals(metricTO, differentIdMetricTO);
    assertNotEquals(metricTO, null);
  }

  @Test
  public void testHashCode() {
    MetricTO sameIdMetricTO = new MetricTO();
    sameIdMetricTO.setId(metricTO.getId());
    sameIdMetricTO.setMetric("TestMetric");

    MetricTO differentIdMetricTO = new MetricTO();
    differentIdMetricTO.setId(UUID.randomUUID());
    differentIdMetricTO.setMetric("DifferentTestMetric");

    assertEquals(metricTO.hashCode(), sameIdMetricTO.hashCode());
    assertNotEquals(metricTO.hashCode(), differentIdMetricTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "MetricDTO{" + "id='" + metricTO.getId() + "', metric='" + metricTO.getMetric() + "'" + "}";
    assertEquals(expectedToString, metricTO.toString());
  }

  @Test
  public void testGetSetMethods() {
    assertEquals("TestMetric", metricTO.getMetric());

    metricTO.setMetric("UpdatedTestMetric");

    assertEquals("UpdatedTestMetric", metricTO.getMetric());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(metricTO.equals(differentObject));
  }
}
