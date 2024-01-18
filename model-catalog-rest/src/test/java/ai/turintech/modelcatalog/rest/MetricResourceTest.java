package ai.turintech.modelcatalog.rest;


import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class MetricResourceTest extends BasicRestTest {
//  @Autowired private WebTestClient webTestClient;

//  @LocalServerPort private int port;

  @Test
  public void findAllTest() {}
//      WebClient client1 = WebClient.builder().clientConnector(httpConnector()).build();
//
//    webTestClient
//        .get()
//        .uri("http://localhost:" + port + "/") // Update the URI with the correct port
//        .accept(APPLICATION_JSON)
//        .exchange()
//        .expectStatus()
//        .isOk()
//        .expectBodyList(MetricTO.class)
//        .hasSize(1)
//        .value(
//            response -> {
//              MetricTO m = response.get(0);
//              assertNotNull(m.getId());
//              // perform other relevant checks
//            });
//  }
}
