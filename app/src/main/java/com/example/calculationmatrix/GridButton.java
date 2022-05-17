package com.example.calculationmatrix;

import android.graphics.Color;
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
        SetButtonEnabled(true);
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
        if(currentIndex >= 0)
        {
            button.setText(values.get(currentIndex));
        }
        else
        {
            button.setText("");
        }
        SetButtonEnabled(false);
    }

    public void UndoTakeTopValue()
    {
        ++currentIndex;
        button.setText(values.get(currentIndex));
        SetButtonEnabled(true);
    }

    public void SetButtonEnabled(boolean enabled)
    {
        if(currentIndex < 0)
        {
            button.setEnabled(false);
            button.setBackgroundColor(Color.parseColor("#C1C1C1"));
        }
        else
        {
            button.setEnabled(enabled);
            button.setBackgroundColor(Color.parseColor(enabled ? "#ffecb8" : "#C1C1C1"));
        }
    }
}
