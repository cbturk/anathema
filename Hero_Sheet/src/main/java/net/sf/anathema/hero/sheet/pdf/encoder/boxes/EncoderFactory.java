package net.sf.anathema.hero.sheet.pdf.encoder.boxes;

import net.sf.anathema.hero.sheet.pdf.content.BasicContent;
import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.lib.util.Identifier;

public interface EncoderFactory extends Identifier {

  ContentEncoder create(Resources resources, BasicContent content);

  boolean supports(BasicContent content);

  float getPreferredHeight(EncodingMetrics metrics, float width);
}
