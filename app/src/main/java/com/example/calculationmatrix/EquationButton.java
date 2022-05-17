package com.example.calculationmatrix;

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
                RemoveOccupyingButton();
            }
        });
        button.setEnabled(false);
        occupyingGridButton = null;
    }

    public void SetOccupyingButton(GridButton gridButton)
    {
        occupyingGridButton = gridButton;
        this.button.setText(gridButton.GetButton().getText());
        this.button.setEnabled(true);
    }

    public void RemoveOccupyingButton()
    {
        occupyingGridButton = null;
        this.button.setText("");
        this.button.setEnabled(false);
    }

    public boolean IsOccupied()
    {
        return occupyingGridButton != null;
    }
}
