package com.example.hw1;

import java.util.Random;

public class GameManager {

    private final int MAX_LIVES = 3;

    private int score = 0;
    private int lives = MAX_LIVES;

    private Player fugitivePlayer;
    private Player enemyPlayer;
    private GameMatrix gameMatrix;

    public GameManager() {
        fugitivePlayer = new FugitivePlayer();
        enemyPlayer = new EnemyPlayer();
        gameMatrix = new GameMatrix();
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getMaxLives() {
        return MAX_LIVES;
    }


    public int getMatRows() {
        return gameMatrix.getMatRows();
    }

    public int getMatCols() {
        return gameMatrix.getMatCols();
    }

    public int getCurrentFugitivePositionRow() {
        return fugitivePlayer.getCurrentPositionRow();
    }

    public int getCurrentFugitivePositionCol() {
        return fugitivePlayer.getCurrentPositionCol();
    }

    public int getCurrentEnemyPositionRow() {
        return enemyPlayer.getCurrentPositionRow();
    }

    public int getCurrentEnemyPositionCol() {
        return enemyPlayer.getCurrentPositionCol();
    }

    public void updateScore() {
        score++;
    }

    public void reduceLives() {
        lives--;
    }

    public void initPosition() {
        fugitivePlayer.setToStartingPosition();
        enemyPlayer.setToStartingPosition();
    }

    public void updateFugitivePlayerDirectionData(MoveDirection moveDirection) {
        fugitivePlayer.setCurrentDirection(moveDirection);
    }

    public void updateEnemyPlayerDirectionData() {
        Random r = new Random();
        int randomNumber = r.ints(1, 1, 5).findFirst().getAsInt();
        switch (randomNumber) {
            case 1:
                enemyPlayer.setCurrentDirection(MoveDirection.UP);
                break;

            case 2:
                enemyPlayer.setCurrentDirection(MoveDirection.DOWN);
                break;

            case 3:
                enemyPlayer.setCurrentDirection(MoveDirection.RIGHT);
                break;

            case 4:
                enemyPlayer.setCurrentDirection(MoveDirection.LEFT);
                break;
        }
    }

    public void updateGameData() {
        //**************************************Enemy Player**************************************
        //Pick a random number for the direction of the enemy player and update direction data
        updateEnemyPlayerDirectionData();
        //Update enemy player's position by the current position data
        updateEnemyPlayerPositionData(enemyPlayer);

        //**************************************Fugitive Player**************************************
        /*
        The direction of the fugitive player was already updated by the on click listener
        so we update only the position data
         */
        updateFugitivePlayerPositionData(fugitivePlayer);

        //update score
        updateScore();
    }

    private void updateFugitivePlayerPositionData(Player fugitivePlayer) {
        if (!checkIfInBoundaries(fugitivePlayer))
            updateObjectPlayerPositionData(fugitivePlayer);
    }

    private void updateEnemyPlayerPositionData(Player enemyPlayer) {
        if (!checkIfInBoundaries(enemyPlayer))
            updateObjectPlayerPositionData(enemyPlayer);
    }

    private boolean checkIfInBoundaries(Player player) {
        MoveDirection moveDirection = player.getCurrentDirection();
        int currentRow = player.getCurrentPositionRow();
        int currentCol = player.getCurrentPositionCol();

        if (moveDirection.equals(MoveDirection.UP) && currentRow == 0 ||
                moveDirection.equals(MoveDirection.DOWN) && currentRow == 4 ||
                moveDirection.equals(MoveDirection.RIGHT) && currentCol == 2 ||
                moveDirection.equals(MoveDirection.LEFT) && currentCol == 0)
            return true;

        return false;
    }


    //The method is general to update position of any object player
    private void updateObjectPlayerPositionData(Player player) {
        switch (player.getCurrentDirection()) {
            case UP:
                if (player.getCurrentPositionRow() != 0)
                    player.setCurrentPositionRow(player.getCurrentPositionRow() - 1);
                break;

            case DOWN: {
                if (player.getCurrentPositionRow() != 4)
                    player.setCurrentPositionRow(player.getCurrentPositionRow() + 1);
                break;
            }
            case RIGHT: {
                if (player.getCurrentPositionCol() != 2)
                    player.setCurrentPositionCol(player.getCurrentPositionCol() + 1);
                break;
            }

            case LEFT: {
                if (player.getCurrentPositionCol() != 0)
                    player.setCurrentPositionCol(player.getCurrentPositionCol() - 1);
                break;
            }
            case NONE:
                break;
        }
    }


    public boolean checkIfCollision() {
        int fugitiveRow = fugitivePlayer.getCurrentPositionRow();
        int fugitiveCol = fugitivePlayer.getCurrentPositionCol();
        int enemyRow = enemyPlayer.getCurrentPositionRow();
        int enemyCol = enemyPlayer.getCurrentPositionCol();

        return (fugitiveRow == enemyRow) && (fugitiveCol == enemyCol);
    }

    public void updateCollisionEventGameData() {
        reduceLives();
    }

    public boolean isGameOver() {
        return (lives <= 0);
    }
}

