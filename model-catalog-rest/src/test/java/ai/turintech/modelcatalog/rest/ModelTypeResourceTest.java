package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.ModelTypeDTO;
import ai.turintech.modelcatalog.facade.ModelTypeFacade;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringJUnitConfig(TestRestConfig.class)
public class ModelTypeResourceTest extends BasicRestTest {

  @MockBean private ModelTypeFacade modelTypeFacade;

  private UUID uuid;
  private ModelTypeDTO modelTypeDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    modelTypeDTO = new ModelTypeDTO();
    modelTypeDTO.setId(uuid);
    modelTypeDTO.setName("test_model_type");
  }

  @Test
  public void findByIdTest() {
    Mono<ModelTypeDTO> modelTypeDTOMono = Mono.just(modelTypeDTO);
    when(modelTypeFacade.findOne(any(UUID.class))).thenReturn(modelTypeDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-types/{id}"),
            request -> ServerResponse.ok().bodyValue(modelTypeDTO));
    executeGetAndExpect(route, "/model-types/{id}", modelTypeDTO);
  }

  @Test
  public void createModelTypeTest() {
    when(modelTypeFacade.save(any(ModelTypeDTO.class))).thenReturn(Mono.just(modelTypeDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/model-types"),
            request -> ServerResponse.created(URI.create("/model-types")).bodyValue(modelTypeDTO));

    executePostAndExpect(route, "/model-types", modelTypeDTO, modelTypeDTO);
  }

  @Test
  public void updateModelTypeTest() {
    when(modelTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelTypeFacade.update(any(ModelTypeDTO.class))).thenReturn(Mono.just(modelTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/model-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelTypeDTO));

    executePutAndExpect(route, "/model-types/{id}", modelTypeDTO, modelTypeDTO);
  }

  @Test
  public void partialUpdateModelTypeTest() {
    when(modelTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelTypeFacade.partialUpdate(any(ModelTypeDTO.class)))
        .thenReturn(Mono.just(modelTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/model-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelTypeDTO));

    executePatchAndExpect(route, "/model-types/{id}", modelTypeDTO, modelTypeDTO);
  }

  @Test
  public void findAllModelTypesTest() {
    List<ModelTypeDTO> modelTypeDTOList =
        Arrays.asList(modelTypeDTO, generateAnotherModelTypeDTO());
    when(modelTypeFacade.findAll()).thenReturn(Flux.fromIterable(modelTypeDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-types"),
            req -> ServerResponse.ok().body(modelTypeFacade.findAll(), ModelTypeDTO.class));
    executeGetAndExpectMultiple(route, "/model-types", modelTypeDTOList);
  }

  @Test
  public void deleteModelTypeTest() {
    when(modelTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelTypeFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/model-types/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/model-types/{id}");
  }

  private void executeGetAndExpect(RouterFunction<?> route, String url, ModelTypeDTO modelTypeDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelTypeDTO.class)
        .isEqualTo(modelTypeDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route, String url, ModelTypeDTO requestBody, ModelTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ModelTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route, String url, ModelTypeDTO requestBody, ModelTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route, String url, ModelTypeDTO requestBody, ModelTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<ModelTypeDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ModelTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeDeleteAndExpect(RouterFunction<?> route, String url) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .delete()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isNoContent();
  }

  private ModelTypeDTO generateAnotherModelTypeDTO() {
    ModelTypeDTO anotherModelTypeDTO = new ModelTypeDTO();
    anotherModelTypeDTO.setId(UUID.randomUUID());
    anotherModelTypeDTO.setName("another_test_structure_type");
    return anotherModelTypeDTO;
  }
}
