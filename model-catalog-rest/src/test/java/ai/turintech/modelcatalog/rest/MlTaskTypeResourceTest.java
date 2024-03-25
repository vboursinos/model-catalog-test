package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.MlTaskTypeDTO;
import ai.turintech.modelcatalog.facade.MlTaskTypeFacade;
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
public class MlTaskTypeResourceTest extends BasicRestTest {

  @MockBean private MlTaskTypeFacade mlTaskTypeFacade;

  private UUID uuid;
  private MlTaskTypeDTO mlTaskTypeDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    mlTaskTypeDTO = new MlTaskTypeDTO();
    mlTaskTypeDTO.setId(uuid);
    mlTaskTypeDTO.setName("test_task_type");
  }

  @Test
  public void findByIdTest() {
    Mono<MlTaskTypeDTO> mlTaskTypeDTOMono = Mono.just(mlTaskTypeDTO);
    when(mlTaskTypeFacade.findOne(any(UUID.class))).thenReturn(mlTaskTypeDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/ml-task-types/{id}"),
            request -> ServerResponse.ok().bodyValue(mlTaskTypeDTO));
    executeGetAndExpect(route, "/ml-task-types/{id}", mlTaskTypeDTO);
  }

  @Test
  public void createMlTaskTypeTest() {
    when(mlTaskTypeFacade.save(any(MlTaskTypeDTO.class))).thenReturn(Mono.just(mlTaskTypeDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/ml-task-types"),
            request ->
                ServerResponse.created(URI.create("/ml-task-types")).bodyValue(mlTaskTypeDTO));

    executePostAndExpect(route, "/ml-task-types", mlTaskTypeDTO, mlTaskTypeDTO);
  }

  @Test
  public void updateMlTaskTypeTest() {
    when(mlTaskTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(mlTaskTypeFacade.update(any(MlTaskTypeDTO.class))).thenReturn(Mono.just(mlTaskTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/ml-task-types/{id}"),
            req -> ServerResponse.ok().bodyValue(mlTaskTypeDTO));

    executePutAndExpect(route, "/ml-task-types/{id}", mlTaskTypeDTO, mlTaskTypeDTO);
  }

  @Test
  public void partialUpdateMlTaskTypeTest() {
    when(mlTaskTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(mlTaskTypeFacade.partialUpdate(any(MlTaskTypeDTO.class)))
        .thenReturn(Mono.just(mlTaskTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/ml-task-types/{id}"),
            req -> ServerResponse.ok().bodyValue(mlTaskTypeDTO));

    executePatchAndExpect(route, "/ml-task-types/{id}", mlTaskTypeDTO, mlTaskTypeDTO);
  }

  @Test
  public void findAllMlTaskTypesTest() {
    List<MlTaskTypeDTO> mlTaskTypeDTOList =
        Arrays.asList(mlTaskTypeDTO, generateAnotherMlTaskTypeDTO());
    when(mlTaskTypeFacade.findAll()).thenReturn(Flux.fromIterable(mlTaskTypeDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/ml-task-types"),
            req -> ServerResponse.ok().body(mlTaskTypeFacade.findAll(), MlTaskTypeDTO.class));
    executeGetAndExpectMultiple(route, "/ml-task-types", mlTaskTypeDTOList);
  }

  @Test
  public void deleteMlTaskTypeTest() {
    when(mlTaskTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(mlTaskTypeFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/ml-task-types/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/ml-task-types/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, MlTaskTypeDTO mlTaskTypeDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MlTaskTypeDTO.class)
        .isEqualTo(mlTaskTypeDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route, String url, MlTaskTypeDTO requestBody, MlTaskTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(MlTaskTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route, String url, MlTaskTypeDTO requestBody, MlTaskTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MlTaskTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route, String url, MlTaskTypeDTO requestBody, MlTaskTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MlTaskTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<MlTaskTypeDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(MlTaskTypeDTO.class)
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

  private MlTaskTypeDTO generateAnotherMlTaskTypeDTO() {
    MlTaskTypeDTO anotherMlTaskTypeDTO = new MlTaskTypeDTO();
    anotherMlTaskTypeDTO.setId(UUID.randomUUID());
    anotherMlTaskTypeDTO.setName("another_test_metric");
    return anotherMlTaskTypeDTO;
  }
}
