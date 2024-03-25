package ai.turintech.modelcatalog.dtoentitymapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class MetricMapperTest {

  @Autowired private MetricMapper metricMapper;

  @Test
  public void testToEntity() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.randomUUID());
    metricDTO.setMetric("Accuracy");

    Metric metric = metricMapper.from(metricDTO);

    assertEquals(metricDTO.getId(), metric.getId());
    assertEquals(metricDTO.getMetric(), metric.getMetric());
  }

  @Test
  public void testToDTO() {
    Metric metric = new Metric();
    metric.setId(UUID.randomUUID());
    metric.setMetric("Precision");

    MetricDTO metricDTO = metricMapper.to(metric);

    assertEquals(metric.getId(), metricDTO.getId());
    assertEquals(metric.getMetric(), metricDTO.getMetric());
  }

  @Test
  public void testPartialUpdate() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.randomUUID());
    metricDTO.setMetric("Recall");

    Metric metric = new Metric();
    metric.setId(metricDTO.getId());
    metric.setMetric("F1 Score");

    metricMapper.partialUpdate(metric, metricDTO);

    assertEquals(metricDTO.getMetric(), metric.getMetric());
  }
}
