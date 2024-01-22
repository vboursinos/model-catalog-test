package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.ModelEnsembleTypeDTO;
import ai.turintech.modelcatalog.facade.ModelEnsembleTypeFacade;
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
public class ModelEnsembleTypeResourceTest extends BasicRestTest {

  @MockBean private ModelEnsembleTypeFacade modelEnsembleTypeFacade;

  private UUID uuid;
  private ModelEnsembleTypeDTO modelEnsembleTypeDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    modelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    modelEnsembleTypeDTO.setId(uuid);
    modelEnsembleTypeDTO.setName("test_ensemble_type");
  }

  @Test
  public void findByIdTest() {
    Mono<ModelEnsembleTypeDTO> modelEnsembleTypeDTOMono = Mono.just(modelEnsembleTypeDTO);
    when(modelEnsembleTypeFacade.findOne(any(UUID.class))).thenReturn(modelEnsembleTypeDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-ensemble-types/{id}"),
            request -> ServerResponse.ok().bodyValue(modelEnsembleTypeDTO));
    executeGetAndExpect(route, "/model-ensemble-types/{id}", modelEnsembleTypeDTO);
  }

  @Test
  public void createModelEnsembleTypeTest() {
    when(modelEnsembleTypeFacade.save(any(ModelEnsembleTypeDTO.class)))
        .thenReturn(Mono.just(modelEnsembleTypeDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/model-ensemble-types"),
            request ->
                ServerResponse.created(URI.create("/model-ensemble-types"))
                    .bodyValue(modelEnsembleTypeDTO));

    executePostAndExpect(
        route, "/model-ensemble-types", modelEnsembleTypeDTO, modelEnsembleTypeDTO);
  }

  @Test
  public void updateModelEnsembleTypeTest() {
    when(modelEnsembleTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelEnsembleTypeFacade.update(any(ModelEnsembleTypeDTO.class)))
        .thenReturn(Mono.just(modelEnsembleTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/model-ensemble-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelEnsembleTypeDTO));

    executePutAndExpect(
        route, "/model-ensemble-types/{id}", modelEnsembleTypeDTO, modelEnsembleTypeDTO);
  }

  @Test
  public void partialUpdateModelEnsembleTypeTest() {
    when(modelEnsembleTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelEnsembleTypeFacade.partialUpdate(any(ModelEnsembleTypeDTO.class)))
        .thenReturn(Mono.just(modelEnsembleTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/model-ensemble-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelEnsembleTypeDTO));

    executePatchAndExpect(
        route, "/model-ensemble-types/{id}", modelEnsembleTypeDTO, modelEnsembleTypeDTO);
  }

  @Test
  public void findAllModelEnsembleTypesTest() {
    List<ModelEnsembleTypeDTO> modelEnsembleTypeDTOList =
        Arrays.asList(modelEnsembleTypeDTO, generateAnotherModelEnsembleTypeDTO());
    when(modelEnsembleTypeFacade.findAll()).thenReturn(Flux.fromIterable(modelEnsembleTypeDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-ensemble-types"),
            req ->
                ServerResponse.ok()
                    .body(modelEnsembleTypeFacade.findAll(), ModelEnsembleTypeDTO.class));
    executeGetAndExpectMultiple(route, "/model-ensemble-types", modelEnsembleTypeDTOList);
  }

  @Test
  public void deleteModelEnsembleTypeTest() {
    when(modelEnsembleTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelEnsembleTypeFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/model-ensemble-types/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/model-ensemble-types/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, ModelEnsembleTypeDTO modelEnsembleTypeDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelEnsembleTypeDTO.class)
        .isEqualTo(modelEnsembleTypeDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      ModelEnsembleTypeDTO requestBody,
      ModelEnsembleTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ModelEnsembleTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      ModelEnsembleTypeDTO requestBody,
      ModelEnsembleTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelEnsembleTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      ModelEnsembleTypeDTO requestBody,
      ModelEnsembleTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelEnsembleTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<ModelEnsembleTypeDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ModelEnsembleTypeDTO.class)
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

  private ModelEnsembleTypeDTO generateAnotherModelEnsembleTypeDTO() {
    ModelEnsembleTypeDTO anotherModelEnsembleTypeDTO = new ModelEnsembleTypeDTO();
    anotherModelEnsembleTypeDTO.setId(UUID.randomUUID());
    anotherModelEnsembleTypeDTO.setName("another_test_ensemble_type");
    return anotherModelEnsembleTypeDTO;
  }
}
