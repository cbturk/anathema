package net.sf.anathema.charmdatabase.view.fx;

import java.util.Arrays;
import java.util.Comparator;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.sf.anathema.character.main.magic.basic.attribute.MagicAttribute;
import net.sf.anathema.charmdatabase.view.rules.CharmKeywordsPanel;
import net.sf.anathema.framework.environment.Resources;

public class FxCharmKeywordsPanel extends AbstractFxListPanel<MagicAttribute> implements CharmKeywordsPanel {
	
	public FxCharmKeywordsPanel(final Resources resources) {
		// TODO: A graphical means to render the trait values would be nice.
		super(new LC().height("100"), new AC(), new AC().index(1).shrinkPrio(200),
				new IconlessCellRenderer<MagicAttribute>() {
					@Override
					public String getLabel(MagicAttribute attribute) {
						return attribute != null ? resources.getString(attribute.getId()) 
								+ (attribute.isVisualized() ? "" : " " + resources.getString("Charms.Creation.Rules.Keywords.NonVisualized"))
										: null;
					}
		});
	}
	
	@Override
	public void setKeywords(MagicAttribute[] keywords) {
		Arrays.sort(keywords, new Comparator<MagicAttribute>() {

			@Override
			public int compare(MagicAttribute o1, MagicAttribute o2) {
				if (o1.isVisualized() && !o2.isVisualized()) {
					return -1;
				}
				if (o2.isVisualized() && !o1.isVisualized()) {
					return 1;
				}
				return o1.getId().compareTo(o2.getId());
			}
			
		});
		setObjects(keywords);
	}

}
