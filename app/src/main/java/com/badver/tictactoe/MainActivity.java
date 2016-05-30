package com.badver.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    private CellImageView[][] cells = new CellImageView[3][3];

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

                CellImageView c = new CellImageView(this);

                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, getResources().getDisplayMetrics());
                int width = height;
                int marginH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11f, getResources().getDisplayMetrics());
                int marginV = marginH;

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));
                params.width = width;
                params.height = height;
                params.setMargins(marginH, marginV, marginH, marginV);
                params.setGravity(Gravity.CENTER);
                c.setCellType(CellType.EMPTY);
                c.setVisibility(View.VISIBLE);
                c.setLayoutParams(params);
                grid.addView(c);


                cells[i][j] = c;

                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CellImageView c = (CellImageView) v;
                        if (c.getCellType() == CellType.EMPTY || c.getCellType() == CellType.YELLOW) {
                            c.setCellType(CellType.RED);
                        } else {
                            c.setCellType(CellType.YELLOW);
                        }
                    }
                });
            }

        }
    }
}
