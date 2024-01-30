package ai.turintech.modelcatalog.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelGroupTypeTest {

  private ModelGroupType modelGroupTypeUnderTest;
  private Model model;

  @BeforeEach
  public void setUp() {
    modelGroupTypeUnderTest = new ModelGroupType();
    model = new Model();

    UUID id = UUID.randomUUID();
    modelGroupTypeUnderTest.setId(id);
    modelGroupTypeUnderTest.setName("Test");
  }

  @Test
  public void testGetName() {
    String result = modelGroupTypeUnderTest.getName();
    assertThat(result).isEqualTo("Test");
  }

  @Test
  public void testSetName() {
    modelGroupTypeUnderTest.setName("UpdatedTest");

    String result = modelGroupTypeUnderTest.getName();
    assertThat(result).isEqualTo("UpdatedTest");
  }

  @Test
  public void testGetModels() {
    modelGroupTypeUnderTest.addModels(model);
    assertThat(modelGroupTypeUnderTest.getModels()).contains(model);
  }

  @Test
  public void testSetModels() {
    modelGroupTypeUnderTest.addModels(model);
    assertThat(modelGroupTypeUnderTest.getModels()).contains(model);
  }

  @Test
  public void testEquals() {
    ModelGroupType entityToCompare = new ModelGroupType();
    entityToCompare.setId(modelGroupTypeUnderTest.getId());
    entityToCompare.setName(modelGroupTypeUnderTest.getName());

    assertThat(modelGroupTypeUnderTest).isEqualTo(entityToCompare);
  }

  @Test
  public void testHashCode() {
    int result = modelGroupTypeUnderTest.hashCode();
    assertThat(result).isEqualTo(ModelGroupType.class.hashCode());
  }

  @Test
  public void testToString() {
    String result = modelGroupTypeUnderTest.toString();
    assertThat(result)
        .isEqualTo(
            "ModelGroupType{id="
                + modelGroupTypeUnderTest.getId()
                + ", name='"
                + modelGroupTypeUnderTest.getName()
                + "'}");
  }
}
