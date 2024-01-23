// package ai.turintech.modelcatalog.rest;
//
// import ai.turintech.modelcatalog.entity.Metric;
// import java.util.UUID;
// import org.junit.Assert;
// import org.junit.jupiter.api.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.graphql.test.tester.HttpGraphQlTester;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.web.reactive.server.WebTestClient;
// import org.testcontainers.junit.jupiter.Testcontainers;
//
// @SpringBootTest
// @AutoConfigureHttpGraphQlTester
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @Testcontainers
// @ContextConfiguration(classes = TestRestConfig.class)
// public class GraphQLTest extends BasicRestTest {
//
//  @Autowired HttpGraphQlTester httpGraphQlTester;
//  private final String GRAPHQL_ENDPOINT = "http://localhost:8081/graphql";
//
//  @Test
//  @Order(1)
//  void findAllMetrics() {
//    Metric metric =
//        this.httpGraphQlTester
//            .document(
//                """
//                          query{\\n" +
//                                             "  Metrics{\\n" +
//                                             "    pages\\n" +
//                                             "    total\\n" +
//                                             "    select{\\n" +
//                                             "      metric\\n" +
//                                             "    }\\n" +
//                                             "  }\\n" +
//                                             "}
//                        """)
//            .execute()
//            .errors()
//            .verify()
//            .path("createProduct")
//            .entity(Metric.class)
//            .get();
//    System.out.println("metric: " + metric);
//  }
// }
