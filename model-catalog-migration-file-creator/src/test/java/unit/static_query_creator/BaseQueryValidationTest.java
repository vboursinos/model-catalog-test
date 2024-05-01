package unit.static_query_creator;

public class BaseQueryValidationTest {

  protected static final String EXPECTED_REV_INFO_QUERY =
      "INSERT INTO revinfo(rev,revtstmp) VALUES ( COALESCE (( select max(rev)+1 from revinfo), 1 ),";
}
