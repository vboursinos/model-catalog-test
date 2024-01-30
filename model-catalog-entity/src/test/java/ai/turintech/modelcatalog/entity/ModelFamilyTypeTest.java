package ai.turintech.modelcatalog.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelFamilyTypeTest {

  private ModelFamilyType modelFamilyTypeUnderTest;
  private Model model;

  @BeforeEach
  public void setUp() {
    modelFamilyTypeUnderTest = new ModelFamilyType();
    model = new Model();

    UUID id = UUID.randomUUID();
    modelFamilyTypeUnderTest.setId(id);
    modelFamilyTypeUnderTest.setName("Test");
  }

  @Test
  public void testGetName() {
    String result = modelFamilyTypeUnderTest.getName();
    assertThat(result).isEqualTo("Test");
  }

  @Test
  public void testSetName() {
    modelFamilyTypeUnderTest.setName("UpdatedTest");

    String result = modelFamilyTypeUnderTest.getName();
    assertThat(result).isEqualTo("UpdatedTest");
  }

  @Test
  public void testGetModels() {
    modelFamilyTypeUnderTest.addModels(model);
    assertThat(modelFamilyTypeUnderTest.getModels()).contains(model);
  }

  @Test
  public void testSetModels() {
    modelFamilyTypeUnderTest.addModels(model);
    assertThat(modelFamilyTypeUnderTest.getModels()).contains(model);
  }

  @Test
  public void testEquals() {
    ModelFamilyType entityToCompare = new ModelFamilyType();
    entityToCompare.setId(modelFamilyTypeUnderTest.getId());
    entityToCompare.setName(modelFamilyTypeUnderTest.getName());

    assertThat(modelFamilyTypeUnderTest).isEqualTo(entityToCompare);
  }

  @Test
  public void testHashCode() {
    int result = modelFamilyTypeUnderTest.hashCode();
    assertThat(result).isEqualTo(ModelFamilyType.class.hashCode());
  }

  @Test
  public void testToString() {
    String result = modelFamilyTypeUnderTest.toString();
    assertThat(result)
        .isEqualTo(
            "ModelFamilyType{id="
                + modelFamilyTypeUnderTest.getId()
                + ", name='"
                + modelFamilyTypeUnderTest.getName()
                + "'}");
  }
}
