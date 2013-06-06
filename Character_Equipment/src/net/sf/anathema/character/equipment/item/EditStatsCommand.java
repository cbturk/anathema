package net.sf.anathema.character.equipment.item;

import net.sf.anathema.character.equipment.item.model.IEquipmentTemplateEditModel;
import net.sf.anathema.character.equipment.item.model.StatsEditor;
import net.sf.anathema.character.equipment.item.view.ToolListView;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.lib.resources.Resources;

import java.util.ArrayList;
import java.util.List;

public class EditStatsCommand implements Command {
  private final ToolListView<IEquipmentStats> statsListView;
  private final StatsEditor factory;
  private final IEquipmentTemplateEditModel editModel;
  private final Resources resources;

  public EditStatsCommand(ToolListView<IEquipmentStats> statsListView, StatsEditor factory, IEquipmentTemplateEditModel editModel, Resources resources) {
    this.statsListView = statsListView;
    this.factory = factory;
    this.editModel = editModel;
    this.resources = resources;
  }

  @Override
  public void execute() {
    IEquipmentStats selectedStats = statsListView.getSelectedItems().get(0);
    List<String> definedNames = new ArrayList<>();
    for (IEquipmentStats stats : editModel.getStats()) {
      if (stats == selectedStats) {
        continue;
      }
      definedNames.add(stats.getName().getId());
    }
    String[] nameArray = definedNames.toArray(new String[definedNames.size()]);
    IEquipmentStats equipmentStats = factory.editStats(resources, nameArray, selectedStats);
    if (equipmentStats == null) {
      return;
    }
    editModel.replaceStatistics(selectedStats, equipmentStats);
  }
}