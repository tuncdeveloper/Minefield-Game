package org.example;

public class Player {
    private Integer playerId;
    private String name;

    public Player(Integer playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }
    public Player() {

    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
