package com.example.hw1;

public class EnemyPlayer extends Player {

    private final int PLAYER_STARTING_POSITION_ROW = 4;
    private final int PLAYER_STARTING_POSITION_COLUMN = 1;

    public EnemyPlayer() {
        super();
    }

    @Override
    int getStartingPositionRow() {
        return PLAYER_STARTING_POSITION_ROW;
    }

    @Override
    int getStartingPositionColumn() {
        return PLAYER_STARTING_POSITION_COLUMN;
    }
}
