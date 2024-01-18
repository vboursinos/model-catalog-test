package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.MetricDTO;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class MetricServiceTest extends BasicServiceTest {
  @Autowired private MetricService metricService;

  private MetricDTO getMetricDTO() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setMetric("test_metric");
    return metricDTO;
  }

  private MetricDTO getUpdatedMetricDTO() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"));
    metricDTO.setMetric("test_updated_metric");
    return metricDTO;
  }

  @Test
  void testFindAllMetricService() {
    Mono<List<MetricDTO>> metrics = metricService.findAll();

    List<MetricDTO> metricDTOList = metrics.block();

    Assert.assertNotNull(metricDTOList);
    Assert.assertEquals(4, metricDTOList.size());
    Assert.assertEquals("Metric1", metricDTOList.get(0).getMetric());
  }

  @Test
  void testFindByIdMetricService() {
    Mono<MetricDTO> metric =
        metricService.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23"));

    metric.subscribe(
        metricDTO -> {
          System.out.println("Metric: " + metricDTO);
          Assert.assertEquals("Metric1", metricDTO.getMetric());
        });
  }

  @Test
  void testSaveMetricService() {
    Mono<MetricDTO> savedMetric = metricService.save(getMetricDTO());
    savedMetric.subscribe(
        metricDTO -> {
          System.out.println("Saved Metric: " + metricDTO);
          Assert.assertEquals(getMetricDTO().getMetric(), metricDTO.getMetric());
          metricService.delete(metricDTO.getId()).block();
        });
  }

  @Test
  void testUpdateMetricService() {
    Mono<MetricDTO> updatedMetric = metricService.save(getUpdatedMetricDTO());
    updatedMetric.subscribe(
        metricDTO -> {
          System.out.println("Updated Metric: " + metricDTO);
          Assert.assertEquals(getUpdatedMetricDTO().getMetric(), metricDTO.getMetric());
        });
  }
}
