package ai.turintech.modelcatalog.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ai.turintech.components.architecture.callable.impl.reactive.ReactiveAbstractUUIDIdentityCrudCallableImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.entity.Metric;
import ai.turintech.modelcatalog.repository.MetricRepository;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class MetricServiceTest {

  // String IDs for testing
  private static final String EXISTING_METRIC_ID = "1b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d23";
  private static final String NON_EXISTING_METRIC_ID = "123e4567-e89b-12d3-a456-426614174099";

  private static final String EXISTING_METRIC_ID_FOR_UPDATE =
      "4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26";

  private static final String METRIC_ID_SAVE_RESPONSE = "123e4567-e89b-12d3-a456-426614174099";
  @Mock private ApplicationContext context;
  @Mock private MetricRepository repository;
  @Mock private MapperInterface<MetricDTO, Metric> mapperInterface;
  @InjectMocks private MetricServiceImpl metricServiceImpl;

  private MetricDTO getMetricDTO() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setMetric("test_metric");
    return metricDTO;
  }

  private MetricDTO getUpdatedMetricDTO() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.fromString(EXISTING_METRIC_ID_FOR_UPDATE));
    metricDTO.setMetric("test_updated_metric");
    return metricDTO;
  }

  private MetricDTO getMetricDTOSaveResponse() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.fromString(METRIC_ID_SAVE_RESPONSE));
    metricDTO.setMetric("test_metric");
    return metricDTO;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ReflectionTestUtils.setField(metricServiceImpl, "context", context);
    ReflectionTestUtils.setField(metricServiceImpl, "jdbcScheduler", Schedulers.immediate());
    ReflectionTestUtils.setField(metricServiceImpl, "repository", repository);
    ReflectionTestUtils.setField(metricServiceImpl, "mapperInterface", mapperInterface);
  }

  @Test
  void testFindAllMetricService() {

    List<MetricDTO> metricDTOList = Arrays.asList(getMetricDTO());

    ReactiveAbstractUUIDIdentityCrudCallableImpl<List<MetricDTO>, MetricDTO, Metric> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);

    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findAll"),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(metricDTOList);

    Mono<List<MetricDTO>> metricListMono = metricServiceImpl.findAll();
    StepVerifier.create(metricListMono).expectNext(metricDTOList).verifyComplete();
  }

  @Test
  void testFindByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_METRIC_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<MetricDTO, MetricDTO, Metric> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenReturn(getMetricDTO());

    Mono<MetricDTO> metricDTOMono = metricServiceImpl.findOne(existingId);

    StepVerifier.create(metricDTOMono)
        .expectNextMatches(
            metricDTO -> {
              Assert.assertEquals("test_metric", metricDTO.getMetric());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testFindByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_METRIC_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<MetricDTO, MetricDTO, Metric> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("findById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    when(callable.call()).thenThrow(NoSuchElementException.class);

    Mono<MetricDTO> metricDTOMono = metricServiceImpl.findOne(nonExistingId);

    StepVerifier.create(metricDTOMono).expectError(NoSuchElementException.class).verify();
  }

  @Test
  void testExistsByIdForExistingId() {
    UUID existingId = UUID.fromString(EXISTING_METRIC_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, MetricDTO, Metric> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(true);

    Mono<Boolean> exists = metricServiceImpl.existsById(existingId);

    StepVerifier.create(exists).expectNext(true).verifyComplete();
  }

  @Test
  void testExistsByIdForNonExistingId() {
    UUID nonExistingId = UUID.fromString(NON_EXISTING_METRIC_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Boolean, MetricDTO, Metric> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("existsById"),
            eq(nonExistingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(false);

    Mono<Boolean> existsForNonExistingId = metricServiceImpl.existsById(nonExistingId);

    StepVerifier.create(existsForNonExistingId).expectNext(false).verifyComplete();
  }

  @Test
  void testSaveMetricService() {
    ReactiveAbstractUUIDIdentityCrudCallableImpl<MetricDTO, MetricDTO, Metric> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("create"),
            eq(getMetricDTOSaveResponse()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getMetricDTOSaveResponse());

    Mono<MetricDTO> savedMetric = metricServiceImpl.save(getMetricDTOSaveResponse());

    StepVerifier.create(savedMetric)
        .expectNextMatches(
            metricDTO -> {
              Assert.assertEquals(getMetricDTOSaveResponse().getMetric(), metricDTO.getMetric());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testUpdateMetricService() {
    ReactiveAbstractUUIDIdentityCrudCallableImpl<MetricDTO, MetricDTO, Metric> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("update"),
            eq(getUpdatedMetricDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedMetricDTO());

    Mono<MetricDTO> updatedMetric = metricServiceImpl.update(getUpdatedMetricDTO());

    StepVerifier.create(updatedMetric)
        .expectNextMatches(
            metricDTO -> {
              Assert.assertEquals(getUpdatedMetricDTO().getMetric(), metricDTO.getMetric());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testPartialUpdateMetricService() {

    ReactiveAbstractUUIDIdentityCrudCallableImpl<MetricDTO, MetricDTO, Metric> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("partialUpdate"),
            eq(getUpdatedMetricDTO()),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);

    when(callable.call()).thenReturn(getUpdatedMetricDTO());

    Mono<MetricDTO> updatedMetric = metricServiceImpl.partialUpdate(getUpdatedMetricDTO());

    StepVerifier.create(updatedMetric)
        .expectNextMatches(
            metricDTO -> {
              Assert.assertEquals(getUpdatedMetricDTO().getMetric(), metricDTO.getMetric());
              return true;
            })
        .verifyComplete();
  }

  @Test
  void testDeleteMetricService() {
    UUID existingId = UUID.fromString(EXISTING_METRIC_ID);

    ReactiveAbstractUUIDIdentityCrudCallableImpl<Void, MetricDTO, Metric> callable =
        mock(ReactiveAbstractUUIDIdentityCrudCallableImpl.class);
    when(context.getBean(
            eq(ReactiveAbstractUUIDIdentityCrudCallableImpl.class),
            eq("delete"),
            eq(existingId),
            eq(repository),
            eq(mapperInterface)))
        .thenReturn(callable);
    metricServiceImpl.delete(existingId);
  }
}
