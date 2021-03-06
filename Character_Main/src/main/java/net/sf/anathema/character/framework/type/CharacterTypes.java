package net.sf.anathema.character.framework.type;

import net.sf.anathema.hero.framework.data.ExtensibleDataSet;

public interface CharacterTypes extends Iterable<CharacterType>, ExtensibleDataSet {
  CharacterType findById(String id);
}
