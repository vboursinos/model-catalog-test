package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.ModelStructureTypeDTO;
import ai.turintech.modelcatalog.facade.ModelStructureTypeFacade;
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
public class ModelStructureTypeResourceTest extends BasicRestTest {

  @MockBean private ModelStructureTypeFacade modelStructureTypeFacade;

  private UUID uuid;
  private ModelStructureTypeDTO modelStructureTypeDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    modelStructureTypeDTO = new ModelStructureTypeDTO();
    modelStructureTypeDTO.setId(uuid);
    modelStructureTypeDTO.setName("test_structure_type");
  }

  @Test
  public void findByIdTest() {
    Mono<ModelStructureTypeDTO> modelStructureTypeDTOMono = Mono.just(modelStructureTypeDTO);
    when(modelStructureTypeFacade.findOne(any(UUID.class))).thenReturn(modelStructureTypeDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-structure-types/{id}"),
            request -> ServerResponse.ok().bodyValue(modelStructureTypeDTO));
    executeGetAndExpect(route, "/model-structure-types/{id}", modelStructureTypeDTO);
  }

  @Test
  public void createModelStructureTypeTest() {
    when(modelStructureTypeFacade.save(any(ModelStructureTypeDTO.class)))
        .thenReturn(Mono.just(modelStructureTypeDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/model-structure-types"),
            request ->
                ServerResponse.created(URI.create("/model-structure-types"))
                    .bodyValue(modelStructureTypeDTO));

    executePostAndExpect(
        route, "/model-structure-types", modelStructureTypeDTO, modelStructureTypeDTO);
  }

  @Test
  public void updateModelStructureTypeTest() {
    when(modelStructureTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelStructureTypeFacade.update(any(ModelStructureTypeDTO.class)))
        .thenReturn(Mono.just(modelStructureTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/model-structure-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelStructureTypeDTO));

    executePutAndExpect(
        route, "/model-structure-types/{id}", modelStructureTypeDTO, modelStructureTypeDTO);
  }

  @Test
  public void partialUpdateModelStructureTypeTest() {
    when(modelStructureTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelStructureTypeFacade.partialUpdate(any(ModelStructureTypeDTO.class)))
        .thenReturn(Mono.just(modelStructureTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/model-structure-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelStructureTypeDTO));

    executePatchAndExpect(
        route, "/model-structure-types/{id}", modelStructureTypeDTO, modelStructureTypeDTO);
  }

  @Test
  public void findAllModelStructureTypesTest() {
    List<ModelStructureTypeDTO> modelStructureTypeDTOList =
        Arrays.asList(modelStructureTypeDTO, generateAnotherModelStructureTypeDTO());
    when(modelStructureTypeFacade.findAll())
        .thenReturn(Flux.fromIterable(modelStructureTypeDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-structure-types"),
            req ->
                ServerResponse.ok()
                    .body(modelStructureTypeFacade.findAll(), ModelStructureTypeDTO.class));
    executeGetAndExpectMultiple(route, "/model-structure-types", modelStructureTypeDTOList);
  }

  @Test
  public void deleteModelStructureTypeTest() {
    when(modelStructureTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelStructureTypeFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/model-structure-types/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/model-structure-types/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, ModelStructureTypeDTO modelStructureTypeDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelStructureTypeDTO.class)
        .isEqualTo(modelStructureTypeDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      ModelStructureTypeDTO requestBody,
      ModelStructureTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ModelStructureTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      ModelStructureTypeDTO requestBody,
      ModelStructureTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelStructureTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      ModelStructureTypeDTO requestBody,
      ModelStructureTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelStructureTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<ModelStructureTypeDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ModelStructureTypeDTO.class)
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

  private ModelStructureTypeDTO generateAnotherModelStructureTypeDTO() {
    ModelStructureTypeDTO anotherModelStructureTypeDTO = new ModelStructureTypeDTO();
    anotherModelStructureTypeDTO.setId(UUID.randomUUID());
    anotherModelStructureTypeDTO.setName("another_test_structure_type");
    return anotherModelStructureTypeDTO;
  }
}
