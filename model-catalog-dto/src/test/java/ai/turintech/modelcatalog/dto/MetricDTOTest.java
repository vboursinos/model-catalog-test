package ai.turintech.modelcatalog.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MetricDTOTest {

  private MetricDTO metricDTO;

  @BeforeAll
  public void setUp() {
    metricDTO = new MetricDTO();
    metricDTO.setId(UUID.randomUUID());
    metricDTO.setMetric("TestMetric");
  }

  @Test
  public void testEquals() {
    MetricDTO sameIdMetricDTO = new MetricDTO();
    sameIdMetricDTO.setId(metricDTO.getId());

    MetricDTO differentIdMetricDTO = new MetricDTO();
    differentIdMetricDTO.setId(UUID.randomUUID());

    assertEquals(metricDTO, sameIdMetricDTO);
    assertNotEquals(metricDTO, differentIdMetricDTO);
    assertNotEquals(metricDTO, null);
  }

  @Test
  public void testHashCode() {
    MetricDTO sameIdMetricDTO = new MetricDTO();
    sameIdMetricDTO.setId(metricDTO.getId());

    MetricDTO differentIdMetricDTO = new MetricDTO();
    differentIdMetricDTO.setId(UUID.randomUUID());

    assertEquals(metricDTO.hashCode(), sameIdMetricDTO.hashCode());
    assertNotEquals(metricDTO.hashCode(), differentIdMetricDTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString = "MetricDTO{id='" + metricDTO.getId() + "', metric='TestMetric'}";
    assertEquals(expectedToString, metricDTO.toString());
  }

  @Test
  public void testGetSetMetric() {
    MetricDTO newMetricDTO = new MetricDTO();
    newMetricDTO.setMetric("NewTestMetric");

    assertEquals("NewTestMetric", newMetricDTO.getMetric());
  }

  @Test
  public void testEqualsDifferentClass() {
    Object differentObject = new Object();

    assertFalse(metricDTO.equals(differentObject));
  }
}
