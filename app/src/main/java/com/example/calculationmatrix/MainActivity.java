package com.example.calculationmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    public static final String EXTRA_TITLE = "com.example.calculationmatrix.EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "com.example.calculationmatrix.EXTRA_CONTENT";
    public static final String EXTRA_BUTTON_TEXT = "com.example.calculationmatrix.EXTRA_BUTTON_TEXT";

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
        InitializeScore();

        Intent intent = getIntent();
        boolean isFromPopup = intent.getBooleanExtra(PopupMenuActivity.EXTRA_FROM_POPUP, false);
        if(isFromPopup)
        {
            InitializeProgressBar();
        }
        else
        {
            CreatePopup("Calculation Matrix", "Select the grids to make a correct mathematical equation." +
                    "\n Don't let the timer run out and score as high as you can!", "Start");
        }
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
                SetAllButtonsEnabled(false, StaticColor.GRAY);

                CreatePopup("Game Over!", "Score: " + userScore, "Restart");
            }
        };
        countDownTimer.start();
    }

    private void InitializeScore()
    {
        scoreText = findViewById(R.id.score);
        UpdateScore();
    }

    private void CreatePopup(String title, String content, String buttonText)
    {
        Intent intent = new Intent(MainActivity.this, PopupMenuActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_BUTTON_TEXT, buttonText);
        startActivity(intent);
    }

    private void UpdateScore()
    {
        scoreText.setText("Score: " + Integer.toString(userScore));
    }

    private void AddScore(int add_value)
    {
        userScore += add_value;
        UpdateScore();
        long added_time = Math.max(5000 - (userScore * (userScore - 1)) * 7, 2500);
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

        boolean isEquationCorrect = EquationManager.CheckEquation(a, sign, b, c);

        SetAllButtonsEnabled(false, isEquationCorrect ? StaticColor.LIGHT_GREEN : StaticColor.LIGHT_RED);
        if(isEquationCorrect)
        {
            final MediaPlayer EQUATION_CORRECT = MediaPlayer.create(this, R.raw.equation_correct);
            EQUATION_CORRECT.start();
            AddScore(1);
        }
        else
        {
            final MediaPlayer EQUATION_WRONG = MediaPlayer.create(this, R.raw.equation_wrong);
            EQUATION_WRONG.start();
            CreateCountdownTimer(Math.min(timeLeft - 5000 , TIME_LIMIT), INTERVAL);
        }

        CountDownTimer timer = new CountDownTimer(500, INTERVAL) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                SetAllButtonsEnabled(true, StaticColor.LIGHT_YELLOW);
                if(isEquationCorrect)
                {
                    for(EquationButton digits: digitButtons)
                    {
                        digits.RemoveOccupyingButton();
                    }
                    signButton.RemoveOccupyingButton();
                    gameManager.FillEmptyGridButton();
                }
                else
                {
                    for(EquationButton digits: digitButtons)
                    {
                        digits.UndoOccupyingButton();
                    }
                    signButton.UndoOccupyingButton();
                }
            }
        };
        timer.start();
    }

    private void SetAllButtonsEnabled(boolean enabled, String color)
    {
        for(EquationButton digits: digitButtons)
        {
            digits.SetEnabled(enabled, color);
        }
        signButton.SetEnabled(enabled, color);
        gameManager.SetEnabled(enabled);
    }
}