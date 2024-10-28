package de.poohscord.poohboard.group.impl.group.sort.test;

import de.poohscord.poohboard.group.impl.group.sort.GroupSort;
import de.poohscord.poohboard.group.impl.group.sort.GroupSortImpl;
import net.luckperms.api.cacheddata.CachedDataManager;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GroupSortTest {

    private GroupSort groupSort;

    @BeforeEach
    public void setUp() {
        groupSort = new GroupSortImpl();
    }

    @Test
    public void testSortGroupsByWeightAlphabetically() {
        Group owner = mock(Group.class);
        Group developer = mock(Group.class);
        Group user = mock(Group.class);

        CachedDataManager ownerData = mock(CachedDataManager.class);
        CachedDataManager developerData = mock(CachedDataManager.class);
        CachedDataManager userData = mock(CachedDataManager.class);

        CachedMetaData ownerMeta = mock(CachedMetaData.class);
        CachedMetaData developerMeta = mock(CachedMetaData.class);
        CachedMetaData userMeta = mock(CachedMetaData.class);

        when(owner.getWeight()).thenReturn(OptionalInt.of(100));
        when(developer.getWeight()).thenReturn(OptionalInt.of(50));
        when(user.getWeight()).thenReturn(OptionalInt.of(0));

        when(owner.getCachedData()).thenReturn(ownerData);
        when(developer.getCachedData()).thenReturn(developerData);
        when(user.getCachedData()).thenReturn(userData);

        when(ownerData.getMetaData()).thenReturn(ownerMeta);
        when(developerData.getMetaData()).thenReturn(developerMeta);
        when(userData.getMetaData()).thenReturn(userMeta);

        when(ownerMeta.getPrefix()).thenReturn("§4Owner");
        when(developerMeta.getPrefix()).thenReturn("§2Developer");
        when(userMeta.getPrefix()).thenReturn("§7User");

        //best case
        List<Group> groups = List.of(owner, developer, user);
        Map<String, String> result = groupSort.sortGroupsByWeightAlphabetically(groups);

        assertEquals("§4Owner", result.get("a"));
        assertEquals("§2Developer", result.get("b"));
        assertEquals("§7User", result.get("c"));

        //average case
        groups = List.of(developer, user, owner);
        result = groupSort.sortGroupsByWeightAlphabetically(groups);

        assertEquals("§4Owner", result.get("a"));
        assertEquals("§2Developer", result.get("b"));
        assertEquals("§7User", result.get("c"));

        //worst case
        groups = List.of(user, developer, owner);
        result = groupSort.sortGroupsByWeightAlphabetically(groups);

        assertEquals("§4Owner", result.get("a"));
        assertEquals("§2Developer", result.get("b"));
        assertEquals("§7User", result.get("c"));
    }
}
