package com.example.calculationmatrix;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EquationButton {
    private Button button;
    private GridButton occupyingGridButton;

    public EquationButton(Button button, MediaPlayer soundEffect)
    {
        this.button = button;
        button.setText("");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UndoOccupyingButton();
                soundEffect.start();
            }
        });
        SetButtonText("");
        occupyingGridButton = null;
    }

    public String GetText()
    {
        return button.getText().toString();
    }

    public void SetOccupyingButton(GridButton gridButton)
    {
        occupyingGridButton = gridButton;
        SetButtonText(gridButton.GetButton().getText().toString());

        gridButton.TakeValue();
    }

    public void UndoOccupyingButton()
    {
        occupyingGridButton.UndoTakeValue();
        occupyingGridButton = null;
        SetButtonText("");
    }

    public void RemoveOccupyingButton()
    {
        occupyingGridButton.RemoveValue();
        occupyingGridButton = null;
        SetButtonText("");
    }

    public boolean IsOccupied()
    {
        return occupyingGridButton != null;
    }

    private void SetButtonText(String text)
    {
        button.setText(text);
        boolean enabled = !text.equals("");
        button.setEnabled(enabled);
        if(enabled)
        {
            button.setBackgroundColor(Color.parseColor(MathHelper.isNumeric(text) ? StaticColor.LIGHT_YELLOW : StaticColor.LIGHT_BLUE));
        }
        else
        {
            button.setBackgroundColor(Color.parseColor(StaticColor.GRAY));
        }
    }

    public void SetEnabled(boolean enabled, String color)
    {
        button.setEnabled(enabled);
        button.setBackgroundColor(Color.parseColor(color));
    }
}
