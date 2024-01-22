package ai.turintech.modelcatalog.rest;

import static org.mockito.Mockito.*;

import ai.turintech.components.architecture.reactive.ReactiveUUIDIdentityCrudFacade;
import ai.turintech.components.architecture.rest.impl.reactive.ReactiveAbstractUUIDIdentityCrudRestImpl;
import ai.turintech.components.mapper.api.MapperInterface;
import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.to.BooleanParameterTO;
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
import reactor.core.publisher.Mono;

@SpringJUnitConfig(TestRestConfig.class)
class ReactiveAbstractUUIDIdentityCrudRestImplTest extends BasicRestTest {

  @MockBean private ReactiveUUIDIdentityCrudFacade<BooleanParameterDTO> facade;

  @Mock private MapperInterface<BooleanParameterTO, BooleanParameterDTO> mapper;

  @InjectMocks
  private ReactiveAbstractUUIDIdentityCrudRestImpl<BooleanParameterTO, BooleanParameterDTO>
      restController;

  private BooleanParameterTO booleanParameterTO;
  private BooleanParameterDTO booleanParameterDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Sample data
    UUID id = UUID.randomUUID();
    booleanParameterTO = new BooleanParameterTO();
    booleanParameterTO.setId(id);
    booleanParameterTO.setDefaultValue(true);

    booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(id);
    booleanParameterDTO.setDefaultValue(true);
  }

  //  @Test
  //  void createTest() {
  //    // Mocking
  //    when(mapper.from(booleanParameterTO)).thenReturn(booleanParameterDTO);
  //    when(facade.save(booleanParameterDTO)).thenReturn(Mono.just(booleanParameterDTO));
  //
  //    // Test
  //    ResponseEntity<BooleanParameterTO> response =
  // restController.create(booleanParameterTO).block();
  //
  //    // Assertions
  //    verify(facade, times(1)).save(booleanParameterDTO);
  //    verify(mapper, times(1)).to(booleanParameterDTO);
  //    verifyNoMoreInteractions(facade, mapper);
  //
  //    assert response != null;
  //    assert response.getStatusCode().equals(HttpStatus.CREATED);
  //    assert response.getBody().equals(booleanParameterTO);
  //  }
  //
  //  @Test
  //  void updateTest() {
  //    // Mocking
  //    UUID id = booleanParameterDTO.getId();
  //    when(mapper.from(booleanParameterTO)).thenReturn(booleanParameterDTO);
  //    when(facade.existsById(id)).thenReturn(Mono.just(true));
  //    when(facade.update(booleanParameterDTO)).thenReturn(Mono.just(booleanParameterDTO));
  //
  //    // Test
  //    ResponseEntity<BooleanParameterTO> response =
  //        restController.update(id, booleanParameterTO).block();
  //
  //    // Assertions
  //    verify(facade, times(1)).existsById(id);
  //    verify(facade, times(1)).update(booleanParameterDTO);
  //    verify(mapper, times(1)).to(booleanParameterDTO);
  //    verifyNoMoreInteractions(facade, mapper);
  //
  //    assert response != null;
  //    assert response.getStatusCode().equals(HttpStatus.OK);
  //    assert response.getBody().equals(booleanParameterTO);
  //  }
  //
  //  @Test
  //  void partialUpdateTest() {
  //    // Mocking
  //    UUID id = booleanParameterDTO.getId();
  //    when(mapper.from(booleanParameterTO)).thenReturn(booleanParameterDTO);
  //    when(facade.existsById(id)).thenReturn(Mono.just(true));
  //    when(facade.partialUpdate(booleanParameterDTO)).thenReturn(Mono.just(booleanParameterDTO));
  //
  //    // Test
  //    ResponseEntity<BooleanParameterTO> response =
  //        restController.partialUpdate(id, booleanParameterTO).block();
  //
  //    // Assertions
  //    verify(facade, times(1)).existsById(id);
  //    verify(facade, times(1)).partialUpdate(booleanParameterDTO);
  //    verify(mapper, times(1)).to(booleanParameterDTO);
  //    verifyNoMoreInteractions(facade, mapper);
  //
  //    assert response != null;
  //    assert response.getStatusCode().equals(HttpStatus.OK);
  //    assert response.getBody().equals(booleanParameterTO);
  //  }
  //
  //  @Test
  //  void findAllTest() {
  //    // Mocking
  //    Flux<BooleanParameterDTO> booleanParameterFlux = Flux.just(booleanParameterDTO);
  //    when(facade.findAll()).thenReturn(booleanParameterFlux);
  //    when(mapper.to(booleanParameterDTO)).thenReturn(booleanParameterTO);
  //
  //    // Test
  //    Flux<BooleanParameterTO> response = restController.findAll();
  //
  //    // Assertions
  //    verify(facade, times(1)).findAll();
  //    verify(mapper, times(1)).to(booleanParameterDTO);
  //    verifyNoMoreInteractions(facade, mapper);
  //
  //    assert response.collectList().block().equals(List.of(booleanParameterTO));
  //  }

  @Test
  void findOneTest() {
    // Mocking
    UUID id = booleanParameterDTO.getId();
    when(facade.findOne(id)).thenReturn(Mono.just(booleanParameterDTO));
    when(mapper.to(booleanParameterDTO)).thenReturn(booleanParameterTO);

    // Test
    ResponseEntity<BooleanParameterTO> response = restController.findOne(id).block();

    // Assertions
    verify(facade, times(1)).findOne(id);
    verify(mapper, times(1)).to(booleanParameterDTO);
    verifyNoMoreInteractions(facade, mapper);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getBody().equals(booleanParameterTO);
  }

  @Test
  void deleteTest() {
    // Mocking
    UUID id = booleanParameterDTO.getId();
    when(facade.delete(id)).thenReturn(Mono.empty());

    // Test
    ResponseEntity<Void> response = restController.delete(id).block();

    // Assertions
    verify(facade, times(1)).delete(id);
    verifyNoMoreInteractions(facade, mapper);

    assert response != null;
    assert response.getStatusCode().equals(HttpStatus.NO_CONTENT);
  }
}
