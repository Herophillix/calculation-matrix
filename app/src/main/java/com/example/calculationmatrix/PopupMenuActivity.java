package com.example.calculationmatrix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopupMenuActivity extends Activity {
    public final static String EXTRA_FROM_POPUP = "com.example.calculationmatrix.EXTRA_FROM_POPUP";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_menu);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));
        InitializePopupContent();
    }

    private void InitializePopupContent()
    {
        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.EXTRA_TITLE);
        String content = intent.getStringExtra(MainActivity.EXTRA_CONTENT);
        String button = intent.getStringExtra(MainActivity.EXTRA_BUTTON_TEXT);

        TextView titleText = findViewById(R.id.popup_title);
        TextView contentText = findViewById(R.id.popup_content);
        Button buttonText = findViewById(R.id.popup_button);

        titleText.setText(title);
        contentText.setText(content);
        buttonText.setText(button);
        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopupMenuActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_FROM_POPUP, true);
                startActivity(intent);
            }
        });
    }

}
