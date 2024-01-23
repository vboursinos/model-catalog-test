package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ai.turintech.modelcatalog.dto.IntegerParameterDTO;
import ai.turintech.modelcatalog.facade.IntegerParameterFacade;
import ai.turintech.modelcatalog.rest.resource.IntegerParameterResource;
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
class IntegerParameterResourceTest extends BasicRestTest {

  @Mock private IntegerParameterFacade integerParameterFacade;

  @InjectMocks private IntegerParameterResource resource;

  private UUID uuid;
  private IntegerParameterDTO integerParameterDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Sample data
    uuid = UUID.randomUUID();
    integerParameterDTO = new IntegerParameterDTO();
    integerParameterDTO.setId(uuid);
    integerParameterDTO.setDefaultValue(12);
  }

  @Test
  void findByIdTest() {
    Mono<IntegerParameterDTO> integerParameterDTOMono = Mono.just(integerParameterDTO);
    when(integerParameterFacade.findOne(any(UUID.class))).thenReturn(integerParameterDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/integer-parameters/{id}"),
            request -> ServerResponse.ok().bodyValue(integerParameterDTO));
    executeGetAndExpect(route, "/api/integer-parameters/{id}", integerParameterDTO);
  }

  @Test
  void createIntegerParameterTest() {
    when(integerParameterFacade.save(any(IntegerParameterDTO.class)))
        .thenReturn(Mono.just(integerParameterDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/api/integer-parameters"),
            request ->
                ServerResponse.created(URI.create("/api/integer-parameters"))
                    .bodyValue(integerParameterDTO));

    executePostAndExpect(
        route, "/api/integer-parameters", integerParameterDTO, integerParameterDTO);
  }

  @Test
  void updateIntegerParameterTest() {
    when(integerParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(integerParameterFacade.update(any(IntegerParameterDTO.class)))
        .thenReturn(Mono.just(integerParameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/api/integer-parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(integerParameterDTO));

    executePutAndExpect(
        route, "/api/integer-parameters/{id}", integerParameterDTO, integerParameterDTO);
  }

  @Test
  void partialUpdateIntegerParameterTest() {
    when(integerParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(integerParameterFacade.partialUpdate(any(IntegerParameterDTO.class)))
        .thenReturn(Mono.just(integerParameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/api/integer-parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(integerParameterDTO));

    executePatchAndExpect(
        route, "/api/integer-parameters/{id}", integerParameterDTO, integerParameterDTO);
  }

  @Test
  void findAllIntegerParameterTest() {
    List<IntegerParameterDTO> integerParameterDTOList =
        Arrays.asList(integerParameterDTO, generateAnotherIntegerParameterDTO());
    when(integerParameterFacade.findAll()).thenReturn(Flux.fromIterable(integerParameterDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/integer-parameters"),
            req ->
                ServerResponse.ok()
                    .body(integerParameterFacade.findAll(), IntegerParameterDTO.class));
    executeGetAndExpectMultiple(route, "/api/integer-parameters", integerParameterDTOList);
  }

  @Test
  void deleteIntegerParameterTest() {
    when(integerParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(integerParameterFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/api/integer-parameters/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/api/integer-parameters/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, IntegerParameterDTO integerParameterDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(IntegerParameterDTO.class)
        .isEqualTo(integerParameterDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      IntegerParameterDTO requestBody,
      IntegerParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(IntegerParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      IntegerParameterDTO requestBody,
      IntegerParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(IntegerParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      IntegerParameterDTO requestBody,
      IntegerParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(IntegerParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<IntegerParameterDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(IntegerParameterDTO.class)
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

  private IntegerParameterDTO generateAnotherIntegerParameterDTO() {
    IntegerParameterDTO anotherIntegerParameterDTO = new IntegerParameterDTO();
    anotherIntegerParameterDTO.setId(UUID.randomUUID());
    anotherIntegerParameterDTO.setDefaultValue(23);
    return anotherIntegerParameterDTO;
  }
}
