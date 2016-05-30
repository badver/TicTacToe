package com.badver.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CellImageView[][] cells = new CellImageView[3][3];
    private MarkType activePlayer = MarkType.YELLOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createCells();
    }

    private void createCells() {

        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        assert grid != null;
        grid.setColumnCount(3);
        grid.setRowCount(3);

        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[i].length; ++j) {
                CellImageView newCell = new CellImageView(this);

                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, getResources().getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, getResources().getDisplayMetrics());
                int marginH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11f, getResources().getDisplayMetrics());
                int marginV = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11f, getResources().getDisplayMetrics());

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));
                params.width = width;
                params.height = height;
                params.setMargins(marginH, marginV, marginH, marginV);
                params.setGravity(Gravity.CENTER);
                newCell.setMarkType(MarkType.EMPTY);
                newCell.setVisibility(View.VISIBLE);
                newCell.setLayoutParams(params);
                newCell.setOnClickListener(new CellOnClickListener());

                grid.addView(newCell);
                cells[i][j] = newCell;
            }
        }
    }

    private MarkType getWinner() {
        MarkType winner = MarkType.EMPTY;

        /*
        * 0 1 2
        * 3 4 5
        * 6 7 8
        */
        final int[][] winLines = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},

                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},

                {0, 4, 8},
                {2, 4, 6}
        };


        for (int[] line : winLines) {
            if (cells[line[0] / 3][line[0] % 3].getMarkType() != MarkType.EMPTY &&
                        cells[line[0] / 3][line[0] % 3].getMarkType() == cells[line[1] / 3][line[1] % 3].getMarkType() &&
                        cells[line[1] / 3][line[1] % 3].getMarkType() == cells[line[2] / 3][line[2] % 3].getMarkType()) {
                winner = cells[line[0] / 3][line[0] % 3].getMarkType();
                break;
            }
        }

        return winner;
    }

    private void nextPlayer() {
        if (getActivePlayer() == MarkType.YELLOW) {
            setActivePlayer(MarkType.RED);
        }
        else {
            setActivePlayer(MarkType.YELLOW);
        }
    }

    private MarkType getActivePlayer() {
        return activePlayer;
    }

    private void setActivePlayer(MarkType activePlayer) {
        this.activePlayer = activePlayer;
    }

    private class CellOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CellImageView cell = (CellImageView) v;
            if (cell.getMarkType() != MarkType.EMPTY) return;

            if (activePlayer == MarkType.RED) {
                cell.setMarkType(MarkType.RED);
            }
            else {
                cell.setMarkType(MarkType.YELLOW);
            }

            MarkType winner = getWinner();
            if (winner == MarkType.EMPTY) {
                nextPlayer();
            }
            else {
                Toast.makeText(MainActivity.this, "Winner: " + winner, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
