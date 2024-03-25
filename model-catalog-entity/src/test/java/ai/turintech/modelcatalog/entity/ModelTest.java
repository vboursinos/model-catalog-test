package ai.turintech.modelcatalog.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.*;

class ModelTest {

  private final Model model = new Model();

  @Test
  public void testName() {
    model.setName("Test Model");
    assertEquals("Test Model", model.getName());
  }

  @Test
  public void testDisplayName() {
    model.setDisplayName("Display Test Model");
    assertEquals("Display Test Model", model.getDisplayName());
  }

  @Test
  public void testDescription() {
    model.setDescription("This is a test model.");
    assertEquals("This is a test model.", model.getDescription());
  }

  @Test
  public void testAdvantages() {
    String[] advantages = {"advantage1", "advantage2"};
    model.setAdvantages(advantages);
    assertArrayEquals(advantages, model.getAdvantages());
  }

  @Test
  public void testDisadvantages() {
    String[] disadvantages = {"disadvantage1", "disadvantage2"};
    model.setDisadvantages(disadvantages);
    assertArrayEquals(disadvantages, model.getDisadvantages());
  }

  @Test
  public void testEnabled() {
    model.setEnabled(true);
    assertTrue(model.getEnabled());
  }

  @Test
  public void testDecisionTree() {
    model.setDecisionTree(true);
    assertTrue(model.getDecisionTree());
  }

  @Test
  public void testHashCode() {
    model.setName("Test Model");
    Model model2 = new Model();
    model2.setName("Test Model");
    assertEquals(model.hashCode(), model2.hashCode());
  }

  @Test
  public void testToString() {
    model.setName("Test Model");
    assertTrue(model.toString().contains("Test Model"));
  }
}
