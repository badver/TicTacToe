package com.badver.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int[][] WIN_LINES = new int[][]{
        /*
        * 0 1 2
        * 3 4 5
        * 6 7 8
        */

        // horizontal
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},

        // vertical
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},

        // diagonal
        {0, 4, 8},
        {2, 4, 6}
    };
    private static final int COL = 3;
    private static final int ROW = 3;
    private static final int IMAGE_HEIGHT = 90;
    private static final int IMAGE_WIDTH = 90;
    private static final float HORIZONTAL_MARGIN = 11f;
    private static final float VERTICAL_MARGIN = 11f;
    private final CellImageView[][] cells = new CellImageView[ROW][COL];
    private MarkType activePlayer = MarkType.YELLOW;
    private Button resetButton;
    private TextView lastWinnerText;
    private boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetButton = (Button) findViewById(R.id.reset_button);
        lastWinnerText = (TextView) findViewById(R.id.last_winner_text);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCells();
                resetButton.setVisibility(View.INVISIBLE);
                lastWinnerText.setVisibility(View.INVISIBLE);
                gameOver = false;
            }
        });

        createCells();
    }

    private void resetCells() {
        for (CellImageView[] row : cells) {
            for (CellImageView cell : row) {
                cell.setMarkType(MarkType.EMPTY);
            }
        }
    }

    private void createCells() {

        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        assert grid != null;
        grid.setColumnCount(COL);
        grid.setRowCount(ROW);

        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[i].length; ++j) {

                LinearLayout lay = (LinearLayout) getLayoutInflater().inflate(R.layout.image_cell, null);
                CellImageView newCell = (CellImageView) lay.getChildAt(0);

                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, IMAGE_HEIGHT, getResources().getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, IMAGE_WIDTH, getResources().getDisplayMetrics());
                int marginH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HORIZONTAL_MARGIN, getResources().getDisplayMetrics());
                int marginV = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, VERTICAL_MARGIN, getResources().getDisplayMetrics());

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));
                params.width = width;
                params.height = height;
                params.setMargins(marginH, marginV, marginH, marginV);
                params.setGravity(Gravity.CENTER);
                newCell.setMarkType(MarkType.EMPTY);
                newCell.setVisibility(View.VISIBLE);
                lay.setLayoutParams(params);
                newCell.setOnClickListener(new CellOnClickListener());

                grid.addView(lay);
                cells[i][j] = newCell;
            }
        }
    }

    private boolean hasEmptyCell() {
        for (CellImageView[] row : cells) {
            for (CellImageView cell : row) {
                if (cell.getMarkType() == MarkType.EMPTY) {
                    return true;
                }
            }
        }
        return false;
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

    private void showGameOver() {
        gameOver = true;
        resetButton.setVisibility(View.VISIBLE);
        lastWinnerText.setVisibility(View.VISIBLE);

        String lastWinner = "No winner";
        switch (getWinner()) {
            case EMPTY:
                break;
            case RED:
                lastWinner = "Winner: Red Player";
                break;
            case YELLOW:
                lastWinner = "Winner: Yellow Player";
                break;
        }
        lastWinnerText.setText(lastWinner);
    }

    private MarkType getWinner() {
        MarkType winner = MarkType.EMPTY;

        for (int[] line : WIN_LINES) {
            if (cells[line[0] / ROW][line[0] % COL].getMarkType() != MarkType.EMPTY &&
                cells[line[0] / ROW][line[0] % COL].getMarkType() == cells[line[1] / ROW][line[1] % COL].getMarkType() &&
                cells[line[1] / ROW][line[1] % COL].getMarkType() == cells[line[2] / ROW][line[2] % COL].getMarkType()) {

                winner = cells[line[0] / ROW][line[0] % COL].getMarkType();
                break;
            }
        }

        return winner;
    }

    private class CellOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (gameOver) return;

            CellImageView cell = (CellImageView) v;
            if (cell.getMarkType() != MarkType.EMPTY) {
                Toast.makeText(MainActivity.this, "Try another cell", Toast.LENGTH_SHORT).show();
                return;
            }

            if (activePlayer == MarkType.RED) {
                cell.setMarkType(MarkType.RED);
            }
            else {
                cell.setMarkType(MarkType.YELLOW);
            }

            MarkType winner = getWinner();
            if (winner == MarkType.EMPTY) {
                if (hasEmptyCell()) {
                    nextPlayer();
                }
                else {
                    showGameOver();
                }
            }
            else {
                Toast.makeText(MainActivity.this, "Winner: " + winner, Toast.LENGTH_SHORT).show();
                showGameOver();
            }
        }
    }
}
