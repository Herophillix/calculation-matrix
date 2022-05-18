package com.example.calculationmatrix;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundPlayer {
    public static MediaPlayer GRID_CLICK;
    public static MediaPlayer GRID_RETURN;
    public static MediaPlayer EQUATION_CORRECT;
    public static MediaPlayer EQUATION_WRONG;
    public static MediaPlayer GAME_OVER;

    public SoundPlayer(Context context)
    {
        GRID_CLICK = MediaPlayer.create(context, R.raw.grid_click);
        GRID_RETURN = MediaPlayer.create(context, R.raw.grid_return);
        EQUATION_CORRECT = MediaPlayer.create(context, R.raw.equation_correct);
        EQUATION_WRONG = MediaPlayer.create(context, R.raw.equation_wrong);
        GAME_OVER = MediaPlayer.create(context, R.raw.game_over);
    }
}
