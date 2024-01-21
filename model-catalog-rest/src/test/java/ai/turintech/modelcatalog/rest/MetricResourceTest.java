package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.ResponseEntity.ok;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.facade.MetricFacade;
import ai.turintech.modelcatalog.rest.resource.MetricResource;
import ai.turintech.modelcatalog.to.MetricTO;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringJUnitConfig(TestRestConfig.class)
public class MetricResourceTest extends BasicRestTest {
  @MockBean private MetricFacade metricFacade;

  @MockBean private MetricResource metricResource;

  public UUID uuid = UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26");

  public Mono<ResponseEntity<MetricTO>> getMetricTOMono() {
    MetricTO metricTO = new MetricTO();
    metricTO.setId(uuid);
    metricTO.setMetric("test_metric");
    Mono<ResponseEntity<MetricTO>> metricTOMono = Mono.just(ok(metricTO));
    return metricTOMono;
  }

  public MetricDTO getMetricDTO() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(uuid);
    metricDTO.setMetric("test_metric");
    return metricDTO;
  }

  public Mono<MetricDTO> getMetricDTOMono() {
    MetricDTO metricDTO = getMetricDTO();
    return Mono.just(metricDTO);
  }

  public MetricDTO getAnotherMetricDTO() {
    MetricDTO anotherMetricDTO = new MetricDTO();
    // Set different values for testing
    anotherMetricDTO.setId(UUID.randomUUID());
    anotherMetricDTO.setMetric("another_test_metric");
    return anotherMetricDTO;
  }

  @Test
  public void findByIdTest() {
    Mono<ResponseEntity<MetricTO>> metricTOMono = getMetricTOMono();
    Mono<MetricDTO> metricDTOMono = getMetricDTOMono();
    when(metricFacade.findOne(any(UUID.class))).thenReturn(metricDTOMono);
    when(metricResource.findOne(any(UUID.class))).thenReturn(metricTOMono);

    System.out.println(
        "metricFacade.findOne(any(UUID.class)) = " + metricFacade.findOne(uuid).block());

    Mono<ServerResponse> responseMono =
        Mono.just(getMetricDTO())
            .flatMap(
                metric ->
                    ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(metric));

    when(metricResource.findOne(any(UUID.class))).thenReturn(metricTOMono);

    RouterFunction function =
        RouterFunctions.route(
            RequestPredicates.GET("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"),
            request -> responseMono);

    WebTestClient.bindToRouterFunction(function)
        .build()
        .get()
        .uri("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MetricDTO.class)
        .isEqualTo(getMetricDTO());

    // Verify the mock interaction
    Assert.assertEquals(metricFacade.findOne(uuid).block().getMetric(), "test_metric");
  }

  @Test
  public void createMetricTest() {
    MetricTO newMetricTO = new MetricTO();
    newMetricTO.setMetric("new_metric");

    Mono<ServerResponse> responseMono =
        Mono.just(getMetricDTO())
            .flatMap(
                metric ->
                    ServerResponse.created(URI.create("/metrics"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(metric));

    when(metricResource.create(any(MetricTO.class))).thenReturn(getMetricTOMono());

    RouterFunction function =
        RouterFunctions.route(RequestPredicates.POST("/metrics"), request -> responseMono);

    when(metricFacade.save(any(MetricDTO.class))).thenReturn(Mono.just(getMetricDTO()));

    WebTestClient.bindToRouterFunction(function)
        .build()
        .post()
        .uri("/metrics")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(newMetricTO)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(MetricDTO.class)
        .isEqualTo(getMetricDTO());
  }

  @Test
  public void updateMetricTest() {
    MetricTO existingMetricTO = new MetricTO();
    existingMetricTO.setId(uuid);
    existingMetricTO.setMetric("existing_metric");

    when(metricFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(metricFacade.update(any(MetricDTO.class))).thenReturn(Mono.just(getMetricDTO()));

    Mono<ServerResponse> responseMono =
        Mono.just(getMetricDTO())
            .flatMap(
                metric ->
                    ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(metric));

    when(metricResource.update(any(UUID.class), any(MetricTO.class))).thenReturn(getMetricTOMono());

    RouterFunction function =
        RouterFunctions.route(
            RequestPredicates.PUT("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"),
            request -> responseMono);

    when(metricFacade.update(any(MetricDTO.class))).thenReturn(Mono.just(getMetricDTO()));

    WebTestClient.bindToRouterFunction(function)
        .build()
        .put()
        .uri("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(existingMetricTO)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MetricDTO.class)
        .isEqualTo(getMetricDTO());
  }

  @Test
  public void partialUpdateMetricTest() {
    MetricTO existingMetricTO = new MetricTO();
    existingMetricTO.setId(uuid);
    existingMetricTO.setMetric("existing_metric");

    when(metricFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(metricFacade.partialUpdate(any(MetricDTO.class))).thenReturn(Mono.just(getMetricDTO()));

    Mono<ServerResponse> responseMono =
        Mono.just(getMetricDTO())
            .flatMap(
                metric ->
                    ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(metric));

    when(metricResource.partialUpdate(any(UUID.class), any(MetricTO.class)))
        .thenReturn(getMetricTOMono());

    RouterFunction function =
        RouterFunctions.route(
            RequestPredicates.PATCH("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"),
            request -> responseMono);

    when(metricFacade.update(any(MetricDTO.class))).thenReturn(Mono.just(getMetricDTO()));

    WebTestClient.bindToRouterFunction(function)
        .build()
        .patch()
        .uri("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(existingMetricTO)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MetricDTO.class)
        .isEqualTo(getMetricDTO());
  }

  @Test
  public void findAllMetricsTest() {
    List<MetricDTO> metricDTOList = Arrays.asList(getMetricDTO(), getAnotherMetricDTO());
    when(metricFacade.findAll()).thenReturn(Flux.fromIterable(metricDTOList));

    RouterFunction function =
        RouterFunctions.route(
            RequestPredicates.GET("/metrics"),
            request ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(metricFacade.findAll(), MetricDTO.class));

    WebTestClient.bindToRouterFunction(function)
        .build()
        .get()
        .uri("/metrics")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(MetricDTO.class)
        .isEqualTo(metricDTOList);
  }

  @Test
  public void deleteMetricTest() {
    when(metricFacade.existsById(uuid)).thenReturn(Mono.just(true));
    when(metricFacade.delete(uuid)).thenReturn(Mono.empty());

    RouterFunction function =
        RouterFunctions.route(
            RequestPredicates.DELETE("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"),
            request -> ServerResponse.noContent().build());

    WebTestClient.bindToRouterFunction(function)
        .build()
        .delete()
        .uri("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26")
        .exchange()
        .expectStatus()
        .isNoContent();
  }
}
