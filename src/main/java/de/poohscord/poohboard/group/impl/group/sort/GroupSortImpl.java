package de.poohscord.poohboard.group.impl.group.sort;

import net.luckperms.api.model.group.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupSortImpl implements GroupSort {

    @Override
    public Map<String, String> sortGroupsByWeightAlphabetically(List<Group> groupList) {
        List<String> alphabet = new ArrayList<>();
        for (int i = 97; i < 97 + groupList.size(); i++) {
            alphabet.add(String.valueOf((char) i));
        }

        //order groups by weight
        List<Group> sortedGroups = groupList.stream().sorted((group1, group2) -> {
            int weight1 = group1.getWeight().orElse(0);
            int weight2 = group2.getWeight().orElse(0);
            return Integer.compare(weight2, weight1);
        }).toList();

        Map<String, String> weightedGroups = new HashMap<>(alphabet.size());
        for (int i = 0; i < sortedGroups.size(); i++) {
            weightedGroups.put(alphabet.get(i), sortedGroups.get(i).getCachedData().getMetaData().getPrefix());
        }
        return weightedGroups;
    }

}
