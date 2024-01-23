package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ai.turintech.modelcatalog.dto.ParameterDTO;
import ai.turintech.modelcatalog.facade.ParameterFacade;
import ai.turintech.modelcatalog.rest.resource.ParameterResource;
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
import reactor.core.publisher.Mono;

@SpringJUnitConfig(TestRestConfig.class)
class ParameterResourceTest extends BasicRestTest {

  @Mock private ParameterFacade parameterFacade;

  @InjectMocks private ParameterResource resource;

  private UUID uuid;
  private ParameterDTO parameterDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Sample data
    uuid = UUID.randomUUID();
    parameterDTO = new ParameterDTO();
    parameterDTO.setId(uuid);
    parameterDTO.setName("TestParameter");
    parameterDTO.setName("TestValue");
  }

  @Test
  void createParameterTest() {
    when(parameterFacade.save(any(ParameterDTO.class))).thenReturn(Mono.just(parameterDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/api/parameters"),
            request ->
                ServerResponse.created(URI.create("/api/parameters")).bodyValue(parameterDTO));

    executePostAndExpect(route, "/api/parameters", parameterDTO, parameterDTO);
  }

  @Test
  void updateParameterTest() {
    when(parameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterFacade.update(any(ParameterDTO.class))).thenReturn(Mono.just(parameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/api/parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(parameterDTO));

    executePutAndExpect(route, "/api/parameters/{id}", parameterDTO, parameterDTO);
  }

  @Test
  void partialUpdateParameterTest() {
    when(parameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterFacade.partialUpdate(any(ParameterDTO.class)))
        .thenReturn(Mono.just(parameterDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/api/parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(parameterDTO));

    executePatchAndExpect(route, "/api/parameters/{id}", parameterDTO, parameterDTO);
  }

  @Test
  void getAllParametersTest() {
    List<ParameterDTO> parameterDTOList =
        Arrays.asList(parameterDTO, generateAnotherParameterDTO());
    when(parameterFacade.findAllPageable(any())).thenReturn(Mono.just(parameterDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/parameters/non-stream"),
            req ->
                ServerResponse.ok()
                    .body(parameterFacade.findAllPageable(any()), ParameterDTO.class));
    executeGetAndExpectMultiple(route, "/api/parameters/non-stream", parameterDTOList);
  }

  @Test
  void getParameterTest() {
    Mono<ParameterDTO> parameterDTOMono = Mono.just(parameterDTO);
    when(parameterFacade.findOne(uuid)).thenReturn(parameterDTOMono);

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/parameters/{id}"),
            req -> ServerResponse.ok().bodyValue(parameterDTO));
    executeGetAndExpect(route, "/api/parameters/{id}", parameterDTO);
  }

  @Test
  void deleteParameterTest() {
    when(parameterFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/api/parameters/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/api/parameters/{id}");
  }

  protected void executeGetAndExpect(
      RouterFunction<ServerResponse> route, String url, ParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterDTO.class)
        .isEqualTo(responseBody);
  }

  protected void executePostAndExpect(
      RouterFunction<ServerResponse> route,
      String url,
      ParameterDTO requestBody,
      ParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ParameterDTO.class)
        .isEqualTo(responseBody);
  }

  protected void executePutAndExpect(
      RouterFunction<ServerResponse> route,
      String url,
      ParameterDTO requestBody,
      ParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterDTO.class)
        .isEqualTo(responseBody);
  }

  protected void executePatchAndExpect(
      RouterFunction<ServerResponse> route,
      String url,
      ParameterDTO requestBody,
      ParameterDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterDTO.class)
        .isEqualTo(responseBody);
  }

  protected void executeGetAndExpectMultiple(
      RouterFunction<ServerResponse> route, String url, List<ParameterDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ParameterDTO.class)
        .isEqualTo(responseBody);
  }

  protected void executeDeleteAndExpect(RouterFunction<ServerResponse> route, String url) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .delete()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isNoContent();
  }

  private ParameterDTO generateAnotherParameterDTO() {
    ParameterDTO anotherParameterTO = new ParameterDTO();
    anotherParameterTO.setId(UUID.randomUUID());
    anotherParameterTO.setName("AnotherParameter");
    return anotherParameterTO;
  }
}
