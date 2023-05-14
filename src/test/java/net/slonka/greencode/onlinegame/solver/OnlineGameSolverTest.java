package net.slonka.greencode.onlinegame.solver;

import net.slonka.greencode.onlinegame.domain.Clan;
import net.slonka.greencode.onlinegame.domain.Group;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OnlineGameSolverTest {

    @Test
    public void testSingleClanExactFit() {
        Clan[] clans = new Clan[] { new Clan(5, 100) };
        List<Group> result = OnlineGameSolver.calculateOrder(5, clans);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).get(0).getNumberOfPlayers());
        assertEquals(100, result.get(0).get(0).getPoints());
    }

    @Test
    public void testSingleClanSmallerThanGroup() {
        Clan[] clans = new Clan[] { new Clan(3, 100) };
        List<Group> result = OnlineGameSolver.calculateOrder(5, clans);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).get(0).getNumberOfPlayers());
        assertEquals(100, result.get(0).get(0).getPoints());
    }

    @Test
    public void testMultipleClansFitInOneGroup() {
        Clan[] clans = new Clan[] { new Clan(3, 100), new Clan(2, 80) };
        List<Group> result = OnlineGameSolver.calculateOrder(5, clans);
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).get(0).getNumberOfPlayers());
        assertEquals(100, result.get(0).get(0).getPoints());
        assertEquals(2, result.get(0).get(1).getNumberOfPlayers());
        assertEquals(80, result.get(0).get(1).getPoints());
    }

    @Test
    public void testMultipleClansNeedMultipleGroups() {
        Clan[] clans = new Clan[] { new Clan(3, 100), new Clan(4, 80) };
        List<Group> result = OnlineGameSolver.calculateOrder(5, clans);
        assertEquals(2, result.size());
        assertEquals(3, result.get(0).get(0).getNumberOfPlayers());
        assertEquals(100, result.get(0).get(0).getPoints());
        assertEquals(4, result.get(1).get(0).getNumberOfPlayers());
        assertEquals(80, result.get(1).get(0).getPoints());
    }

    @Test
    public void testClansWithSamePoints() {
        Clan[] clans = new Clan[] { new Clan(3, 100), new Clan(2, 100) };
        List<Group> result = OnlineGameSolver.calculateOrder(4, clans);
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).get(0).getNumberOfPlayers());
        assertEquals(100, result.get(0).get(0).getPoints());
        assertEquals(3, result.get(1).get(0).getNumberOfPlayers());
        assertEquals(100, result.get(1).get(0).getPoints());
    }
}