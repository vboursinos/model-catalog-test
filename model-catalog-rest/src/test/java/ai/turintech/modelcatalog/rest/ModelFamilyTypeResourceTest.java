package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.ModelFamilyTypeDTO;
import ai.turintech.modelcatalog.facade.ModelFamilyTypeFacade;
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
public class ModelFamilyTypeResourceTest extends BasicRestTest {

  @MockBean private ModelFamilyTypeFacade modelFamilyTypeFacade;

  private UUID uuid;
  private ModelFamilyTypeDTO modelFamilyTypeDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    modelFamilyTypeDTO = new ModelFamilyTypeDTO();
    modelFamilyTypeDTO.setId(uuid);
    modelFamilyTypeDTO.setName("test_family_type");
  }

  @Test
  public void findByIdTest() {
    Mono<ModelFamilyTypeDTO> modelFamilyTypeDTOMono = Mono.just(modelFamilyTypeDTO);
    when(modelFamilyTypeFacade.findOne(any(UUID.class))).thenReturn(modelFamilyTypeDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-family-types/{id}"),
            request -> ServerResponse.ok().bodyValue(modelFamilyTypeDTO));
    executeGetAndExpect(route, "/model-family-types/{id}", modelFamilyTypeDTO);
  }

  @Test
  public void createModelFamilyTypeTest() {
    when(modelFamilyTypeFacade.save(any(ModelFamilyTypeDTO.class)))
        .thenReturn(Mono.just(modelFamilyTypeDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/model-family-types"),
            request ->
                ServerResponse.created(URI.create("/model-family-types"))
                    .bodyValue(modelFamilyTypeDTO));

    executePostAndExpect(route, "/model-family-types", modelFamilyTypeDTO, modelFamilyTypeDTO);
  }

  @Test
  public void updateModelFamilyTypeTest() {
    when(modelFamilyTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelFamilyTypeFacade.update(any(ModelFamilyTypeDTO.class)))
        .thenReturn(Mono.just(modelFamilyTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/model-family-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelFamilyTypeDTO));

    executePutAndExpect(route, "/model-family-types/{id}", modelFamilyTypeDTO, modelFamilyTypeDTO);
  }

  @Test
  public void partialUpdateModelFamilyTypeTest() {
    when(modelFamilyTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelFamilyTypeFacade.partialUpdate(any(ModelFamilyTypeDTO.class)))
        .thenReturn(Mono.just(modelFamilyTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/model-family-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelFamilyTypeDTO));

    executePatchAndExpect(
        route, "/model-family-types/{id}", modelFamilyTypeDTO, modelFamilyTypeDTO);
  }

  @Test
  public void findAllModelFamilyTypesTest() {
    List<ModelFamilyTypeDTO> modelFamilyTypeDTOList =
        Arrays.asList(modelFamilyTypeDTO, generateAnotherModelFamilyTypeDTO());
    when(modelFamilyTypeFacade.findAll()).thenReturn(Flux.fromIterable(modelFamilyTypeDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-family-types"),
            req ->
                ServerResponse.ok()
                    .body(modelFamilyTypeFacade.findAll(), ModelFamilyTypeDTO.class));
    executeGetAndExpectMultiple(route, "/model-family-types", modelFamilyTypeDTOList);
  }

  @Test
  public void deleteModelFamilyTypeTest() {
    when(modelFamilyTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelFamilyTypeFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/model-family-types/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/model-family-types/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, ModelFamilyTypeDTO modelFamilyTypeDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelFamilyTypeDTO.class)
        .isEqualTo(modelFamilyTypeDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      ModelFamilyTypeDTO requestBody,
      ModelFamilyTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ModelFamilyTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      ModelFamilyTypeDTO requestBody,
      ModelFamilyTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelFamilyTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      ModelFamilyTypeDTO requestBody,
      ModelFamilyTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelFamilyTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<ModelFamilyTypeDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ModelFamilyTypeDTO.class)
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

  private ModelFamilyTypeDTO generateAnotherModelFamilyTypeDTO() {
    ModelFamilyTypeDTO anotherModelFamilyTypeDTO = new ModelFamilyTypeDTO();
    anotherModelFamilyTypeDTO.setId(UUID.randomUUID());
    anotherModelFamilyTypeDTO.setName("another_test_family_type");
    return anotherModelFamilyTypeDTO;
  }
}
