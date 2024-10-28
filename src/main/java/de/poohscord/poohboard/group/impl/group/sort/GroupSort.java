package de.poohscord.poohboard.group.impl.group.sort;

import net.luckperms.api.model.group.Group;

import java.util.List;
import java.util.Map;

public interface GroupSort {

    Map<String, String> sortGroupsByWeightAlphabetically(List<Group> groupList);

}
