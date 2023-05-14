package net.slonka.greencode.onlinegame.domain;

import java.util.List;

// Players.java
public class Players {
    private int groupCount;
    private Clan[] clans;

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public Clan[] getClans() {
        return clans;
    }

    public void setClans(Clan[] clans) {
        this.clans = clans;
    }
}

