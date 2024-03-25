package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.ParameterTypeDefinitionDTO;
import ai.turintech.modelcatalog.facade.ParameterTypeDefinitionFacade;
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
public class ParameterTypeDefinitionResourceTest extends BasicRestTest {

  @MockBean private ParameterTypeDefinitionFacade parameterTypeDefinitionFacade;

  private UUID uuid;
  private ParameterTypeDefinitionDTO parameterTypeDefinitionDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    parameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    parameterTypeDefinitionDTO.setId(uuid);
    parameterTypeDefinitionDTO.setOrdering(1);
  }

  @Test
  public void findByIdTest() {
    Mono<ParameterTypeDefinitionDTO> parameterTypeDefinitionDTOMono =
        Mono.just(parameterTypeDefinitionDTO);
    when(parameterTypeDefinitionFacade.findOne(any(UUID.class)))
        .thenReturn(parameterTypeDefinitionDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/parameter-type-definitions/{id}"),
            request -> ServerResponse.ok().bodyValue(parameterTypeDefinitionDTO));
    executeGetAndExpect(route, "/parameter-type-definitions/{id}", parameterTypeDefinitionDTO);
  }

  @Test
  public void createParameterTypeDefinitionTest() {
    when(parameterTypeDefinitionFacade.save(any(ParameterTypeDefinitionDTO.class)))
        .thenReturn(Mono.just(parameterTypeDefinitionDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/parameter-type-definitions"),
            request ->
                ServerResponse.created(URI.create("/parameter-type-definitions"))
                    .bodyValue(parameterTypeDefinitionDTO));

    executePostAndExpect(
        route,
        "/parameter-type-definitions",
        parameterTypeDefinitionDTO,
        parameterTypeDefinitionDTO);
  }

  @Test
  public void updateParameterTypeDefinitionTest() {
    when(parameterTypeDefinitionFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterTypeDefinitionFacade.update(any(ParameterTypeDefinitionDTO.class)))
        .thenReturn(Mono.just(parameterTypeDefinitionDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/parameter-type-definitions/{id}"),
            req -> ServerResponse.ok().bodyValue(parameterTypeDefinitionDTO));

    executePutAndExpect(
        route,
        "/parameter-type-definitions/{id}",
        parameterTypeDefinitionDTO,
        parameterTypeDefinitionDTO);
  }

  @Test
  public void partialUpdateParameterTypeDefinitionTest() {
    when(parameterTypeDefinitionFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterTypeDefinitionFacade.partialUpdate(any(ParameterTypeDefinitionDTO.class)))
        .thenReturn(Mono.just(parameterTypeDefinitionDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/parameter-type-definitions/{id}"),
            req -> ServerResponse.ok().bodyValue(parameterTypeDefinitionDTO));

    executePatchAndExpect(
        route,
        "/parameter-type-definitions/{id}",
        parameterTypeDefinitionDTO,
        parameterTypeDefinitionDTO);
  }

  @Test
  public void findAllParameterTypeDefinitionsTest() {
    List<ParameterTypeDefinitionDTO> parameterTypeDefinitionDTOList =
        Arrays.asList(parameterTypeDefinitionDTO, generateAnotherParameterTypeDefinitionDTO());
    when(parameterTypeDefinitionFacade.findAll())
        .thenReturn(Flux.fromIterable(parameterTypeDefinitionDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/parameter-type-definitions"),
            req ->
                ServerResponse.ok()
                    .body(
                        parameterTypeDefinitionFacade.findAll(), ParameterTypeDefinitionDTO.class));
    executeGetAndExpectMultiple(
        route, "/parameter-type-definitions", parameterTypeDefinitionDTOList);
  }

  @Test
  public void deleteParameterTypeDefinitionTest() {
    when(parameterTypeDefinitionFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterTypeDefinitionFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/parameter-type-definitions/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/parameter-type-definitions/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route, String url, ParameterTypeDefinitionDTO parameterTypeDefinitionDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterTypeDefinitionDTO.class)
        .isEqualTo(parameterTypeDefinitionDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterTypeDefinitionDTO requestBody,
      ParameterTypeDefinitionDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ParameterTypeDefinitionDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterTypeDefinitionDTO requestBody,
      ParameterTypeDefinitionDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterTypeDefinitionDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterTypeDefinitionDTO requestBody,
      ParameterTypeDefinitionDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterTypeDefinitionDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<ParameterTypeDefinitionDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ParameterTypeDefinitionDTO.class)
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

  private ParameterTypeDefinitionDTO generateAnotherParameterTypeDefinitionDTO() {
    ParameterTypeDefinitionDTO anotherParameterTypeDefinitionDTO = new ParameterTypeDefinitionDTO();
    anotherParameterTypeDefinitionDTO.setId(UUID.randomUUID());
    anotherParameterTypeDefinitionDTO.setOrdering(10);
    return anotherParameterTypeDefinitionDTO;
  }
}
