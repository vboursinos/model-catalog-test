package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.IntegerParameterValueDTO;
import ai.turintech.modelcatalog.facade.IntegerParameterValueFacade;
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
public class IntegerParameterValueResourceTest extends BasicRestTest {

  @MockBean private IntegerParameterValueFacade integerParameterValueFacade;

  private UUID uuid;
  private IntegerParameterValueDTO integerParameterValueDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    integerParameterValueDTO = new IntegerParameterValueDTO();
    integerParameterValueDTO.setId(uuid);
    integerParameterValueDTO.setLower(42);
    integerParameterValueDTO.setUpper(420);
  }

  @Test
  public void findByIdTest() {
    Mono<IntegerParameterValueDTO> integerParameterValueDTOMono =
        Mono.just(integerParameterValueDTO);
    when(integerParameterValueFacade.findOne(any(UUID.class)))
        .thenReturn(integerParameterValueDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/integer-parameter-values/{id}"),
            request -> ServerResponse.ok().bodyValue(integerParameterValueDTO));
    executeGetAndExpect(route, "/api/integer-parameter-values/{id}", integerParameterValueDTO);
  }

  @Test
  public void createIntegerParameterValueTest() {
    when(integerParameterValueFacade.save(any(IntegerParameterValueDTO.class)))
        .thenReturn(Mono.just(integerParameterValueDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/api/integer-parameter-values"),
            request ->
                ServerResponse.created(URI.create("/api/integer-parameter-values"))
                    .bodyValue(integerParameterValueDTO));

    executePostAndExpect(
        route, "/api/integer-parameter-values", integerParameterValueDTO, integerParameterValueDTO);
  }

  @Test
  public void updateIntegerParameterValueTest() {
    when(integerParameterValueFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(integerParameterValueFacade.update(any(IntegerParameterValueDTO.class)))
        .thenReturn(Mono.just(integerParameterValueDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/api/integer-parameter-values/{id}"),
            req -> ServerResponse.ok().bodyValue(integerParameterValueDTO));

    executePutAndExpect(
        route,
        "/api/integer-parameter-values/{id}",
        integerParameterValueDTO,
        integerParameterValueDTO);
  }

  @Test
  public void partialUpdateIntegerParameterValueTest() {
    when(integerParameterValueFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(integerParameterValueFacade.partialUpdate(any(IntegerParameterValueDTO.class)))
        .thenReturn(Mono.just(integerParameterValueDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/api/integer-parameter-values/{id}"),
            req -> ServerResponse.ok().bodyValue(integerParameterValueDTO));

    executePatchAndExpect(
        route,
        "/api/integer-parameter-values/{id}",
        integerParameterValueDTO,
        integerParameterValueDTO);
  }

  @Test
  public void findAllIntegerParameterValuesTest() {
    List<IntegerParameterValueDTO> integerParameterValueDTOList =
        Arrays.asList(integerParameterValueDTO, generateAnotherIntegerParameterValueDTO());
    when(integerParameterValueFacade.findAll())
        .thenReturn(Flux.fromIterable(integerParameterValueDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/api/integer-parameter-values"),
            req ->
                ServerResponse.ok()
                    .body(integerParameterValueFacade.findAll(), IntegerParameterValueDTO.class));
    executeGetAndExpectMultiple(
        route, "/api/integer-parameter-values", integerParameterValueDTOList);
  }

  @Test
  public void deleteIntegerParameterValueTest() {
    when(integerParameterValueFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(integerParameterValueFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/api/integer-parameter-values/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/api/integer-parameter-values/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, IntegerParameterValueDTO integerParameterValueDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(IntegerParameterValueDTO.class)
        .isEqualTo(integerParameterValueDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      IntegerParameterValueDTO requestBody,
      IntegerParameterValueDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(IntegerParameterValueDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      IntegerParameterValueDTO requestBody,
      IntegerParameterValueDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(IntegerParameterValueDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      IntegerParameterValueDTO requestBody,
      IntegerParameterValueDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(IntegerParameterValueDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<IntegerParameterValueDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(IntegerParameterValueDTO.class)
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

  private IntegerParameterValueDTO generateAnotherIntegerParameterValueDTO() {
    IntegerParameterValueDTO anotherIntegerParameterValueDTO = new IntegerParameterValueDTO();
    anotherIntegerParameterValueDTO.setId(UUID.randomUUID());
    anotherIntegerParameterValueDTO.setUpper(2);
    anotherIntegerParameterValueDTO.setLower(1);
    return anotherIntegerParameterValueDTO;
  }
}
