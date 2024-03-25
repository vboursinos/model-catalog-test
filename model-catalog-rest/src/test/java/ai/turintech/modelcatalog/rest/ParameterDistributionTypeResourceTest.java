package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.ParameterDistributionTypeDTO;
import ai.turintech.modelcatalog.facade.ParameterDistributionTypeFacade;
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
public class ParameterDistributionTypeResourceTest extends BasicRestTest {

  @MockBean private ParameterDistributionTypeFacade parameterDistributionTypeFacade;

  private UUID uuid;
  private ParameterDistributionTypeDTO parameterDistributionTypeDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    parameterDistributionTypeDTO = new ParameterDistributionTypeDTO();
    parameterDistributionTypeDTO.setId(uuid);
    parameterDistributionTypeDTO.setName("test_parameter_distribution_type");
  }

  @Test
  public void findByIdTest() {
    Mono<ParameterDistributionTypeDTO> parameterDistributionTypeDTOMono =
        Mono.just(parameterDistributionTypeDTO);
    when(parameterDistributionTypeFacade.findOne(any(UUID.class)))
        .thenReturn(parameterDistributionTypeDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/parameter-distribution-types/{id}"),
            request -> ServerResponse.ok().bodyValue(parameterDistributionTypeDTO));
    executeGetAndExpect(route, "/parameter-distribution-types/{id}", parameterDistributionTypeDTO);
  }

  @Test
  public void createParameterDistributionTypeTest() {
    when(parameterDistributionTypeFacade.save(any(ParameterDistributionTypeDTO.class)))
        .thenReturn(Mono.just(parameterDistributionTypeDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/parameter-distribution-types"),
            request ->
                ServerResponse.created(URI.create("/parameter-distribution-types"))
                    .bodyValue(parameterDistributionTypeDTO));

    executePostAndExpect(
        route,
        "/parameter-distribution-types",
        parameterDistributionTypeDTO,
        parameterDistributionTypeDTO);
  }

  @Test
  public void updateParameterDistributionTypeTest() {
    when(parameterDistributionTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterDistributionTypeFacade.update(any(ParameterDistributionTypeDTO.class)))
        .thenReturn(Mono.just(parameterDistributionTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/parameter-distribution-types/{id}"),
            req -> ServerResponse.ok().bodyValue(parameterDistributionTypeDTO));

    executePutAndExpect(
        route,
        "/parameter-distribution-types/{id}",
        parameterDistributionTypeDTO,
        parameterDistributionTypeDTO);
  }

  @Test
  public void partialUpdateParameterDistributionTypeTest() {
    when(parameterDistributionTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterDistributionTypeFacade.partialUpdate(any(ParameterDistributionTypeDTO.class)))
        .thenReturn(Mono.just(parameterDistributionTypeDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/parameter-distribution-types/{id}"),
            req -> ServerResponse.ok().bodyValue(parameterDistributionTypeDTO));

    executePatchAndExpect(
        route,
        "/parameter-distribution-types/{id}",
        parameterDistributionTypeDTO,
        parameterDistributionTypeDTO);
  }

  @Test
  public void findAllParameterDistributionTypesTest() {
    List<ParameterDistributionTypeDTO> parameterDistributionTypeDTOList =
        Arrays.asList(parameterDistributionTypeDTO, generateAnotherParameterDistributionTypeDTO());
    when(parameterDistributionTypeFacade.findAll())
        .thenReturn(Flux.fromIterable(parameterDistributionTypeDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/parameter-distribution-types"),
            req ->
                ServerResponse.ok()
                    .body(
                        parameterDistributionTypeFacade.findAll(),
                        ParameterDistributionTypeDTO.class));
    executeGetAndExpectMultiple(
        route, "/parameter-distribution-types", parameterDistributionTypeDTOList);
  }

  @Test
  public void deleteParameterDistributionTypeTest() {
    when(parameterDistributionTypeFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(parameterDistributionTypeFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/parameter-distribution-types/{id}"),
            req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/parameter-distribution-types/{id}");
  }

  private void executeGetAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterDistributionTypeDTO parameterDistributionTypeDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterDistributionTypeDTO.class)
        .isEqualTo(parameterDistributionTypeDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterDistributionTypeDTO requestBody,
      ParameterDistributionTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ParameterDistributionTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterDistributionTypeDTO requestBody,
      ParameterDistributionTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterDistributionTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route,
      String url,
      ParameterDistributionTypeDTO requestBody,
      ParameterDistributionTypeDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ParameterDistributionTypeDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<ParameterDistributionTypeDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ParameterDistributionTypeDTO.class)
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

  private ParameterDistributionTypeDTO generateAnotherParameterDistributionTypeDTO() {
    ParameterDistributionTypeDTO anotherParameterDistributionTypeDTO =
        new ParameterDistributionTypeDTO();
    anotherParameterDistributionTypeDTO.setId(UUID.randomUUID());
    anotherParameterDistributionTypeDTO.setName("another_test_structure_type");
    return anotherParameterDistributionTypeDTO;
  }
}
