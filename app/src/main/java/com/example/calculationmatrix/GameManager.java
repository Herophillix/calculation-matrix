package com.example.calculationmatrix;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private GridButton[][] gridButtons;

    public GameManager(int h, int v, int numOfEquations)
    {
        gridButtons = new GridButton[h][v];
        for(int i = 0; i < h; ++i)
        {
            for(int j = 0; j < v; ++j)
            {
                GridButton gridButton = new GridButton();
                gridButtons[i][j] = gridButton;
            }
        }

        Random rand = new Random();

        gridButtons[h-1][v-1].SetValue(EquationManager.CreateEquation(1, 9)[0]);
        for(int i = 0; i < numOfEquations; ++i)
        {
            String[] equation = EquationManager.CreateEquation(1, 9);
            for(String value: equation)
            {
                boolean isGridEmpty = false;
                while(!isGridEmpty)
                {
                    GridButton randomGridButton = gridButtons[rand.nextInt(h)][rand.nextInt(v)];
                    if(!randomGridButton.IsOccupied())
                    {
                        randomGridButton.SetValue(value);
                        isGridEmpty = true;
                    }
                }
            }
        }
    }

    public GridButton GetGrid(int x, int y)
    {
        return gridButtons[x][y];
    }

    public void FillEmptyGridButton()
    {
        ArrayList<GridButton> emptyGridButtons = new ArrayList<>();
        GridButton a = null;
        GridButton sign = null;
        GridButton b = null;
        for(int i = 0; i < gridButtons.length; ++i)
        {
            for(int j = 0; j < gridButtons[i].length; ++j)
            {
                GridButton gridButton = gridButtons[i][j];
                if(!gridButton.IsOccupied())
                    emptyGridButtons.add(gridButton);
                else if(MathHelper.isNumeric(gridButton.GetValue()))
                {
                    if (a != null && b != null)
                    {
                        continue;
                    }
                    int value = Integer.parseInt(gridButton.GetValue());
                    if(value < 10)
                    {
                        if(a == null)
                            a = gridButton;
                        else
                            b = gridButton;
                    }
                }
                else
                {
                    sign = gridButton;
                }
            }
        }
        String[] equation = EquationManager.CreateEquation(1, 9);
        for(int i = 0; i < emptyGridButtons.size(); ++i)
        {
            emptyGridButtons.get(i).SetValue(equation[i]);
        }
        if (!(a == null || b == null || sign == null))
        {
            String result = EquationManager.GetResult(a.GetValue(), sign.GetValue(), b.GetValue());
            emptyGridButtons.get(emptyGridButtons.size() - 1).SetValue(result);
        }
    }
}
