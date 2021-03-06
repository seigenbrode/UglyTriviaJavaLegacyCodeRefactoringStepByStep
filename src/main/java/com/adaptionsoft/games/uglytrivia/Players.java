package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Players implements Iterable<Player> {
    int currentPlayer = -1;
    List<Player> players = new ArrayList<Player>();

    public Players(String... playerNames) {
        for (String playerName : playerNames) {
            add(playerName);
        }
    }

    public Player next() {
        currentPlayer++;
        if (currentPlayer == players.size())
            currentPlayer = 0;
        return players.get(currentPlayer);
    }

    public void add(String playerName) {
        players.add(new Player(playerName));
    }

    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }
}
