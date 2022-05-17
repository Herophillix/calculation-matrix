package com.example.calculationmatrix;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EquationButton {
    private Button button;
    private GridButton occupyingGridButton;

    public EquationButton(Button button)
    {
        this.button = button;
        button.setText("");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveOccupyingButton(false);
            }
        });
        SetButtonEnabled(false);
        occupyingGridButton = null;
    }

    public String GetText()
    {
        return button.getText().toString();
    }

    public void SetOccupyingButton(GridButton gridButton)
    {
        occupyingGridButton = gridButton;
        this.button.setText(gridButton.GetButton().getText());
        SetButtonEnabled(true);
    }

    public void RemoveOccupyingButton(boolean isSolved)
    {
        if(!isSolved)
            occupyingGridButton.UndoTakeTopValue();
        occupyingGridButton = null;
        this.button.setText("");
        SetButtonEnabled(false);
    }

    public boolean IsOccupied()
    {
        return occupyingGridButton != null;
    }

    public void SetButtonEnabled(boolean enabled)
    {
        button.setEnabled(enabled);
        button.setBackgroundColor(Color.parseColor(enabled ? "#ffecb8" : "#C1C1C1"));
    }
}
