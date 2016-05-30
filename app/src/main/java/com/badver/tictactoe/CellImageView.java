package com.badver.tictactoe;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class CellImageView extends AppCompatImageView {

    private CellType cellType = CellType.EMPTY;

    public CellImageView(Context context) {
        super(context);
    }

    public CellImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CellImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;

        switch (cellType) {
            case EMPTY:
//                break;
            case YELLOW:
                this.setImageResource(R.drawable.yellow);
                break;
            case RED:
                this.setImageResource(R.drawable.red);
                break;
        }
    }

    @Override
    public String toString() {
        return "CellImageView{" +
                "cellType=" + cellType +
                '}';
    }
}