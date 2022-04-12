package com.example.hw1;

public class FugitivePlayer extends Player {

    private final int FUGITIVE_STARTING_POSITION_ROW = 0;
    private final int FUGITIVE_STARTING_POSITION_COLUMN = 1;

    public FugitivePlayer() {
        super();
    }


    @Override
    int getStartingPositionRow() {
        return FUGITIVE_STARTING_POSITION_ROW;
    }

    @Override
    int getStartingPositionColumn() {
        return FUGITIVE_STARTING_POSITION_COLUMN;
    }
}
