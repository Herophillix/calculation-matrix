package com.example.calculationmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int HORIZONTAL_SIZE = 3;
    private final int VERTICAL_SIZE = 3;
    final long TIME_LIMIT = 30000;
    final long INTERVAL = 50;

    private GameManager gameManager;
    private ArrayList<EquationButton> digitButtons;
    private EquationButton signButton;

    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    long timeLeft;

    int userScore;
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeGameManager();
        InitializeEquationButtons();
        InitializeProgressBar();
        InitializeScore();
    }

    private void InitializeGameManager()
    {
        gameManager = new GameManager(HORIZONTAL_SIZE, VERTICAL_SIZE, 2);
        Button[][] buttons = new Button[VERTICAL_SIZE][HORIZONTAL_SIZE];

        buttons[0][0] = findViewById(R.id.grid_1);
        buttons[1][0] = findViewById(R.id.grid_2);
        buttons[2][0] = findViewById(R.id.grid_3);
        buttons[0][1] = findViewById(R.id.grid_4);
        buttons[1][1] = findViewById(R.id.grid_5);
        buttons[2][1] = findViewById(R.id.grid_6);
        buttons[0][2] = findViewById(R.id.grid_7);
        buttons[1][2] = findViewById(R.id.grid_8);
        buttons[2][2] = findViewById(R.id.grid_9);

        final MediaPlayer GRID_CLICK = MediaPlayer.create(this, R.raw.grid_click);

        for(int i = 0; i < HORIZONTAL_SIZE; ++i)
        {
            for(int j = 0; j < VERTICAL_SIZE; ++j)
            {
                Button button = buttons[i][j];
                GridButton gridButton = gameManager.GetGrid(i, j);
                gridButton.SetButton(button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnGridButtonSelected(gridButton);
                        GRID_CLICK.start();
                    }
                });
            }
        }
    }

    private void InitializeEquationButtons()
    {
        final MediaPlayer GRID_RETURN = MediaPlayer.create(this, R.raw.grid_return);

        digitButtons = new ArrayList<>();
        digitButtons.add(new EquationButton(findViewById(R.id.lhs_1), GRID_RETURN));
        digitButtons.add(new EquationButton(findViewById(R.id.lhs_2), GRID_RETURN));
        digitButtons.add(new EquationButton(findViewById(R.id.ans), GRID_RETURN));

        signButton = new EquationButton(findViewById(R.id.lhs_sign), GRID_RETURN);
    }

    private void InitializeProgressBar()
    {
        progressBar =(ProgressBar)findViewById(R.id.progressbar);
        progressBar.setProgress(0);

        CreateCountdownTimer(TIME_LIMIT, INTERVAL);
    }

    private void CreateCountdownTimer(long timeLimit, long interval)
    {
        if(countDownTimer != null)
            countDownTimer.cancel();
        MainActivity instance = this;
        countDownTimer = new CountDownTimer(timeLimit,interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                progressBar.setProgress((int)((TIME_LIMIT - timeLeft)/(double)TIME_LIMIT * 100));
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(100);

                final MediaPlayer GAME_OVER = MediaPlayer.create(instance, R.raw.game_over);
                GAME_OVER.start();

                Toast toast = Toast.makeText(getApplicationContext(), "Game Over!", Toast.LENGTH_LONG);
                toast.show();
                CountDownTimer delay = new CountDownTimer(3000, INTERVAL) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        finish();
                        startActivity(getIntent());
                    }
                };
                delay.start();
            }
        };
        countDownTimer.start();
    }

    private void InitializeScore()
    {
        scoreText = findViewById(R.id.score);
        UpdateScore();
    }

    private void UpdateScore()
    {
        scoreText.setText("Score: " + Integer.toString(userScore));
    }

    private void AddScore(int add_value)
    {
        userScore += add_value;
        UpdateScore();
        long added_time = Math.max(5000 - userScore * 300, 2000);
        CreateCountdownTimer(Math.min(timeLeft + added_time , TIME_LIMIT), INTERVAL);
    }

    private void OnGridButtonSelected(GridButton gridButton)
    {
        String value = gridButton.GetValue();
        if(MathHelper.isNumeric(value))
        {
            for(EquationButton digits: digitButtons)
            {
                if(!digits.IsOccupied())
                {
                    digits.SetOccupyingButton(gridButton);
                    break;
                }
            }
        }
        else
        {
            if(!signButton.IsOccupied())
            {
                signButton.SetOccupyingButton(gridButton);
            }
        }

        for(EquationButton digits: digitButtons)
        {
            if(!digits.IsOccupied())
            {
                return;
            }
        }
        if(!signButton.IsOccupied())
            return;
        String a = digitButtons.get(0).GetText();
        String sign = signButton.GetText();
        String b = digitButtons.get(1).GetText();
        String c = digitButtons.get(2).GetText();
        if(EquationManager.CheckEquation(a, sign, b, c))
        {
            for(EquationButton digits: digitButtons)
            {
                digits.SetEnabled(false);
            }
            signButton.SetEnabled(false);
            gameManager.SetEnabled(false);
            final MediaPlayer EQUATION_CORRECT = MediaPlayer.create(this, R.raw.equation_correct);
            EQUATION_CORRECT.start();
            CountDownTimer timer = new CountDownTimer(500, INTERVAL) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    for(EquationButton digits: digitButtons)
                    {
                        digits.SetEnabled(true);
                        digits.RemoveOccupyingButton();
                    }
                    signButton.SetEnabled(true);
                    signButton.RemoveOccupyingButton();
                    gameManager.SetEnabled(true);
                    gameManager.FillEmptyGridButton();
                    AddScore(1);
                }
            };
            timer.start();
        }
        else
        {
            final MediaPlayer EQUATION_WRONG = MediaPlayer.create(this, R.raw.equation_wrong);
            EQUATION_WRONG.start();
        }
    }
}