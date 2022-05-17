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
                UndoOccupyingButton();
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

        gridButton.TakeTopValue();
    }

    private void UndoOccupyingButton()
    {
        occupyingGridButton.UndoTakeTopValue();
        RemoveOccupyingButton();
    }

    public void RemoveOccupyingButton()
    {
        occupyingGridButton.SetButtonEnabled(true);
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
        button.setBackgroundColor(Color.parseColor(enabled ? "#ffecb8" : "#C1C1C1"));
    }
}
