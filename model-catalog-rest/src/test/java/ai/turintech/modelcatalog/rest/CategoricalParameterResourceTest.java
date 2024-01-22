package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ai.turintech.modelcatalog.dto.CategoricalParameterDTO;
import ai.turintech.modelcatalog.facade.CategoricalParameterFacade;
import ai.turintech.modelcatalog.rest.resource.CategoricalParameterResource;
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
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class CategoricalParameterResourceTest extends BasicRestTest {

  @Mock private CategoricalParameterFacade categoricalParameterFacade;

  @InjectMocks private CategoricalParameterResource resource;

  private UUID uuid;
  private CategoricalParameterDTO categoricalParameterDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Sample data
    uuid = UUID.randomUUID();
    categoricalParameterDTO = new CategoricalParameterDTO();
    categoricalParameterDTO.setId(uuid);
    categoricalParameterDTO.setDefaultValue("test_categorical_value");
  }

  @Test
  void findByIdTest() {
    Mono<CategoricalParameterDTO> categoricalParameterDTOMono = Mono.just(categoricalParameterDTO);
    when(categoricalParameterFacade.findOne(any(UUID.class)))
        .thenReturn(categoricalParameterDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/categorical-parameters/{id}"),
            request -> ServerResponse.ok().bodyValue(categoricalParameterDTO));
    executeGetAndExpect(route, "/api/categorical-parameters/{id}", categoricalParameterDTO);
  }

  @Test
  void createCategoricalParameterTest() {
    when(categoricalParameterFacade.save(any(CategoricalParameterDTO.class)))
        .thenReturn(Mono.just(categoricalParameterDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/api/categorical-parameters"),
            request ->
                ServerResponse.created(URI.create("/api/categorical-parameters"))
                    .bodyValue(categoricalParameterDTO));

    executePostAndExpect(
        route, "/api/categorical-parameters", categoricalParameterDTO, categoricalParameterDTO);
  }

  @Test
  void updateCategoricalParameterTest() {
    when(categoricalParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(categoricalParameterFacade.update(any(CategoricalParameterDTO.class)))
        .thenReturn(Mono.just(categoricalParameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/api/categorical-parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(categoricalParameterDTO));

    executePutAndExpect(
        route,
        "/api/categorical-parameters/{id}",
        categoricalParameterDTO,
        categoricalParameterDTO);
  }

  @Test
  void partialUpdateCategoricalParameterTest() {
    when(categoricalParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(categoricalParameterFacade.partialUpdate(any(CategoricalParameterDTO.class)))
        .thenReturn(Mono.just(categoricalParameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/api/categorical-parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(categoricalParameterDTO));

    executePatchAndExpect(
        route,
        "/api/categorical-parameters/{id}",
        categoricalParameterDTO,
        categoricalParameterDTO);
  }

  @Test
  void findAllCategoricalParameterTest() {
    List<CategoricalParameterDTO> categoricalParameterDTOList =
        Arrays.asList(categoricalParameterDTO, generateAnotherCategoricalParameterDTO());
    when(categoricalParameterFacade.findAll())
        .thenReturn(Flux.fromIterable(categoricalParameterDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/categorical-parameters"),
            req ->
                ServerResponse.ok()
                    .body(categoricalParameterFacade.findAll(), CategoricalParameterDTO.class));
    executeGetAndExpectMultiple(route, "/api/categorical-parameters", categoricalParameterDTOList);
  }

  @Test
  void deleteCategoricalParameterTest() {
    when(categoricalParameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(categoricalParameterFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/api/categorical-parameters/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/api/categorical-parameters/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, CategoricalParameterDTO categoricalParameterDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(CategoricalParameterDTO.class)
        .isEqualTo(categoricalParameterDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      CategoricalParameterDTO requestBody,
      CategoricalParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(CategoricalParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      CategoricalParameterDTO requestBody,
      CategoricalParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(CategoricalParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      CategoricalParameterDTO requestBody,
      CategoricalParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(CategoricalParameterDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<CategoricalParameterDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(CategoricalParameterDTO.class)
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

  private CategoricalParameterDTO generateAnotherCategoricalParameterDTO() {
    CategoricalParameterDTO anotherCategoricalParameterDTO = new CategoricalParameterDTO();
    anotherCategoricalParameterDTO.setId(UUID.randomUUID());
    anotherCategoricalParameterDTO.setDefaultValue("another_categorical_value");
    return anotherCategoricalParameterDTO;
  }
}
