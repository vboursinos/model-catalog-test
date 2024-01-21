package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.facade.MetricFacade;
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
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringJUnitConfig(TestRestConfig.class)
public class MetricResourceTest extends BasicRestTest {

  @MockBean private MetricFacade metricFacade;

  private UUID uuid;
  private MetricDTO metricDTO;

  @BeforeEach
  public void setup() {
    uuid = UUID.randomUUID();
    metricDTO = new MetricDTO();
    metricDTO.setId(uuid);
    metricDTO.setMetric("test_metric");
  }

  @Test
  public void findByIdTest() {
    Mono<MetricDTO> metricDTOMono = Mono.just(metricDTO);
    when(metricFacade.findOne(any(UUID.class))).thenReturn(metricDTOMono);
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/metrics/{id}"),
            request -> ServerResponse.ok().bodyValue(metricDTO));
    executeGetAndExpect(route, "/metrics/{id}", metricDTO);
  }

  @Test
  public void createMetricTest() {
    when(metricFacade.save(any(MetricDTO.class))).thenReturn(Mono.just(metricDTO));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.POST("/metrics"),
            request -> ServerResponse.created(URI.create("/metrics")).bodyValue(metricDTO));

    executePostAndExpect(route, "/metrics", metricDTO, metricDTO);
  }

  @Test
  public void updateMetricTest() {
    when(metricFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(metricFacade.update(any(MetricDTO.class))).thenReturn(Mono.just(metricDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PUT("/metrics/{id}"),
            req -> ServerResponse.ok().bodyValue(metricDTO));

    executePutAndExpect(route, "/metrics/{id}", metricDTO, metricDTO);
  }

  @Test
  public void partialUpdateMetricTest() {
    when(metricFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(metricFacade.partialUpdate(any(MetricDTO.class))).thenReturn(Mono.just(metricDTO));
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.PATCH("/metrics/{id}"),
            req -> ServerResponse.ok().bodyValue(metricDTO));

    executePatchAndExpect(route, "/metrics/{id}", metricDTO, metricDTO);
  }

  @Test
  public void findAllMetricsTest() {
    List<MetricDTO> metricDTOList = Arrays.asList(metricDTO, generateAnotherMetricDTO());
    when(metricFacade.findAll()).thenReturn(Flux.fromIterable(metricDTOList));

    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.GET("/metrics"),
            req -> ServerResponse.ok().body(metricFacade.findAll(), MetricDTO.class));
    executeGetAndExpectMultiple(route, "/metrics", metricDTOList);
  }

  @Test
  public void deleteMetricTest() {
    when(metricFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(metricFacade.delete(uuid)).thenReturn(Mono.empty());
    RouterFunction<ServerResponse> route =
        RouterFunctions.route(
            RequestPredicates.DELETE("/metrics/{id}"), req -> ServerResponse.noContent().build());

    executeDeleteAndExpect(route, "/metrics/{id}");
  }

  private void executeGetAndExpect(RouterFunction<?> route, String url, MetricDTO metricDTO) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url, uuid.toString())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MetricDTO.class)
        .isEqualTo(metricDTO);
  }

  private void executePostAndExpect(
      RouterFunction<?> route, String url, MetricDTO requestBody, MetricDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(MetricDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePutAndExpect(
      RouterFunction<?> route, String url, MetricDTO requestBody, MetricDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .put()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MetricDTO.class)
        .isEqualTo(responseBody);
  }

  private void executePatchAndExpect(
      RouterFunction<?> route, String url, MetricDTO requestBody, MetricDTO responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .patch()
        .uri(url, uuid.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MetricDTO.class)
        .isEqualTo(responseBody);
  }

  private void executeGetAndExpectMultiple(
      RouterFunction<?> route, String url, List<MetricDTO> responseBody) {
    WebTestClient.bindToRouterFunction(route)
        .build()
        .get()
        .uri(url)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(MetricDTO.class)
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

  private MetricDTO generateAnotherMetricDTO() {
    MetricDTO anotherMetricDTO = new MetricDTO();
    anotherMetricDTO.setId(UUID.randomUUID());
    anotherMetricDTO.setMetric("another_test_metric");
    return anotherMetricDTO;
  }
}
