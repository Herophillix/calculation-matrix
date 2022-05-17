package com.example.calculationmatrix.equation;

import java.util.Random;

public class EquationMaker {
    public String[] CreateEquation(int lowerBound, int upperBound)
    {
        Random rand = new Random();
        int a = rand.nextInt(upperBound) + lowerBound;
        int b = rand.nextInt(upperBound) + lowerBound;
        int sign = rand.nextInt(3);
        int c;
        String returnSign;
        switch (sign)
        {
            case 0:
            default:
                returnSign = "+";
                c = a + b;
                break;
            case 1:
                returnSign = "-";
                c = a - b;
                break;
            case 2:
                returnSign = "X";
                c = a * b;
                break;
        }
        return new String[] {
                Integer.toString(a), returnSign, Integer.toString(b), Integer.toString(c)
        };
    }
}
