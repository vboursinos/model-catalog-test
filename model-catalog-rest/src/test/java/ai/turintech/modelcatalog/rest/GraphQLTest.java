//package ai.turintech.modelcatalog.rest;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureHttpGraphQlTester
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Testcontainers
//@ContextConfiguration(classes = TestRestConfig.class)
//public class GraphQLTest extends BasicRestTest {
//
////  @Autowired private TestRestTemplate restTemplate;
//
//    @Autowired
//    private WebTestClient webTestClient;
//  private final String GRAPHQL_ENDPOINT = "/graphql";
//
//  @Test
//  @Order(1)
//  void findAllMetrics() {
//    HttpHeaders headers = new HttpHeaders();
//    headers.set("Content-Type", "application/graphql");
//
//    String requestQuery =
//        """
//              query {
//                 Metrics {
//                    pages
//                    total
//                    select {
//                      metric
//                    }
//                 }
//              }
//              """;
//
//      webTestClient.post()
//              .uri(GRAPHQL_ENDPOINT)
//              .header("Content-Type", "application/graphql")
//              .syncBody(requestQuery)
//              .exchange()
//              .returnResult(String.class)
//              .getResponseBody()
//              .subscribe(response -> {
//                  // Handle response here
//                  System.out.println("Response: " + response);
//              });
//  }
//}
