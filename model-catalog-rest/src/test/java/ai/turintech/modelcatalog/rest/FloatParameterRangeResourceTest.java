package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.FloatParameterRangeDTO;
import ai.turintech.modelcatalog.facade.FloatParameterRangeFacade;
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
public class FloatParameterRangeResourceTest extends BasicRestTest {

  @MockBean private FloatParameterRangeFacade floatParameterRangeFacade;

  private UUID uuid;
  private FloatParameterRangeDTO floatParameterRangeDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    floatParameterRangeDTO = new FloatParameterRangeDTO();
    floatParameterRangeDTO.setId(uuid);
    floatParameterRangeDTO.setLower(0.1);
    floatParameterRangeDTO.setUpper(1.0);
  }

  @Test
  public void findByIdTest() {
    Mono<FloatParameterRangeDTO> floatParameterRangeDTOMono = Mono.just(floatParameterRangeDTO);
    when(floatParameterRangeFacade.findOne(any(UUID.class))).thenReturn(floatParameterRangeDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/float-parameter-ranges/{id}"),
            request -> ServerResponse.ok().bodyValue(floatParameterRangeDTO));
    executeGetAndExpect(route, "/api/float-parameter-ranges/{id}", floatParameterRangeDTO);
  }

  @Test
  public void createFloatParameterRangeTest() {
    when(floatParameterRangeFacade.save(any(FloatParameterRangeDTO.class)))
        .thenReturn(Mono.just(floatParameterRangeDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/api/float-parameter-ranges"),
            request ->
                ServerResponse.created(URI.create("/api/float-parameter-ranges"))
                    .bodyValue(floatParameterRangeDTO));

    executePostAndExpect(
        route, "/api/float-parameter-ranges", floatParameterRangeDTO, floatParameterRangeDTO);
  }

  @Test
  public void updateFloatParameterRangeTest() {
    when(floatParameterRangeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(floatParameterRangeFacade.update(any(FloatParameterRangeDTO.class)))
        .thenReturn(Mono.just(floatParameterRangeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/api/float-parameter-ranges/{id}"),
            req -> ServerResponse.ok().bodyValue(floatParameterRangeDTO));

    executePutAndExpect(
        route, "/api/float-parameter-ranges/{id}", floatParameterRangeDTO, floatParameterRangeDTO);
  }

  @Test
  public void partialUpdateFloatParameterRangeTest() {
    when(floatParameterRangeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(floatParameterRangeFacade.partialUpdate(any(FloatParameterRangeDTO.class)))
        .thenReturn(Mono.just(floatParameterRangeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/api/float-parameter-ranges/{id}"),
            req -> ServerResponse.ok().bodyValue(floatParameterRangeDTO));

    executePatchAndExpect(
        route, "/api/float-parameter-ranges/{id}", floatParameterRangeDTO, floatParameterRangeDTO);
  }

  @Test
  public void findAllFloatParameterRangesTest() {
    List<FloatParameterRangeDTO> floatParameterRangeDTOList =
        Arrays.asList(floatParameterRangeDTO, generateAnotherFloatParameterRangeDTO());
    when(floatParameterRangeFacade.findAll())
        .thenReturn(Flux.fromIterable(floatParameterRangeDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/float-parameter-ranges"),
            req ->
                ServerResponse.ok()
                    .body(floatParameterRangeFacade.findAll(), FloatParameterRangeDTO.class));
    executeGetAndExpectMultiple(route, "/api/float-parameter-ranges", floatParameterRangeDTOList);
  }

  @Test
  public void deleteFloatParameterRangeTest() {
    when(floatParameterRangeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(floatParameterRangeFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/api/float-parameter-ranges/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/api/float-parameter-ranges/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, FloatParameterRangeDTO floatParameterRangeDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(FloatParameterRangeDTO.class)
        .isEqualTo(floatParameterRangeDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      FloatParameterRangeDTO requestBody,
      FloatParameterRangeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(FloatParameterRangeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      FloatParameterRangeDTO requestBody,
      FloatParameterRangeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(FloatParameterRangeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      FloatParameterRangeDTO requestBody,
      FloatParameterRangeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(FloatParameterRangeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<FloatParameterRangeDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(FloatParameterRangeDTO.class)
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

  private FloatParameterRangeDTO generateAnotherFloatParameterRangeDTO() {
    FloatParameterRangeDTO anotherFloatParameterRangeDTO = new FloatParameterRangeDTO();
    anotherFloatParameterRangeDTO.setId(UUID.randomUUID());
    anotherFloatParameterRangeDTO.setUpper(0.2);
    anotherFloatParameterRangeDTO.setLower(0.01);
    return anotherFloatParameterRangeDTO;
  }
}
