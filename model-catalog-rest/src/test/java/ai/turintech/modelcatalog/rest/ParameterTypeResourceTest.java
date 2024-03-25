package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.ParameterTypeDTO;
import ai.turintech.modelcatalog.facade.ParameterTypeFacade;
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
public class ParameterTypeResourceTest extends BasicRestTest {

  @MockBean private ParameterTypeFacade parameterTypeFacade;

  private UUID uuid;
  private ParameterTypeDTO parameterTypeDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    parameterTypeDTO = new ParameterTypeDTO();
    parameterTypeDTO.setId(uuid);
    parameterTypeDTO.setName("test_parameter_type");
  }

  @Test
  public void findByIdTest() {
    Mono<ParameterTypeDTO> parameterTypeDTOMono = Mono.just(parameterTypeDTO);
    when(parameterTypeFacade.findOne(any(UUID.class))).thenReturn(parameterTypeDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/parameter-types/{id}"),
            request -> ServerResponse.ok().bodyValue(parameterTypeDTO));
    executeGetAndExpect(route, "/parameter-types/{id}", parameterTypeDTO);
  }

  @Test
  public void createParameterTypeTest() {
    when(parameterTypeFacade.save(any(ParameterTypeDTO.class)))
        .thenReturn(Mono.just(parameterTypeDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/parameter-types"),
            request ->
                ServerResponse.created(URI.create("/parameter-types")).bodyValue(parameterTypeDTO));

    executePostAndExpect(route, "/parameter-types", parameterTypeDTO, parameterTypeDTO);
  }

  @Test
  public void updateParameterTypeTest() {
    when(parameterTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterTypeFacade.update(any(ParameterTypeDTO.class)))
        .thenReturn(Mono.just(parameterTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/parameter-types/{id}"),
            req -> ServerResponse.ok().bodyValue(parameterTypeDTO));

    executePutAndExpect(route, "/parameter-types/{id}", parameterTypeDTO, parameterTypeDTO);
  }

  @Test
  public void partialUpdateParameterTypeTest() {
    when(parameterTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterTypeFacade.partialUpdate(any(ParameterTypeDTO.class)))
        .thenReturn(Mono.just(parameterTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/parameter-types/{id}"),
            req -> ServerResponse.ok().bodyValue(parameterTypeDTO));

    executePatchAndExpect(route, "/parameter-types/{id}", parameterTypeDTO, parameterTypeDTO);
  }

  @Test
  public void findAllParameterTypesTest() {
    List<ParameterTypeDTO> parameterTypeDTOList =
        Arrays.asList(parameterTypeDTO, generateAnotherParameterTypeDTO());
    when(parameterTypeFacade.findAll()).thenReturn(Flux.fromIterable(parameterTypeDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/parameter-types"),
            req -> ServerResponse.ok().body(parameterTypeFacade.findAll(), ParameterTypeDTO.class));
    executeGetAndExpectMultiple(route, "/parameter-types", parameterTypeDTOList);
  }

  @Test
  public void deleteParameterTypeTest() {
    when(parameterTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterTypeFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/parameter-types/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/parameter-types/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, ParameterTypeDTO parameterTypeDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterTypeDTO.class)
        .isEqualTo(parameterTypeDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterTypeDTO requestBody,
      ParameterTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ParameterTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterTypeDTO requestBody,
      ParameterTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterTypeDTO requestBody,
      ParameterTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<ParameterTypeDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ParameterTypeDTO.class)
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

  private ParameterTypeDTO generateAnotherParameterTypeDTO() {
    ParameterTypeDTO anotherParameterTypeDTO = new ParameterTypeDTO();
    anotherParameterTypeDTO.setId(UUID.randomUUID());
    anotherParameterTypeDTO.setName("another_parameter_type");
    return anotherParameterTypeDTO;
  }
}
