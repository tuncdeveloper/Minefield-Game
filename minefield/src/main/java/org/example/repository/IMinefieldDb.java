package org.example.repository;

import org.example.Player;

import java.util.List;

public interface IMinefieldDb {
    void playerAdd(Player player);
    void scoreAdd(Integer playerIdFk,int score);
    List<String> listScores();
}

