package com.example.calculationmatrix;

import com.example.calculationmatrix.equation.EquationMaker;
import com.example.calculationmatrix.grid.Grid;

import java.util.Random;

public class GameManager {
    private Grid[][] grids;

    public GameManager(int h, int v, int numOfEquations)
    {
        grids = new Grid[h][v];
        for(int i = 0; i < h; ++i)
        {
            for(int j = 0; j < v; ++j)
            {
                Grid grid = new Grid();
                grids[i][j] = grid;
            }
        }

        EquationMaker eq = new EquationMaker();
        Random rand = new Random();
        int horizontalSize = grids.length;
        int verticalSize = grids[0].length;

        for(int i = 0; i < numOfEquations; ++i)
        {
            String[] equations = eq.CreateEquation(1, 9);
            for(String value: equations)
            {
                boolean isGridSmallest = false;
                while(!isGridSmallest)
                {
                    int smallestIndex = GetLowestIndex();
                    Grid randomGrid = grids[rand.nextInt(h)][rand.nextInt(v)];
                    if (randomGrid.GetValuesIndex() == smallestIndex)
                    {
                        randomGrid.AddValue(value);
                        isGridSmallest = true;
                    }
                }
            }
        }
    }

    private int GetLowestIndex()
    {
        int smallest = -1;
        for(int i = 0; i < grids.length; ++i)
        {
            for(int j = 0; j < grids[i].length; ++j)
            {
                int index = grids[i][j].GetValuesIndex();
                if(index < smallest || smallest == -1)
                {
                    smallest = index;
                }
            }
        }
        return smallest;
    }

    public Grid GetGrid(int x, int y)
    {
        return grids[x][y];
    }
}
