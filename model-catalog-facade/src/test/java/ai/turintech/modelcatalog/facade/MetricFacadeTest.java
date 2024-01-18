package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.MetricDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class MetricFacadeTest extends BasicFacadeTest {
  @Autowired private MetricFacade metricFacade;

  private MetricDTO getMetricDTO() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setMetric("test_metric");
    return metricDTO;
  }

  private MetricDTO getUpdatedMetricDTO() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23"));
    metricDTO.setMetric("test_updated_metric");
    return metricDTO;
  }

  private List<MetricDTO> getExpectedMetrics() {
    List<MetricDTO> expectedMetrics = new ArrayList<>();
    MetricDTO metricDTO1 = new MetricDTO();
    metricDTO1.setId(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23"));
    metricDTO1.setMetric("Metric1");
    MetricDTO metricDTO2 = new MetricDTO();
    metricDTO2.setId(UUID.fromString("2b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d24"));
    metricDTO2.setMetric("Metric2");
    MetricDTO metricDTO3 = new MetricDTO();
    metricDTO3.setId(UUID.fromString("3b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d25"));
    metricDTO3.setMetric("Metric3");
    MetricDTO metricDTO4 = new MetricDTO();
    metricDTO4.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"));
    metricDTO4.setMetric("Metric4");
    expectedMetrics.add(metricDTO1);
    expectedMetrics.add(metricDTO2);
    expectedMetrics.add(metricDTO3);
    expectedMetrics.add(metricDTO4);
    return expectedMetrics;
  }

  @Test
  void testFindAllMetricFacade() {
    // Assuming metricFacade is an instance of MetricFacade
    Flux<MetricDTO> metrics = metricFacade.findAll();
    metrics
        .collectList()
        .blockOptional()
        .ifPresent(
            metricDTOList -> {
              assertEquals(
                  getExpectedMetrics(),
                  metricDTOList,
                  "Returned metrics do not match expected values");
            });
  }

  @Test
  void testFindByIdMetricFacade() {
    Mono<MetricDTO> metric =
        metricFacade.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23"));

    metric.subscribe(
        metricDTO -> {
          Assert.assertEquals("Metric1", metricDTO.getMetric());
        });
  }

  @Test
  void testExistsByIdMetricFacade() {
    Mono<Boolean> exists =
        metricFacade.existsById(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23"));

    exists.subscribe(
        metricDTO -> {
          Assert.assertEquals(true, exists);
        });
  }

  @Test
  void testSaveMetricFacade() {
    Mono<MetricDTO> savedMetric = metricFacade.save(getMetricDTO());
    savedMetric.subscribe(
        metricDTO -> {
          Assert.assertEquals(getMetricDTO().getMetric(), metricDTO.getMetric());
          metricFacade.delete(metricDTO.getId()).block();
        });
  }

  @Test
  void testUpdateMetricService() {
    Mono<MetricDTO> updatedMetric = metricFacade.save(getUpdatedMetricDTO());
    updatedMetric.subscribe(
        metricDTO -> {
          Assert.assertEquals(getUpdatedMetricDTO().getMetric(), metricDTO.getMetric());
        });
  }
}
