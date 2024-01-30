package ai.turintech.modelcatalog.todtomapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.to.MetricTO;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@ContextConfiguration(classes = TestMapperConfig.class)
public class MetricMapperTest {

  @Autowired private MetricMapper metricMapper;

  @Test
  public void testToTO() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.randomUUID());
    metricDTO.setMetric("Accuracy");

    MetricTO metric = metricMapper.to(metricDTO);

    assertEquals(metricDTO.getId(), metric.getId());
    assertEquals(metricDTO.getMetric(), metric.getMetric());
  }

  @Test
  public void testToDTO() {
    MetricTO metricTO = new MetricTO();
    metricTO.setId(UUID.randomUUID());
    metricTO.setMetric("Precision");

    MetricDTO metricDTO = metricMapper.from(metricTO);

    assertEquals(metricTO.getId(), metricDTO.getId());
    assertEquals(metricTO.getMetric(), metricDTO.getMetric());
  }

  @Test
  public void testPartialUpdate() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.randomUUID());
    metricDTO.setMetric("Recall");

    MetricTO metricTO = new MetricTO();
    metricTO.setId(metricDTO.getId());
    metricTO.setMetric("F1 Score");

    metricMapper.partialUpdate(metricDTO, metricTO);

    assertEquals(metricDTO.getMetric(), metricTO.getMetric());
  }
}
