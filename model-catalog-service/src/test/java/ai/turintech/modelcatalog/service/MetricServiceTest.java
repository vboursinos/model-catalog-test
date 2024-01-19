package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.MetricDTO;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
  void testFindByIdForExistingId() {
    UUID existingId = UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23");
    Mono<MetricDTO> metric = metricService.findOne(existingId);

    StepVerifier.create(metric)
        .expectNextMatches(
            metricDTO -> {
              System.out.println("Found Metric by ID: " + metricDTO);
              Assert.assertEquals(existingId, metricDTO.getId());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.randomUUID(); // Assuming this ID does not exist in your data
    Mono<MetricDTO> metric = metricService.findOne(nonExistingId);

    StepVerifier.create(metric)
        .expectError(NoSuchElementException.class) // Expect a NoSuchElementException
        .verify();
  }

  @Test
  void testExistsByIdForExistingId() {
    // Test case when the ID exists
    UUID existingId = UUID.fromString("1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23");
    Mono<Boolean> existsForExistingId = metricService.existsById(existingId);

    StepVerifier.create(existsForExistingId).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdForNonExistingId() {
    // Test case when the ID does not exist
    UUID nonExistingId = UUID.randomUUID(); // Assuming this ID does not exist in your data
    Mono<Boolean> existsForNonExistingId = metricService.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveAndDeleteMetricService() {
    Mono<MetricDTO> savedMetric = metricService.save(getMetricDTO());

    StepVerifier.create(savedMetric)
        .expectNextMatches(
            metricDTO -> {
              System.out.println("Saved Metric: " + metricDTO);
              Assert.assertEquals(getMetricDTO().getMetric(), metricDTO.getMetric());
              return true;
            })
        .verifyComplete();

    // Verify deletion
    Mono<Void> deletion = savedMetric.flatMap(metricDTO -> metricService.delete(metricDTO.getId()));

    StepVerifier.create(deletion).verifyComplete();
  }

  @Test
  void testUpdateMetricService() {
    Mono<MetricDTO> updatedMetric = metricService.save(getUpdatedMetricDTO());

    StepVerifier.create(updatedMetric)
        .expectNextMatches(
            metricDTO -> {
              System.out.println("Updated Metric: " + metricDTO);
              Assert.assertEquals(getUpdatedMetricDTO().getMetric(), metricDTO.getMetric());
              return true;
            })
        .verifyComplete();
  }
}
