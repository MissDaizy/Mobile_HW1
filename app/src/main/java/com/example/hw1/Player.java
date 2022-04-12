package com.example.hw1;

abstract class Player {
    private int currentPositionRow;
    private int currentPositionCol;
    private MoveDirection currentDirection;

    public Player() {
        setToStartingPosition();
    }

    abstract int getStartingPositionRow();

    abstract int getStartingPositionColumn();

    public void setToStartingPosition() {
        setCurrentPositionRow(getStartingPositionRow());
        setCurrentPositionCol(getStartingPositionColumn());
        setCurrentDirection(MoveDirection.NONE);
    }

    public int getCurrentPositionRow() {
        return currentPositionRow;
    }

    public int getCurrentPositionCol() {
        return currentPositionCol;
    }

    public MoveDirection getCurrentDirection() {
        return currentDirection;
    }

    public Player setCurrentPositionRow(int currentPositionRow) {
        this.currentPositionRow = currentPositionRow;
        return this;
    }

    public Player setCurrentPositionCol(int currentPositionColumn) {
        this.currentPositionCol = currentPositionColumn;
        return this;
    }

    public Player setCurrentDirection(MoveDirection currentDirection) {
        this.currentDirection = currentDirection;
        return this;
    }
}
