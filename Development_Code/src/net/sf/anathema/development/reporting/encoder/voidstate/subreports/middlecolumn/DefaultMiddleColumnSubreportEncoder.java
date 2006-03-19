package net.sf.anathema.development.reporting.encoder.voidstate.subreports.middlecolumn;

import java.util.Map;

import net.sf.anathema.character.generic.framework.reporting.template.voidstate.ExaltVoidstateReportTemplate;
import net.sf.anathema.development.reporting.encoder.AbstractPagedCharacterSheetEncoder;
import net.sf.anathema.development.reporting.encoder.ICharacterSheetPageEncoder;
import net.sf.anathema.development.reporting.encoder.voidstate.format.VoidstateBasicsEncoder;
import net.sf.anathema.development.reporting.encoder.voidstate.util.SubreportUtilities;

public class DefaultMiddleColumnSubreportEncoder extends AbstractPagedCharacterSheetEncoder {
  private final VoidstateBasicsEncoder basicsEncoder;

  public DefaultMiddleColumnSubreportEncoder(VoidstateBasicsEncoder basicsEncoder) {
    super(SubreportUtilities.createPageFormat(DefaultMiddleColumnPageEncoder.getExtents(basicsEncoder)));
    this.basicsEncoder = basicsEncoder;
  }

  @Override
  protected ICharacterSheetPageEncoder[] getPageEncoders() {
    return new ICharacterSheetPageEncoder[] { new DefaultMiddleColumnPageEncoder(basicsEncoder) };
  }

  @Override
  protected void addParameterClasses(Map<String, String> parameterClasses) {
    ExaltVoidstateReportTemplate.addMiddleColumnContentParameterClasses(parameterClasses);
  }

  @Override
  protected String getReportName() {
    return "DefaultMiddleColumnSubreport";
  }
}
