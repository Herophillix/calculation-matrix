package com.example.calculationmatrix;

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
        int index = 0;

        for(int i = 0; i < numOfEquations; ++i)
        {
            String[] equations = EquationManager.CreateEquation(1, 9);
            for(String value: equations)
            {
                boolean isGridSmallest = false;
                while(!isGridSmallest)
                {
                    int smallestIndex = GetLowestIndex();
                    GridButton randomGridButton = gridButtons[rand.nextInt(h)][rand.nextInt(v)];
                    if (randomGridButton.GetValuesIndex() == smallestIndex)
                    {
                        randomGridButton.AddValue(value);
                        isGridSmallest = true;
                    }
                }
            }
        }
    }

    private int GetLowestIndex()
    {
        int smallest = -1;
        for(int i = 0; i < gridButtons.length; ++i)
        {
            for(int j = 0; j < gridButtons[i].length; ++j)
            {
                int index = gridButtons[i][j].GetValuesIndex();
                if(index < smallest || smallest == -1)
                {
                    smallest = index;
                }
            }
        }
        return smallest;
    }

    public GridButton GetGrid(int x, int y)
    {
        return gridButtons[x][y];
    }
}
