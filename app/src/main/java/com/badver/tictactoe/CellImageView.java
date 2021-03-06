package com.badver.tictactoe;

import android.content.Context;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class CellImageView extends AppCompatImageView {

    private static final int ANIMATION_DURATION = 300;
    private static final float ROTATION_ANGLE = 360f;
    private MarkType markType = MarkType.EMPTY;

    public CellImageView(Context context) {
        super(context);
    }

    public CellImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CellImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MarkType getMarkType() {
        return markType;
    }

    public void setMarkType(MarkType markType) {
        this.markType = markType;

        switch (markType) {
            case EMPTY:
                setImageResource(0);
                break;
            case YELLOW:
                showImage(R.drawable.yellow);
                break;
            case RED:
                showImage(R.drawable.red);
                break;
        }
    }

    private void showImage(int red) {
        setAlpha(0.0f);
        setRotation(0);
        setImageResource(red);
        animate().alpha(1.0f).setInterpolator(new FastOutSlowInInterpolator()).rotation(ROTATION_ANGLE).setDuration(ANIMATION_DURATION);
    }

    @Override
    public String toString() {
        return "CellImageView{" +
                       "markType=" + markType +
                '}';
    }
}
