package ai.turintech.modelcatalog.to;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParameterTypeTOTest {

  private ParameterTypeTO parameterTypeTO;

  @BeforeEach
  public void setUp() {
    parameterTypeTO = new ParameterTypeTO();
    parameterTypeTO.setId(UUID.randomUUID());
    parameterTypeTO.setName("typeName");
  }

  @Test
  public void testEquals() {
    ParameterTypeTO sameIdParameterTypeTO = new ParameterTypeTO();
    sameIdParameterTypeTO.setId(parameterTypeTO.getId());
    sameIdParameterTypeTO.setName("typeName");

    ParameterTypeTO differentIdParameterTypeTO = new ParameterTypeTO();
    differentIdParameterTypeTO.setId(UUID.randomUUID());
    differentIdParameterTypeTO.setName("typeName");

    assertEquals(parameterTypeTO, sameIdParameterTypeTO);
    assertNotEquals(parameterTypeTO, differentIdParameterTypeTO);
    assertNotEquals(parameterTypeTO, null);
  }

  @Test
  public void testHashCode() {
    ParameterTypeTO sameIdParameterTypeTO = new ParameterTypeTO();
    sameIdParameterTypeTO.setId(parameterTypeTO.getId());
    sameIdParameterTypeTO.setName("typeName");

    ParameterTypeTO differentIdParameterTypeTO = new ParameterTypeTO();
    differentIdParameterTypeTO.setId(UUID.randomUUID());
    differentIdParameterTypeTO.setName("typeName");

    assertEquals(parameterTypeTO.hashCode(), sameIdParameterTypeTO.hashCode());
    assertNotEquals(parameterTypeTO.hashCode(), differentIdParameterTypeTO.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "ParameterTypeDTO{" + "id='" + parameterTypeTO.getId() + "'" + ", name='typeName'" + "}";
    assertEquals(expectedToString, parameterTypeTO.toString());
  }

  @Test
  public void testAccessorMethods() {
    assertEquals(parameterTypeTO.getId(), parameterTypeTO.getId());
    assertEquals("typeName", parameterTypeTO.getName());
  }
}
