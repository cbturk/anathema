package net.sf.anathema.hero.magic.model.charms;

import net.sf.anathema.character.main.magic.model.charm.type.CharmType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReflexiveCharmComboRulesTest extends AbstractComboRulesTestCase {

  @Test
  public void testCharmComboAttributeAbilityReflexiveForbidden() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(false);
    assertFalse(comboAbilityAttributeCharms(CharmType.Reflexive, CharmType.Reflexive));
  }

  @Test
  public void testCharmComboAttributeAbilityReflexiveAllowed() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(true);
    assertTrue(comboAbilityAttributeCharms(CharmType.Reflexive, CharmType.Reflexive));
  }
}