package net.slonka.greencode.onlinegame.solver;

import net.slonka.greencode.onlinegame.domain.Clan;
import net.slonka.greencode.onlinegame.domain.Group;

import java.util.*;

public class OnlineGameSolver {
    public static List<Group> calculateOrder(int groupCount, Clan[] clans) {
        // Sort the list of clans based on the rules
        Arrays.sort(clans, ((a, b) -> {
            if (a.getPoints() == b.getPoints()) {
                return Integer.compare(a.getNumberOfPlayers(), b.getNumberOfPlayers());
            }
            return Integer.compare(b.getPoints(), a.getPoints());
        }));

        List<Group> groups = new ArrayList<>();
        int clansAddedCount = 0;
        int allClans = clans.length;
        var clansAdded = new boolean[allClans];
        var initialIndex = 0;

        while (clansAddedCount != allClans) {
            Group group = new Group();
            int groupSize = 0;

            for (int i = initialIndex; i < allClans; i++) {
                Clan clan = clans[i];
                if (clansAdded[i]) {
                    if (i == initialIndex) {
                        initialIndex++;
                    }
                    continue;
                }
                if (groupSize + clan.getNumberOfPlayers() <= groupCount) {
                    groupSize += clan.getNumberOfPlayers();
                    group.add(clan);
                    clansAdded[i] = true;
                    clansAddedCount++;
                }
            }

            groups.add(group);
        }

        return groups;
    }
}
