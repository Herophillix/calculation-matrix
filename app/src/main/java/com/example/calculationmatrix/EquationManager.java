package com.example.calculationmatrix;

import java.util.Random;

public class EquationManager {
    public static String[] CreateEquation(int lowerBound, int upperBound)
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

    public static boolean CheckEquation(String a, String sign, String b, String c)
    {
        int int_a = Integer.parseInt(a);
        int int_b = Integer.parseInt(b);
        int int_c = Integer.parseInt(c);


        switch (sign)
        {
            case "+":
                return int_a + int_b == int_c;
            case "-":
                return int_a - int_b == int_c;
            case "X":
                return int_a * int_b == int_c;
            default:
                return false;
        }
    }
}
