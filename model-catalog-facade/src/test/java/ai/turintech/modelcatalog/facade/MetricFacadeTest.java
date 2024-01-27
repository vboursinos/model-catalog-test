package ai.turintech.modelcatalog.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.turintech.modelcatalog.dto.MetricDTO;
import java.util.NoSuchElementException;
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
import reactor.test.StepVerifier;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class MetricFacadeTest extends BasicFacadeTest {

  private final String EXISTING_METRIC_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23";
  private final String NON_EXISTING_METRIC_ID = UUID.randomUUID().toString();

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

  private MetricDTO getPartialUpdatedMetricDTO() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"));
    metricDTO.setMetric("test_updated_metric_partial");
    return metricDTO;
  }

  @Test
  void testFindAllMetricFacade() {
    Flux<MetricDTO> metrics = metricFacade.findAll();
    StepVerifier.create(metrics.collectList())
        .assertNext(
            metricDTOList ->
                assertEquals(
                    4, metricDTOList.size(), "Returned metrics do not match expected size"))
        .verifyComplete();
  }

  @Test
  void testFindByIdMetricFacade() {
    Mono<MetricDTO> metric = metricFacade.findOne(UUID.fromString(EXISTING_METRIC_ID));

    StepVerifier.create(metric)
        .assertNext(metricDTO -> Assert.assertEquals("Metric1", metricDTO.getMetric()))
        .verifyComplete();
  }

  @Test
  void testExistsByIdMetricFacade() {
    Mono<Boolean> exists = metricFacade.existsById(UUID.fromString(EXISTING_METRIC_ID));

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdNonExistingMetricFacade() {
    Mono<Boolean> exists = metricFacade.existsById(UUID.fromString(NON_EXISTING_METRIC_ID));

    StepVerifier.create(exists).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveMetricFacade() {
    Mono<MetricDTO> savedMetric = metricFacade.save(getMetricDTO());
    StepVerifier.create(savedMetric)
        .assertNext(
            metricDTO -> {
              Assert.assertEquals(getMetricDTO().getMetric(), metricDTO.getMetric());
              metricFacade.delete(metricDTO.getId()).block();
            })
        .verifyComplete();
  }

  @Test
  void testUpdateMetricFacade() {
    Mono<MetricDTO> updatedMetric = metricFacade.update(getUpdatedMetricDTO());
    StepVerifier.create(updatedMetric)
        .assertNext(
            metricDTO ->
                Assert.assertEquals(getUpdatedMetricDTO().getMetric(), metricDTO.getMetric()))
        .verifyComplete();
  }

  @Test
  void testPartialUpdateMetricFacade() {

    Mono<MetricDTO> updatedMetric = metricFacade.partialUpdate(getPartialUpdatedMetricDTO());
    StepVerifier.create(updatedMetric)
        .assertNext(
            metricDTOPartial ->
                Assert.assertEquals(
                    getPartialUpdatedMetricDTO().getMetric(), metricDTOPartial.getMetric()))
        .verifyComplete();
  }

  @Test
  void testDeleteMetricFacade() {
    Mono<MetricDTO> savedMetric = metricFacade.save(getMetricDTO());

    StepVerifier.create(savedMetric)
        .assertNext(
            metricDTO -> {
              StepVerifier.create(metricFacade.delete(metricDTO.getId()))
                  .expectNextCount(0)
                  .verifyComplete();

              StepVerifier.create(metricFacade.findOne(metricDTO.getId()))
                  .expectError(NoSuchElementException.class)
                  .verify();
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdNonExistingMetricFacade() {
    Mono<MetricDTO> metric = metricFacade.findOne(UUID.fromString(NON_EXISTING_METRIC_ID));

    StepVerifier.create(metric).expectError(NoSuchElementException.class).verify();
  }
}
