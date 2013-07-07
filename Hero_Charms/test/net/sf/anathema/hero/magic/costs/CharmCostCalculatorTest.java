package net.sf.anathema.hero.magic.costs;

import net.sf.anathema.character.main.costs.AbstractBonusPointTestCase;
import net.sf.anathema.character.main.template.points.DefaultBonusPointCosts;
import net.sf.anathema.character.main.magic.model.spells.ISpell;
import net.sf.anathema.character.main.traits.types.AbilityType;
import net.sf.anathema.character.main.library.trait.favorable.FavorableState;
import net.sf.anathema.hero.spells.SpellModel;
import net.sf.anathema.hero.traits.TraitModel;
import net.sf.anathema.hero.traits.TraitModelFetcher;
import net.sf.anathema.character.main.testing.BasicCharacterTestCase;
import net.sf.anathema.character.main.testing.dummy.DummyHero;
import net.sf.anathema.character.main.testing.dummy.magic.DummyCharmsModel;
import net.sf.anathema.character.main.testing.dummy.magic.DummySpell;
import net.sf.anathema.character.main.testing.dummy.magic.DummySpellModel;
import net.sf.anathema.character.main.traits.context.CreationTraitValueStrategy;
import net.sf.anathema.character.main.magic.advance.MagicCostCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharmCostCalculatorTest extends AbstractBonusPointTestCase {

  private MagicCostCalculator calculator;
  private SpellModel spells;
  private TraitModel traitModel;

  @Before
  public void setUp() throws Exception {
    DummyCharmsModel charms = new DummyCharmsModel();
    spells = new DummySpellModel();
    DummyHero hero = new BasicCharacterTestCase().createModelContextWithEssence2(new CreationTraitValueStrategy());
    hero.addModel(charms);
    hero.addModel(spells);
    traitModel = TraitModelFetcher.fetch(hero);
    addAbilityAndEssence(traitModel, hero);
    hero.template.creationPoints.favoredCreationCharmCount = 2;
    hero.template.creationPoints.defaultCreationCharmCount = 3;
    calculator = new MagicCostCalculator(hero, new DefaultBonusPointCosts());
  }

  @Test
  public void testNoSpellsLearned() {
    assertEquals(0, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointsSpentForCharms());
    assertEquals(0, calculator.getBonusPointsSpentForSpells());
  }

  @Test
  public void testOneSpellLearnedOccultUnfavored() {
    spells.addSpells(new ISpell[]{new DummySpell()});
    calculator.calculateMagicCosts();
    assertEquals(1, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointsSpentForCharms());
    assertEquals(0, calculator.getBonusPointsSpentForSpells());
  }

  @Test
  public void testOneSpellLearnedOccultFavored() {
    setOccultFavored();
    spells.addSpells(new ISpell[]{new DummySpell()});
    calculator.calculateMagicCosts();
    assertEquals(0, calculator.getGeneralCharmPicksSpent());
    assertEquals(1, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointsSpentForCharms());
    assertEquals(0, calculator.getBonusPointsSpentForSpells());
  }

  private void setOccultFavored() {
    traitModel.getTrait(AbilityType.Occult).getFavorization().setFavorableState(FavorableState.Favored);
  }

  @Test
  public void testUnfavoredSpellsOverflowToBonus() {
    DummySpell dummySpell = new DummySpell();
    spells.addSpells(new ISpell[]{dummySpell, dummySpell, dummySpell, dummySpell});
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointsSpentForCharms());
    assertEquals(5, calculator.getBonusPointsSpentForSpells());
  }

  @Test
  public void testUnfavoredSpellsOverflowToBonusAndAreReset() {
    DummySpell dummySpell = new DummySpell();
    DummySpell dummySpellToRemove = new DummySpell();
    spells.addSpells(new ISpell[]{dummySpell, dummySpell, dummySpell, dummySpellToRemove});
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointsSpentForCharms());
    assertEquals(5, calculator.getBonusPointsSpentForSpells());
    spells.removeSpells(new ISpell[]{dummySpellToRemove}, false);
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointsSpentForSpells());
  }

  @Test
  public void testFavoredSpellsOverflowToGeneralAndBonus() {
    setOccultFavored();
    DummySpell dummySpell = new DummySpell();
    spells.addSpells(new ISpell[]{dummySpell, dummySpell, dummySpell, dummySpell, dummySpell, dummySpell,});
    calculator.calculateMagicCosts();
    assertEquals(3, calculator.getGeneralCharmPicksSpent());
    assertEquals(2, calculator.getFavoredCharmPicksSpent());
    assertEquals(0, calculator.getBonusPointsSpentForCharms());
    assertEquals(4, calculator.getBonusPointsSpentForSpells());
  }
}