package ai.turintech.modelcatalog.rest;

import static org.mockito.Mockito.*;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.to.MetricTO;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringJUnitConfig(TestRestConfig.class)
class MetricResourceRestTest extends BasicRestTest {

  @MockBean private ReactiveUUIDIdentityCrudFacade<MetricDTO> facade;

  @Mock private MapperInterface<MetricTO, MetricDTO> mapper;

  @InjectMocks private ReactiveAbstractUUIDIdentityCrudRestImpl<MetricTO, MetricDTO> restController;

  private MetricTO metricTO;
  private MetricDTO metricDTO;

  private MetricDTO createMetricDTO() {
    MetricDTO dto = new MetricDTO();
    dto.setMetric("TestMetric");
    // Set your DTO properties here
    return dto;
  }

  private MetricTO createMetricTO() {
    MetricTO to = new MetricTO();
    to.setMetric("TestMetric");
    // Set your TO properties here
    return to;
  }

  private MetricDTO updatedMetricDTO() {
    MetricDTO dto = new MetricDTO();
    dto.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    dto.setMetric("TestMetric_updated");
    // Set your DTO properties here
    return dto;
  }

  private MetricTO updatedMetricTO() {
    MetricTO to = new MetricTO();
    to.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    to.setMetric("TestMetric_updated");
    // Set your DTO properties here
    return to;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    metricTO = new MetricTO();
    metricTO.setId(id);
    metricTO.setMetric("TestMetric");

    metricDTO = new MetricDTO();
    metricDTO.setId(id);
    metricDTO.setMetric("TestMetric");
  }

  @Test
  void createTest() {
    when(mapper.from(createMetricTO())).thenReturn(createMetricDTO());
    when(mapper.to(metricDTO)).thenReturn(metricTO);
    when(facade.save(any())).thenReturn(Mono.just(metricDTO));

    ResponseEntity<MetricTO> response = restController.create(createMetricTO()).block();

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.CREATED);
    assert response.getBody().equals(metricTO);
  }

  @Test
  void updateTest() {
    UUID id = metricDTO.getId();
    when(mapper.from(metricTO)).thenReturn(metricDTO);
    when(mapper.to(updatedMetricDTO())).thenReturn(updatedMetricTO());
    when(facade.existsById(id)).thenReturn(Mono.just(true));
    when(facade.update(metricDTO)).thenReturn(Mono.just(updatedMetricDTO()));

    ResponseEntity<MetricTO> response = restController.update(id, metricTO).block();

    verify(facade, times(1)).existsById(id);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(updatedMetricTO());
  }

  @Test
  void partialUpdateTest() {

    UUID id = metricDTO.getId();
    when(mapper.from(metricTO)).thenReturn(metricDTO);
    when(mapper.to(updatedMetricDTO())).thenReturn(updatedMetricTO());
    when(facade.existsById(id)).thenReturn(Mono.just(true));
    when(facade.update(metricDTO)).thenReturn(Mono.just(updatedMetricDTO()));
    when(facade.partialUpdate(metricDTO)).thenReturn(Mono.just(updatedMetricDTO()));

    ResponseEntity<MetricTO> response = restController.partialUpdate(id, metricTO).block();

    verify(facade, times(1)).existsById(id);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(updatedMetricTO());
  }

  @Test
  void findAllTest() {
    Flux<MetricDTO> metricDTOFlux = Flux.just(metricDTO);
    when(facade.findAll()).thenReturn(metricDTOFlux);
    when(mapper.to(metricDTO)).thenReturn(metricTO);

    Flux<MetricTO> response = restController.findAll();

    verify(facade, times(1)).findAll();
    verifyNoMoreInteractions(facade, mapper);

    assert response.collectList().block().equals(List.of(metricTO));
  }

  @Test
  void findOneTest() {
    UUID id = metricDTO.getId();
    when(facade.findOne(id)).thenReturn(Mono.just(metricDTO));
    when(mapper.to(metricDTO)).thenReturn(metricTO);

    ResponseEntity<MetricTO> response = restController.findOne(id).block();

    verify(facade, times(1)).findOne(id);
    verify(mapper, times(1)).to(metricDTO);
    verifyNoMoreInteractions(facade, mapper);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(metricTO);
  }

  @Test
  void deleteTest() {
    UUID id = metricDTO.getId();
    when(facade.delete(id)).thenReturn(Mono.empty());

    ResponseEntity<Void> response = restController.delete(id).block();

    verify(facade, times(1)).delete(id);
    verifyNoMoreInteractions(facade, mapper);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.NO_CONTENT);
  }
}
