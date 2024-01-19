package ai.turintech.modelcatalog.rest;

import ai.turintech.modelcatalog.dto.MetricDTO;
import ai.turintech.modelcatalog.facade.MetricFacade;
import java.util.UUID;
import ai.turintech.modelcatalog.rest.resource.MetricResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

// @RunWith(SpringRunner.class)
//@WebFluxTest(MetricResourceTest.class)
@SpringJUnitConfig(TestRestConfig.class)
public class MetricResourceTest extends BasicRestTest {

//  @Autowired private WebTestClient webTestClient;

  @MockBean private MetricFacade metricFacade;

  WebTestClient client;

  @BeforeEach
  void setUp(ApplicationContext context) {
    client = WebTestClient.bindToApplicationContext(context).build();
  }
  
  @Test
  public void findByIdTest() {
    MetricDTO metricDTO = new MetricDTO();
    metricDTO.setId(UUID.fromString("4b6f7a9a-4a2d-4e9a-8f2a-6d6bb9c66d26"));
    metricDTO.setMetric("test_metric");

    Mono<MetricDTO> metricDTOMono = Mono.just(metricDTO);
    when(metricFacade.findOne(any(UUID.class))).thenReturn(metricDTOMono);

//    WebTestClient client =
//              WebTestClient.bindToController(new MetricResource()).build();

//    WebTestClient client = MockMvcWebTestClient.bindToController(new MetricResource()).build();
    client
        .get()
        .uri("/api/metrics") // Update the URI with the correct port
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk();
//        .expectBodyList(MetricTO.class)
//        .hasSize(1)
//        .value(
//            response -> {
//              MetricTO m = response.get(0);
//              assertNotNull(m.getId());
//              // perform other relevant checks
//            });
  }
}
