package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ai.turintech.modelcatalog.dto.FloatParameterDTO;
import ai.turintech.modelcatalog.facade.FloatParameterFacade;
import ai.turintech.modelcatalog.rest.resource.FloatParameterResource;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
class FloatParameterResourceTest extends BasicRestTest {

  @Mock private FloatParameterFacade floatParameterFacade;

  @InjectMocks private FloatParameterResource resource;

  private UUID uuid;
  private FloatParameterDTO floatParameterDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Sample data
    uuid = UUID.randomUUID();
    floatParameterDTO = new FloatParameterDTO();
    floatParameterDTO.setId(uuid);
    floatParameterDTO.setDefaultValue(10.1);
  }

  @Test
  void findByIdTest() {
    Mono<FloatParameterDTO> floatParameterDTOMono = Mono.just(floatParameterDTO);
    when(floatParameterFacade.findOne(any(UUID.class))).thenReturn(floatParameterDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/float-parameters/{id}"),
            request -> ServerResponse.ok().bodyValue(floatParameterDTO));
    executeGetAndExpect(route, "/api/float-parameters/{id}", floatParameterDTO);
  }

  @Test
  void createFloatParameterTest() {
    when(floatParameterFacade.save(any(FloatParameterDTO.class)))
        .thenReturn(Mono.just(floatParameterDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/api/float-parameters"),
            request ->
                ServerResponse.created(URI.create("/api/float-parameters"))
                    .bodyValue(floatParameterDTO));

    executePostAndExpect(route, "/api/float-parameters", floatParameterDTO, floatParameterDTO);
  }

  @Test
  void updateFloatParameterTest() {
    when(floatParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(floatParameterFacade.update(any(FloatParameterDTO.class)))
        .thenReturn(Mono.just(floatParameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/api/float-parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(floatParameterDTO));

    executePutAndExpect(route, "/api/float-parameters/{id}", floatParameterDTO, floatParameterDTO);
  }

  @Test
  void partialUpdateFloatParameterTest() {
    when(floatParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(floatParameterFacade.partialUpdate(any(FloatParameterDTO.class)))
        .thenReturn(Mono.just(floatParameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/api/float-parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(floatParameterDTO));

    executePatchAndExpect(
        route, "/api/float-parameters/{id}", floatParameterDTO, floatParameterDTO);
  }

  @Test
  void findAllFloatParameterTest() {
    List<FloatParameterDTO> floatParameterDTOList =
        Arrays.asList(floatParameterDTO, generateAnotherFloatParameterDTO());
    when(floatParameterFacade.findAll()).thenReturn(Flux.fromIterable(floatParameterDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/float-parameters"),
            req ->
                ServerResponse.ok().body(floatParameterFacade.findAll(), FloatParameterDTO.class));
    executeGetAndExpectMultiple(route, "/api/float-parameters", floatParameterDTOList);
  }

  @Test
  void deleteFloatParameterTest() {
    when(floatParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(floatParameterFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/api/float-parameters/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/api/float-parameters/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, FloatParameterDTO floatParameterDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(FloatParameterDTO.class)
        .isEqualTo(floatParameterDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      FloatParameterDTO requestBody,
      FloatParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(FloatParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      FloatParameterDTO requestBody,
      FloatParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(FloatParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      FloatParameterDTO requestBody,
      FloatParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(FloatParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<FloatParameterDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(FloatParameterDTO.class)
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

  private FloatParameterDTO generateAnotherFloatParameterDTO() {
    FloatParameterDTO anotherFloatParameterDTO = new FloatParameterDTO();
    anotherFloatParameterDTO.setId(UUID.randomUUID());
    anotherFloatParameterDTO.setDefaultValue(10.0);
    return anotherFloatParameterDTO;
  }
}
