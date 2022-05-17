package com.example.calculationmatrix.grid;

import java.util.ArrayList;

public class Grid {
    private ArrayList<String> values;

    public Grid()
    {
        values = new ArrayList<String>();
    }

    public void AddValue(String value)
    {
        values.add(value);
    }

    public int GetValuesIndex()
    {
        return values.size();
    }

    public String GetValue()
    {
        return values.get(values.size() - 1);
    }
}
