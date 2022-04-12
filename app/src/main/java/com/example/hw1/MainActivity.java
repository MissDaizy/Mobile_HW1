package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    private ImageView[] game_IMG_hearts;
    private ImageView[][] game_IMG_matrix;
    private ImageView game_IMG_btnUp;
    private ImageView game_IMG_btnDown;
    private ImageView game_IMG_btnRight;
    private ImageView game_IMG_btnLeft;
    private MaterialTextView game_TXT_score;
    private GameManager gameManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameManager = new GameManager();
        findViews();
        setClickListeners();

        InitGame();
    }

    private void InitGame() {
        gameManager.initPosition();
        startTimer();
    }

    private void updateFugitivePlayerDirection(MoveDirection moveDirection) {
        gameManager.updateFugitivePlayerDirectionData(moveDirection);
    }

    private void setClickListeners() {
        game_IMG_btnUp.setOnClickListener(view -> updateFugitivePlayerDirection(MoveDirection.UP));
        game_IMG_btnDown.setOnClickListener(view -> updateFugitivePlayerDirection(MoveDirection.DOWN));
        game_IMG_btnRight.setOnClickListener(view -> updateFugitivePlayerDirection(MoveDirection.RIGHT));
        game_IMG_btnLeft.setOnClickListener(view -> updateFugitivePlayerDirection(MoveDirection.LEFT));
    }

    private void findViews() {
        game_IMG_btnUp = findViewById(R.id.game_IMG_btnUp);
        game_IMG_btnDown = findViewById(R.id.game_IMG_btnDown);
        game_IMG_btnRight = findViewById(R.id.game_IMG_btnRight);
        game_IMG_btnLeft = findViewById(R.id.game_IMG_btnLeft);
        game_TXT_score = findViewById(R.id.game_TXT_score);
        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };

        game_IMG_matrix = new ImageView[gameManager.getMatRows()][gameManager.getMatCols()];

        for (int i = 0; i < gameManager.getMatRows(); i++) {
            for (int j = 0; j < gameManager.getMatCols(); j++) {
                String imageViewName = "game_IMG_matrix_" + i + j;
                int imageId = this.getResources().getIdentifier(imageViewName, "id", this.getPackageName());
                game_IMG_matrix[i][j] = findViewById(imageId);
            }
        }
    }


    private void update() {
        gameManager.updateGameData();
        updateUI();
        if (gameManager.checkIfCollision()) {
            stopTimer();
            vibrateOnce();
            gameManager.updateCollisionEventGameData();
            updateCollisionEventUI();
            if (gameManager.isGameOver()) {
                finish();
            } else {
                showMessageOfLivesLeft();
                InitGame();
            }
        }
    }

    private void showMessageOfLivesLeft() {
        if (gameManager.getLives() == gameManager.getMaxLives() - 1)
            Snackbar.make(findViewById(R.id.game_LAYOUT_relative), R.string.two_lives_left,
                    Snackbar.LENGTH_SHORT)
                    .show();

        else if (gameManager.getLives() == gameManager.getMaxLives() - 2)
            Snackbar.make(findViewById(R.id.game_LAYOUT_relative), R.string.one_life_left,
                    Snackbar.LENGTH_SHORT)
                    .show();
    }

    private void updateCollisionEventUI() {
        for (int i = 0; i < game_IMG_hearts.length; i++) {
            game_IMG_hearts[i].setVisibility(gameManager.getLives() > i ? View.VISIBLE : View.INVISIBLE);
        }
        cleanGameMatrix();
    }

    private void updateUI() {
        cleanGameMatrix();
        updateFugitivePlayerInUI();
        updateEnemyPlayerInUI();
        updateScoreUI();
    }

    private void updateScoreUI() {
        game_TXT_score.setText("" + gameManager.getScore());
    }

    private void updateEnemyPlayerInUI() {
        int currentEnemyRow = gameManager.getCurrentEnemyPositionRow();
        int currentEnemyCol = gameManager.getCurrentEnemyPositionCol();
        game_IMG_matrix[currentEnemyRow][currentEnemyCol].setImageResource(R.drawable.ic_cat);
    }

    private void updateFugitivePlayerInUI() {
        int currentFugitiveRow = gameManager.getCurrentFugitivePositionRow();
        int currentFugitiveCol = gameManager.getCurrentFugitivePositionCol();
        game_IMG_matrix[currentFugitiveRow][currentFugitiveCol].setImageResource(R.drawable.ic_mouse);
    }

    private void cleanGameMatrix() {
        for (int i = 0; i < gameManager.getMatRows(); i++) {
            for (int j = 0; j < gameManager.getMatCols(); j++) {
                game_IMG_matrix[i][j].setImageResource(0);
            }
        }
    }

    private void vibrateOnce() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    // =====================TIMER=====================

    private final int DELAY = 1000;

    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }

    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;


    private final Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(runnable, DELAY);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    update();
                }
            });

        }
    };

    private void startTimer() {
        timerStatus = TIMER_STATUS.RUNNING;
        handler.postDelayed(runnable, DELAY);
    }

    private void stopTimer() {
        timerStatus = TIMER_STATUS.PAUSE;
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timerStatus == TIMER_STATUS.PAUSE) {
            startTimer();
        }
    }


}