package com.example.calculationmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final int HORIZONTAL = 3;
    private final int VERTICAL = 3;

    private GameManager gameManager;

    private Button[][] gridButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeGridButtons();
        InitializeGameManager();
    }

    private void InitializeGameManager()
    {
        gameManager = new GameManager(HORIZONTAL, VERTICAL, 12);

        for(int i = 0; i < HORIZONTAL; ++i)
        {
            for(int j = 0; j < VERTICAL; ++i)
            {
                gridButtons[i][j].setText(gameManager.GetGrid(i,j).GetValue());
            }
        }
    }

    private void InitializeGridButtons()
    {
        gridButtons = new Button[HORIZONTAL][VERTICAL];
        gridButtons[0][0] = findViewById(R.id.grid_1);
        gridButtons[1][0] = findViewById(R.id.grid_2);
        gridButtons[2][0] = findViewById(R.id.grid_3);
        gridButtons[0][1] = findViewById(R.id.grid_4);
        gridButtons[1][1] = findViewById(R.id.grid_5);
        gridButtons[2][1] = findViewById(R.id.grid_6);
        gridButtons[0][2] = findViewById(R.id.grid_7);
        gridButtons[1][2] = findViewById(R.id.grid_8);
        gridButtons[2][2] = findViewById(R.id.grid_9);
    }
}