package ai.turintech.modelcatalog.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelStructureTypeTest {

  private ModelStructureType modelStructureTypeUnderTest;
  private Model model;

  @BeforeEach
  public void setUp() {
    modelStructureTypeUnderTest = new ModelStructureType();
    model = new Model();

    UUID id = UUID.randomUUID();
    modelStructureTypeUnderTest.setId(id);
    modelStructureTypeUnderTest.setName("Test");
  }

  @Test
  public void testGetName() {
    String result = modelStructureTypeUnderTest.getName();
    assertThat(result).isEqualTo("Test");
  }

  @Test
  public void testSetName() {
    modelStructureTypeUnderTest.setName("UpdatedTest");

    String result = modelStructureTypeUnderTest.getName();
    assertThat(result).isEqualTo("UpdatedTest");
  }

  @Test
  public void testGetModels() {
    modelStructureTypeUnderTest.addModels(model);
    assertThat(modelStructureTypeUnderTest.getModels()).contains(model);
  }

  @Test
  public void testSetModels() {
    modelStructureTypeUnderTest.addModels(model);
    assertThat(modelStructureTypeUnderTest.getModels()).contains(model);
  }

  @Test
  public void testEquals() {
    ModelStructureType entityToCompare = new ModelStructureType();
    entityToCompare.setId(modelStructureTypeUnderTest.getId());
    entityToCompare.setName(modelStructureTypeUnderTest.getName());

    assertThat(modelStructureTypeUnderTest).isEqualTo(entityToCompare);
  }

  @Test
  public void testHashCode() {
    int result = modelStructureTypeUnderTest.hashCode();
    assertThat(result).isEqualTo(ModelStructureType.class.hashCode());
  }

  @Test
  public void testToString() {
    String result = modelStructureTypeUnderTest.toString();
    assertThat(result)
        .isEqualTo(
            "ModelStructureType{id="
                + modelStructureTypeUnderTest.getId()
                + ", name='"
                + modelStructureTypeUnderTest.getName()
                + "'}");
  }
}
