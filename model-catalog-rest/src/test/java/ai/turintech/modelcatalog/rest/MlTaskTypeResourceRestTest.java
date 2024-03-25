package ai.turintech.modelcatalog.rest;

import static org.mockito.Mockito.*;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.to.MlTaskTypeTO;
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
class MlTaskTypeResourceRestTest extends BasicRestTest {

  @MockBean private ReactiveUUIDIdentityCrudFacade<MlTaskTypeDTO> facade;

  @Mock private MapperInterface<MlTaskTypeTO, MlTaskTypeDTO> mapper;

  @InjectMocks
  private ReactiveAbstractUUIDIdentityCrudRestImpl<MlTaskTypeTO, MlTaskTypeDTO> restController;

  private MlTaskTypeTO mlTaskTypeTO;
  private MlTaskTypeDTO mlTaskTypeDTO;

  private MlTaskTypeDTO createMlTaskTypeDTO() {
    MlTaskTypeDTO dto = new MlTaskTypeDTO();
    dto.setName("TestMlTaskType");
    // Set your DTO properties here
    return dto;
  }

  private MlTaskTypeTO createMlTaskTypeTO() {
    MlTaskTypeTO to = new MlTaskTypeTO();
    to.setName("TestMlTaskType");
    // Set your TO properties here
    return to;
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    mlTaskTypeTO = new MlTaskTypeTO();
    mlTaskTypeTO.setId(id);
    mlTaskTypeTO.setName("TestMlTaskType");

    mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(id);
    mlTaskTypeDTO.setName("TestMlTaskType");
  }

  @Test
  void createTest() {
    when(mapper.from(createMlTaskTypeTO())).thenReturn(createMlTaskTypeDTO());
    when(mapper.to(mlTaskTypeDTO)).thenReturn(mlTaskTypeTO);
    when(facade.save(any())).thenReturn(Mono.just(mlTaskTypeDTO));

    ResponseEntity<MlTaskTypeTO> response = restController.create(createMlTaskTypeTO()).block();

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.CREATED);
    assert response.getBody().equals(mlTaskTypeTO);
  }

  @Test
  void updateTest() {
    UUID id = mlTaskTypeDTO.getId();
    when(mapper.from(mlTaskTypeTO)).thenReturn(mlTaskTypeDTO);
    when(mapper.to(updatedMlTaskTypeDTO())).thenReturn(updatedMlTaskTypeTO());
    when(facade.existsById(id)).thenReturn(Mono.just(true));
    when(facade.update(mlTaskTypeDTO)).thenReturn(Mono.just(updatedMlTaskTypeDTO()));

    ResponseEntity<MlTaskTypeTO> response = restController.update(id, mlTaskTypeTO).block();

    verify(facade, times(1)).existsById(id);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(updatedMlTaskTypeTO());
  }

  @Test
  void partialUpdateTest() {
    UUID id = mlTaskTypeDTO.getId();
    when(mapper.from(mlTaskTypeTO)).thenReturn(mlTaskTypeDTO);
    when(mapper.to(updatedMlTaskTypeDTO())).thenReturn(updatedMlTaskTypeTO());
    when(facade.existsById(id)).thenReturn(Mono.just(true));
    when(facade.update(mlTaskTypeDTO)).thenReturn(Mono.just(updatedMlTaskTypeDTO()));
    when(facade.partialUpdate(mlTaskTypeDTO)).thenReturn(Mono.just(updatedMlTaskTypeDTO()));

    ResponseEntity<MlTaskTypeTO> response = restController.partialUpdate(id, mlTaskTypeTO).block();

    verify(facade, times(1)).existsById(id);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(updatedMlTaskTypeTO());
  }

  @Test
  void findAllTest() {
    Flux<MlTaskTypeDTO> mlTaskTypeDTOFlux = Flux.just(mlTaskTypeDTO);
    when(facade.findAll()).thenReturn(mlTaskTypeDTOFlux);
    when(mapper.to(mlTaskTypeDTO)).thenReturn(mlTaskTypeTO);

    Flux<MlTaskTypeTO> response = restController.findAll();

    verify(facade, times(1)).findAll();
    verifyNoMoreInteractions(facade, mapper);

    assert response.collectList().block().equals(List.of(mlTaskTypeTO));
  }

  @Test
  void findOneTest() {
    UUID id = mlTaskTypeDTO.getId();
    when(facade.findOne(id)).thenReturn(Mono.just(mlTaskTypeDTO));
    when(mapper.to(mlTaskTypeDTO)).thenReturn(mlTaskTypeTO);

    ResponseEntity<MlTaskTypeTO> response = restController.findOne(id).block();

    verify(facade, times(1)).findOne(id);
    verify(mapper, times(1)).to(mlTaskTypeDTO);
    verifyNoMoreInteractions(facade, mapper);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(mlTaskTypeTO);
  }

  @Test
  void deleteTest() {
    UUID id = mlTaskTypeDTO.getId();
    when(facade.delete(id)).thenReturn(Mono.empty());

    ResponseEntity<Void> response = restController.delete(id).block();

    verify(facade, times(1)).delete(id);
    verifyNoMoreInteractions(facade, mapper);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.NO_CONTENT);
  }

  private MlTaskTypeDTO updatedMlTaskTypeDTO() {
    MlTaskTypeDTO dto = new MlTaskTypeDTO();
    dto.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    dto.setName("TestMlTaskType_updated");
    // Set your DTO properties here
    return dto;
  }

  private MlTaskTypeTO updatedMlTaskTypeTO() {
    MlTaskTypeTO to = new MlTaskTypeTO();
    to.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    to.setName("TestMlTaskType_updated");
    // Set your TO properties here
    return to;
  }
}
