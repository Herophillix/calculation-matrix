package com.example.calculationmatrix;

import android.graphics.Color;
import android.widget.Button;

import java.util.ArrayList;

public class GridButton {
    private String value;
    private Button button;

    public GridButton()
    {
        value = "";
    }

    public void SetButton(Button button)
    {
        this.button = button;
        SetButtonText(value);
    }

    public Button GetButton()
    {
        return button;
    }

    public void SetValue(String value)
    {
        this.value = value;
        SetButtonText(value);
    }

    public String GetValue()
    {
        return value;
    }

    public void TakeValue()
    {
        SetButtonText("");
    }

    public void UndoTakeValue()
    {
        SetButtonText(value);
    }

    public void RemoveValue()
    {
        value = "";
        SetButtonText("");
    }

    public boolean IsOccupied()
    {
        return !value.equals("");
    }

    public void SetButtonText(String text)
    {
        if(button == null)
            return;
        button.setText(text);
        boolean enabled = !text.equals("");
        button.setEnabled(enabled);
        if(enabled)
        {
            button.setBackgroundColor(Color.parseColor(MathHelper.isNumeric(value) ? StaticColor.LIGHT_YELLOW : StaticColor.LIGHT_BLUE));
        }
        else
        {
            button.setBackgroundColor(Color.parseColor(StaticColor.GRAY));
        }
    }
}
