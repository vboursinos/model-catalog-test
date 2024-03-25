package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.ModelGroupTypeDTO;
import ai.turintech.modelcatalog.facade.ModelGroupTypeFacade;
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
public class ModelGroupTypeResourceTest extends BasicRestTest {

  @MockBean private ModelGroupTypeFacade modelGroupTypeFacade;

  private UUID uuid;
  private ModelGroupTypeDTO modelGroupTypeDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    modelGroupTypeDTO = new ModelGroupTypeDTO();
    modelGroupTypeDTO.setId(uuid);
    modelGroupTypeDTO.setName("test_group_type");
  }

  @Test
  public void findByIdTest() {
    Mono<ModelGroupTypeDTO> modelGroupTypeDTOMono = Mono.just(modelGroupTypeDTO);
    when(modelGroupTypeFacade.findOne(any(UUID.class))).thenReturn(modelGroupTypeDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-group-types/{id}"),
            request -> ServerResponse.ok().bodyValue(modelGroupTypeDTO));
    executeGetAndExpect(route, "/model-group-types/{id}", modelGroupTypeDTO);
  }

  @Test
  public void createModelGroupTypeTest() {
    when(modelGroupTypeFacade.save(any(ModelGroupTypeDTO.class)))
        .thenReturn(Mono.just(modelGroupTypeDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/model-group-types"),
            request ->
                ServerResponse.created(URI.create("/model-group-types"))
                    .bodyValue(modelGroupTypeDTO));

    executePostAndExpect(route, "/model-group-types", modelGroupTypeDTO, modelGroupTypeDTO);
  }

  @Test
  public void updateModelGroupTypeTest() {
    when(modelGroupTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelGroupTypeFacade.update(any(ModelGroupTypeDTO.class)))
        .thenReturn(Mono.just(modelGroupTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/model-group-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelGroupTypeDTO));

    executePutAndExpect(route, "/model-group-types/{id}", modelGroupTypeDTO, modelGroupTypeDTO);
  }

  @Test
  public void partialUpdateModelGroupTypeTest() {
    when(modelGroupTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelGroupTypeFacade.partialUpdate(any(ModelGroupTypeDTO.class)))
        .thenReturn(Mono.just(modelGroupTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/model-group-types/{id}"),
            req -> ServerResponse.ok().bodyValue(modelGroupTypeDTO));

    executePatchAndExpect(route, "/model-group-types/{id}", modelGroupTypeDTO, modelGroupTypeDTO);
  }

  @Test
  public void findAllModelGroupTypesTest() {
    List<ModelGroupTypeDTO> modelGroupTypeDTOList =
        Arrays.asList(modelGroupTypeDTO, generateAnotherModelGroupTypeDTO());
    when(modelGroupTypeFacade.findAll()).thenReturn(Flux.fromIterable(modelGroupTypeDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/model-group-types"),
            req ->
                ServerResponse.ok().body(modelGroupTypeFacade.findAll(), ModelGroupTypeDTO.class));
    executeGetAndExpectMultiple(route, "/model-group-types", modelGroupTypeDTOList);
  }

  @Test
  public void deleteModelGroupTypeTest() {
    when(modelGroupTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(modelGroupTypeFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/model-group-types/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/model-group-types/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, ModelGroupTypeDTO modelGroupTypeDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelGroupTypeDTO.class)
        .isEqualTo(modelGroupTypeDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      ModelGroupTypeDTO requestBody,
      ModelGroupTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ModelGroupTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      ModelGroupTypeDTO requestBody,
      ModelGroupTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelGroupTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      ModelGroupTypeDTO requestBody,
      ModelGroupTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ModelGroupTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<ModelGroupTypeDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ModelGroupTypeDTO.class)
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

  private ModelGroupTypeDTO generateAnotherModelGroupTypeDTO() {
    ModelGroupTypeDTO anotherModelGroupTypeDTO = new ModelGroupTypeDTO();
    anotherModelGroupTypeDTO.setId(UUID.randomUUID());
    anotherModelGroupTypeDTO.setName("another_test_family_type");
    return anotherModelGroupTypeDTO;
  }
}
