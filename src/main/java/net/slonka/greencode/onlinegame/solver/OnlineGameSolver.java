package net.slonka.greencode.onlinegame.solver;

import net.slonka.greencode.onlinegame.domain.Clan;
import net.slonka.greencode.onlinegame.domain.Group;

import java.util.*;

public class OnlineGameSolver {
    public static List<Group> calculateOrder(int groupCount, List<Clan> clans) {
        // Sort the list of clans based on the rules
        clans.sort((a, b) -> {
            if (a.getPoints() == b.getPoints()) {
                return Integer.compare(a.getNumberOfPlayers(), b.getNumberOfPlayers());
            }
            return Integer.compare(b.getPoints(), a.getPoints());
        });

        List<Group> groups = new ArrayList<>();
        Queue<Clan> remainingClans = new LinkedList<>(clans);

        while (!remainingClans.isEmpty()) {
            Group group = new Group();
            int groupSize = 0;

            Iterator<Clan> iterator = remainingClans.iterator();
            while (iterator.hasNext()) {
                Clan clan = iterator.next();
                if (groupSize + clan.getNumberOfPlayers() <= groupCount) {
                    groupSize += clan.getNumberOfPlayers();
                    group.add(clan);
                    iterator.remove();
                }
            }

            groups.add(group);
        }

        return groups;
    }
}
