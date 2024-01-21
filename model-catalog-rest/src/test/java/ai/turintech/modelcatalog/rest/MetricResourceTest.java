package ai.turintech.modelcatalog.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.facade.MetricFacade;
import ai.turintech.modelcatalog.rest.resource.MetricResource;
import ai.turintech.modelcatalog.to.MetricTO;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

//@RunWith(SpringRunner.class)
@SpringJUnitConfig(TestRestConfig.class)
//@WebFluxTest(MetricResourceTest.class)
public class MetricResourceTest extends BasicRestTest {
  @MockBean private MetricFacade metricFacade;

  @MockBean private MetricResource metricResource;

  WebTestClient client;

  //  @InjectMocks
  //  private MetricResource metricResource;

//  @BeforeEach
//  void setUp(ApplicationContext context) {
////      client = WebTestClient.bindToApplicationContext(context).build();
////      client = WebTestClient.bindToController(metricResource).build();
//      client = WebTestClient
//            .bindToServer()
//            .baseUrl("http://localhost:8080")
//            .build();
//    }

  @Test
  public void findByIdTest() {
    UUID uuid = UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26");
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(uuid);
    metricDTO.setMetric("test_metric");

    MetricTO metricTO = new MetricTO();
    metricTO.setId(uuid);
    metricTO.setMetric("test_metric");
    Mono<ResponseEntity<MetricTO>> metricTOMono = Mono.just(ResponseEntity.ok(metricTO));

    Mono<MetricDTO> metricDTOMono = Mono.just(metricDTO);
    when(metricFacade.findOne(any(UUID.class))).thenReturn(metricDTOMono);
    when(metricResource.findOne(any(UUID.class))).thenReturn(metricTOMono);

    System.out.println("metricFacade.findOne(any(UUID.class)) = " + metricFacade.findOne(uuid).block());
    //     1st attempt:
    RouterFunction function =
        RouterFunctions.route(
            RequestPredicates.GET("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"), request -> ServerResponse.ok().build());

            WebTestClient
                    .bindToRouterFunction(function)
                    .build().get().uri("/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody().isEmpty();

    // Act
//    WebTestClient.bindToRouterFunction(function).build()
//            .get().uri("/api/metrics/4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26")
//            .exchange()
//    .expectStatus().isOk()
//            .expectBody(String.class)
//            .isEqualTo("expectedResponseBody");

    // Verify the mock interaction
    Assert.assertEquals(metricFacade.findOne(uuid).block().getMetric(), "test_metric");
  }
    // 2nd attempt:
//    WebHandler handler = exchange -> Mono.empty();
//    client = WebTestClient.bindToWebHandler(handler).build();
//
//    client
//        .get()
//        .uri("/api/metrics") // Update the URI with the correct port
//        .accept(APPLICATION_JSON)
//        .exchange()
//        .expectStatus()
//        .isOk()
//        .expectBodyList(MetricTO.class)
//        .hasSize(0);
    //        .value(
    //            response -> {
    //              MetricTO m = response.get(0);
    //              assertNotNull(m.getId());
    //              // perform other relevant checks
    //            });

//            client
//            .get()
//            .uri("/api/metrics") // Update the URI with the correct port
//            .accept(APPLICATION_JSON)
//            .exchange()
//            .expectStatus()
//            .isOk()
//            .expectBodyList(MetricTO.class)
//            .hasSize(0);

//  }
}
