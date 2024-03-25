package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.BooleanParameterDTO;
import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.facade.BooleanParameterFacade;
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
public class BooleanParameterResourceTest extends BasicRestTest {

  @MockBean private BooleanParameterFacade booleanParameterFacade;

  private UUID uuid;
  private BooleanParameterDTO booleanParameterDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    booleanParameterDTO = new BooleanParameterDTO();
    booleanParameterDTO.setId(uuid);
    booleanParameterDTO.setDefaultValue(true);
  }

  @Test
  public void findByIdTest() {
    Mono<BooleanParameterDTO> booleanParameterDTOMono = Mono.just(booleanParameterDTO);
    when(booleanParameterFacade.findOne(any(UUID.class))).thenReturn(booleanParameterDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/boolean-parameters/{id}"),
            request -> ServerResponse.ok().bodyValue(booleanParameterDTO));
    executeGetAndExpect(route, "/api/boolean-parameters/{id}", booleanParameterDTO);
  }

  @Test
  public void createBooleanParameterTest() {
    when(booleanParameterFacade.save(any(BooleanParameterDTO.class)))
        .thenReturn(Mono.just(booleanParameterDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/api/boolean-parameters"),
            request ->
                ServerResponse.created(URI.create("/api/boolean-parameters"))
                    .bodyValue(booleanParameterDTO));

    executePostAndExpect(
        route, "/api/boolean-parameters", booleanParameterDTO, booleanParameterDTO);
  }

  @Test
  public void updateBooleanParameterTest() {
    when(booleanParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(booleanParameterFacade.update(any(BooleanParameterDTO.class)))
        .thenReturn(Mono.just(booleanParameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/api/boolean-parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(booleanParameterDTO));

    executePutAndExpect(
        route, "/api/boolean-parameters/{id}", booleanParameterDTO, booleanParameterDTO);
  }

  @Test
  public void partialUpdateBooleanParameterTest() {
    when(booleanParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(booleanParameterFacade.partialUpdate(any(BooleanParameterDTO.class)))
        .thenReturn(Mono.just(booleanParameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/api/boolean-parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(booleanParameterDTO));

    executePatchAndExpect(
        route, "/api/boolean-parameters/{id}", booleanParameterDTO, booleanParameterDTO);
  }

  @Test
  public void findAllBooleanParameterTest() {
    List<BooleanParameterDTO> booleanParameterDTOList =
        Arrays.asList(booleanParameterDTO, generateAnotherBooleanParameterDTO());
    when(booleanParameterFacade.findAll()).thenReturn(Flux.fromIterable(booleanParameterDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/boolean-parameters"),
            req ->
                ServerResponse.ok()
                    .body(booleanParameterFacade.findAll(), CategoricalParameterValueDTO.class));
    executeGetAndExpectMultiple(route, "/api/boolean-parameters", booleanParameterDTOList);
  }

  @Test
  public void deleteBooleanParameterTest() {
    when(booleanParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(booleanParameterFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/api/boolean-parameters/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/api/boolean-parameters/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, BooleanParameterDTO booleanParameterDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(BooleanParameterDTO.class)
        .isEqualTo(booleanParameterDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      BooleanParameterDTO requestBody,
      BooleanParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(BooleanParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      BooleanParameterDTO requestBody,
      BooleanParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(BooleanParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      BooleanParameterDTO requestBody,
      BooleanParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(BooleanParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<BooleanParameterDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(BooleanParameterDTO.class)
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

  private BooleanParameterDTO generateAnotherBooleanParameterDTO() {
    BooleanParameterDTO anotherBooleanParameterDTO = new BooleanParameterDTO();
    anotherBooleanParameterDTO.setId(UUID.randomUUID());
    anotherBooleanParameterDTO.setDefaultValue(false);
    return anotherBooleanParameterDTO;
  }
}
