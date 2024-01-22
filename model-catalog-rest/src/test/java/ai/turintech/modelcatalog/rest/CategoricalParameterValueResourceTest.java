package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.CategoricalParameterValueDTO;
import ai.turintech.modelcatalog.facade.CategoricalParameterValueFacade;
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
public class CategoricalParameterValueResourceTest extends BasicRestTest {

  @MockBean private CategoricalParameterValueFacade categoricalParameterValueFacade;

  private UUID uuid;
  private CategoricalParameterValueDTO categoricalParameterValueDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    categoricalParameterValueDTO = new CategoricalParameterValueDTO();
    categoricalParameterValueDTO.setId(uuid);
    categoricalParameterValueDTO.setValue("test_categorical_value");
  }

  @Test
  public void findByIdTest() {
    Mono<CategoricalParameterValueDTO> categoricalParameterValueDTOMono =
        Mono.just(categoricalParameterValueDTO);
    when(categoricalParameterValueFacade.findOne(any(UUID.class)))
        .thenReturn(categoricalParameterValueDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/categorical-parameter-values/{id}"),
            request -> ServerResponse.ok().bodyValue(categoricalParameterValueDTO));
    executeGetAndExpect(
        route, "/api/categorical-parameter-values/{id}", categoricalParameterValueDTO);
  }

  @Test
  public void createCategoricalParameterValueTest() {
    when(categoricalParameterValueFacade.save(any(CategoricalParameterValueDTO.class)))
        .thenReturn(Mono.just(categoricalParameterValueDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/api/categorical-parameter-values"),
            request ->
                ServerResponse.created(URI.create("/api/categorical-parameter-values"))
                    .bodyValue(categoricalParameterValueDTO));

    executePostAndExpect(
        route,
        "/api/categorical-parameter-values",
        categoricalParameterValueDTO,
        categoricalParameterValueDTO);
  }

  @Test
  public void updateCategoricalParameterValueTest() {
    when(categoricalParameterValueFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(categoricalParameterValueFacade.update(any(CategoricalParameterValueDTO.class)))
        .thenReturn(Mono.just(categoricalParameterValueDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/api/categorical-parameter-values/{id}"),
            req -> ServerResponse.ok().bodyValue(categoricalParameterValueDTO));

    executePutAndExpect(
        route,
        "/api/categorical-parameter-values/{id}",
        categoricalParameterValueDTO,
        categoricalParameterValueDTO);
  }

  @Test
  public void partialUpdateCategoricalParameterValueTest() {
    when(categoricalParameterValueFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(categoricalParameterValueFacade.partialUpdate(any(CategoricalParameterValueDTO.class)))
        .thenReturn(Mono.just(categoricalParameterValueDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/api/categorical-parameter-values/{id}"),
            req -> ServerResponse.ok().bodyValue(categoricalParameterValueDTO));

    executePatchAndExpect(
        route,
        "/api/categorical-parameter-values/{id}",
        categoricalParameterValueDTO,
        categoricalParameterValueDTO);
  }

  @Test
  public void findAllCategoricalParameterValuesTest() {
    List<CategoricalParameterValueDTO> categoricalParameterValueDTOList =
        Arrays.asList(categoricalParameterValueDTO, generateAnotherCategoricalParameterValueDTO());
    when(categoricalParameterValueFacade.findAll())
        .thenReturn(Flux.fromIterable(categoricalParameterValueDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/categorical-parameter-values"),
            req ->
                ServerResponse.ok()
                    .body(
                        categoricalParameterValueFacade.findAll(),
                        CategoricalParameterValueDTO.class));
    executeGetAndExpectMultiple(
        route, "/api/categorical-parameter-values", categoricalParameterValueDTOList);
  }

  @Test
  public void deleteCategoricalParameterValueTest() {
    when(categoricalParameterValueFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(categoricalParameterValueFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/api/categorical-parameter-values/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/api/categorical-parameter-values/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route,
      String url,
      CategoricalParameterValueDTO categoricalParameterValueDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(CategoricalParameterValueDTO.class)
        .isEqualTo(categoricalParameterValueDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      CategoricalParameterValueDTO requestBody,
      CategoricalParameterValueDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(CategoricalParameterValueDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      CategoricalParameterValueDTO requestBody,
      CategoricalParameterValueDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(CategoricalParameterValueDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      CategoricalParameterValueDTO requestBody,
      CategoricalParameterValueDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(CategoricalParameterValueDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<CategoricalParameterValueDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(CategoricalParameterValueDTO.class)
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

  private CategoricalParameterValueDTO generateAnotherCategoricalParameterValueDTO() {
    CategoricalParameterValueDTO anotherCategoricalParameterValueDTO =
        new CategoricalParameterValueDTO();
    anotherCategoricalParameterValueDTO.setId(UUID.randomUUID());
    anotherCategoricalParameterValueDTO.setValue("another_categorical_value");
    return anotherCategoricalParameterValueDTO;
  }
}
