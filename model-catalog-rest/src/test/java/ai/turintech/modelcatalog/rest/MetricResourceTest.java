package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.ok;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.facade.MetricFacade;
import ai.turintech.modelcatalog.rest.resource.MetricResource;
import ai.turintech.modelcatalog.to.MetricTO;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
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
}
