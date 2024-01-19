package ai.turintech.modelcatalog.rest;

import com.introproventures.graphql.jpa.query.web.GraphQLController.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// @GraphQlTest(MetricResource.class)
@SpringBootTest
// @SpringJUnitConfig(TestRestConfig.class)
public class MetricGraphqlTest extends BasicRestTest {

  //  @Autowired
  //  private WebGraphQlTester webGraphQlTester;

  //  @Autowired TestRestTemplate rest;

  @Test
  public void testGraphql() {
    GraphQLQueryRequest query =
        new GraphQLQueryRequest(
            "query{"
                + "  Metrics{"
                + "    pages"
                + "    total"
                + "    select{"
                + "      metric"
                + "    }"
                + "  }"
                + "}");
    //
    //    ResponseEntity<Result> entity =
    //        rest.postForEntity("/graphql", new HttpEntity<>(query), Result.class);
    //    assertThat(HttpStatus.OK).isEqualTo(entity.getStatusCode());
    //
    //    Result result = entity.getBody();
    //    assertThat(result).isNotNull();
  }

  //  @Test
  //  void findAllMetrics() {
  //    String query =
  //            "query{" +
  //                    "  Metrics{" +
  //                    "    pages" +
  //                    "    total" +
  //                    "    select{" +
  //                    "      metric" +
  //                    "    }" +
  //                    "  }" +
  //                    "}";
  //
  //    this.webGraphQlTester.document(query)
  //            .execute()
  //            .path("data.Metrics")
  //            .matchesJson(
  //                    """
  //                          {
  //                               "pages": 1,
  //                               "total": 2,
  //                               "select": [
  //                                 {
  //                                   "metric": "classification-roc"
  //                                 },
  //                                 {
  //                                   "metric": "classification-log-loss"
  //                                 }
  //                               ]
  //                          }
  //                    """);
  //  }
}
