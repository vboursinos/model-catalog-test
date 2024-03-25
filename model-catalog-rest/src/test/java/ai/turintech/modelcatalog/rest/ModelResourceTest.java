package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.facade.ModelFacade;
import ai.turintech.modelcatalog.to.ModelTO;
import java.net.URI;
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
import reactor.core.publisher.Mono;

@SpringJUnitConfig(TestRestConfig.class)
public class ModelResourceTest extends BasicRestTest {

  @MockBean private ModelFacade modelFacade;

  private UUID uuid;
  private ModelDTO modelDTO;
  private ModelTO modelTO;

  @BeforeEach
  public void modelDTOsetup() {
    uuid = UUID.randomUUID();
    modelDTO = new ModelDTO();
    modelDTO.setId(uuid);
    modelDTO.setName("test_model");
  }

  @BeforeEach
  public void modelTOsetup() {
    uuid = UUID.randomUUID();
    modelTO = new ModelTO();
    modelTO.setId(uuid);
    modelTO.setName("test_model");
  }

  @Test
  public void createModelTest() {
    when(modelFacade.save(any(ModelDTO.class))).thenReturn(Mono.just(modelDTO));
    // todo convert from ModelTO to ModelDTO
    //
    // when(modelFacade.save(any(ModelDTO.class))).thenReturn(Mono.just(modelMapper.from(modelTO)));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/models"),
            request -> ServerResponse.created(URI.create("/models")).bodyValue(modelDTO));

    executePostAndExpect(route, "/models", modelDTO, modelDTO);
  }

  @Test
  public void updateModelTest() {
    when(modelFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelFacade.update(any(ModelDTO.class))).thenReturn(Mono.just(modelDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/models/{id}"), req -> ServerResponse.ok().bodyValue(modelDTO));

    executePutAndExpect(route, "/models/{id}", modelDTO, modelDTO);
  }

  @Test
  public void partialUpdateModelTest() {
    when(modelFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelFacade.partialUpdate(any(ModelDTO.class))).thenReturn(Mono.just(modelDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/models/{id}"),
            req -> ServerResponse.ok().bodyValue(modelDTO));

    executePatchAndExpect(route, "/models/{id}", modelDTO, modelDTO);
  }

  @Test
  public void getModelByIdTest() {
    Mono<ModelDTO> modelDTOMono = Mono.just(modelDTO);
    when(modelFacade.findOne(uuid)).thenReturn(modelDTOMono);

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/models/{id}"),
            request -> ServerResponse.ok().bodyValue(modelDTO));

    executeGetAndExpect(route, "/models/{id}", modelDTO);
  }

  @Test
  public void deleteModelTest() {
    when(modelFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelFacade.delete(uuid)).thenReturn(Mono.empty());

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/models/{id}"), req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/models/{id}");
  }

  private void executeGetAndExpect(RouterFunction<?> route, String url, ModelDTO modelDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelDTO.class)
        .isEqualTo(modelDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route, String url, ModelDTO requestBody, ModelDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ModelDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route, String url, ModelDTO requestBody, ModelDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route, String url, ModelDTO requestBody, ModelDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<ModelDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ModelDTO.class)
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

  private ModelDTO generateAnotherModelDTO() {
    ModelDTO anotherModelDTO = new ModelDTO();
    anotherModelDTO.setId(UUID.randomUUID());
    anotherModelDTO.setName("another_test_model_type");
    return anotherModelDTO;
  }
}
