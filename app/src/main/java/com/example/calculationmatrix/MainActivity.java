package com.example.calculationmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int HORIZONTAL_SIZE = 3;
    private final int VERTICAL_SIZE = 3;

    private GameManager gameManager;
    private ArrayList<EquationButton> digitButtons;
    private EquationButton signButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeGameManager();
        InitializeEquationButtons();
    }

    private void InitializeGameManager()
    {
        gameManager = new GameManager(HORIZONTAL_SIZE, VERTICAL_SIZE, 12);
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
                    }
                });
            }
        }
    }

    private void InitializeEquationButtons()
    {
        digitButtons = new ArrayList<>();
        digitButtons.add(new EquationButton(findViewById(R.id.lhs_1)));
        digitButtons.add(new EquationButton(findViewById(R.id.lhs_2)));
        digitButtons.add(new EquationButton(findViewById(R.id.ans)));

        signButton = new EquationButton(findViewById(R.id.lhs_sign));
    }

    private void OnGridButtonSelected(GridButton gridButton)
    {
        String value = gridButton.GetTopValue();
        if(isNumeric(value))
        {
            for(EquationButton digits: digitButtons)
            {
                if(!digits.IsOccupied())
                {
                    digits.SetOccupyingButton(gridButton);
                    gridButton.TakeTopValue();
                    break;
                }
            }
        }
        else
        {
            if(!signButton.IsOccupied())
            {
                signButton.SetOccupyingButton(gridButton);
                gridButton.TakeTopValue();
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
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}