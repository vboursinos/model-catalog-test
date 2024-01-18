package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.MetricDTO;
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
    metricDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"));
    metricDTO.setMetric("test_updated_metric");
    return metricDTO;
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
              assertEquals(4, metricDTOList.size(), "Returned metrics do not match expected size");
            });
  }

  @Test
  void testFindByIdMetricFacade() {
    Mono<MetricDTO> metric =
        metricFacade.findOne(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23"));

    metric.subscribe(
        metricDTO -> {
          System.out.println(metricDTO.getMetric());
          Assert.assertEquals("Metric1", metricDTO.getMetric());
        });
  }

  @Test
  void testExistsByIdMetricFacade() {
    Mono<Boolean> exists =
        metricFacade.existsById(UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23"));

    exists.subscribe(
        metricDTO -> {
          Assert.assertEquals(true, exists.block());
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
  void testUpdateMetricFacade() {
    Mono<MetricDTO> updatedMetric = metricFacade.save(getUpdatedMetricDTO());
    updatedMetric.subscribe(
        metricDTO -> {
          Assert.assertEquals(getUpdatedMetricDTO().getMetric(), metricDTO.getMetric());
        });
  }
}
