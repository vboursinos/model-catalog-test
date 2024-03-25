package ai.turintech.modelcatalog.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTypeTest {

  private final ModelType modelTypeUnderTest = new ModelType();
  private final Model model = new Model();

  @BeforeEach
  public void setUp() {
    UUID id = UUID.randomUUID();
    modelTypeUnderTest.setId(id);
    modelTypeUnderTest.setName("Test");
  }

  @Test
  public void testGetName() {
    String result = modelTypeUnderTest.getName();
    assertThat(result).isEqualTo("Test");
  }

  @Test
  public void testSetName() {
    modelTypeUnderTest.setName("UpdatedTest");

    String result = modelTypeUnderTest.getName();
    assertThat(result).isEqualTo("UpdatedTest");
  }

  @Test
  public void testGetModels() {
    modelTypeUnderTest.addModels(model);
    assertThat(modelTypeUnderTest.getModels()).contains(model);
  }

  @Test
  public void testSetModels() {
    modelTypeUnderTest.addModels(model);
    assertThat(modelTypeUnderTest.getModels()).contains(model);
  }

  @Test
  public void testEquals() {
    ModelType entityToCompare = new ModelType();
    entityToCompare.setId(modelTypeUnderTest.getId());
    entityToCompare.setName(modelTypeUnderTest.getName());

    assertThat(modelTypeUnderTest).isEqualTo(entityToCompare);
  }

  @Test
  public void testHashCode() {
    int result = modelTypeUnderTest.hashCode();
    assertThat(result).isEqualTo(ModelType.class.hashCode());
  }

  @Test
  public void testToString() {
    String result = modelTypeUnderTest.toString();
    assertThat(result)
        .isEqualTo(
            "ModelType{id="
                + modelTypeUnderTest.getId()
                + ", name='"
                + modelTypeUnderTest.getName()
                + "'}");
  }
}
