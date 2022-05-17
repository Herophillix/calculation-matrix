package com.example.calculationmatrix;

import android.widget.Button;

import java.util.ArrayList;

public class GridButton {
    private ArrayList<String> values;
    private int currentIndex;
    private Button button;

    public GridButton()
    {
        values = new ArrayList<String>();
        currentIndex = -1;
    }

    public void SetButton(Button button)
    {
        this.button = button;
        button.setText(values.get(values.size() - 1));
    }

    public Button GetButton()
    {
        return button;
    }

    public void AddValue(String value)
    {
        values.add(value);
        ++currentIndex;
    }

    public int GetValuesIndex()
    {
        return values.size();
    }

    public String GetTopValue()
    {
        return values.get(currentIndex);
    }

    public void TakeTopValue()
    {
        --currentIndex;
        button.setText(values.get(currentIndex));
    }

    public void UndoTakeTopValue()
    {
        ++currentIndex;
        button.setText(values.get(currentIndex));
    }
}
