package com.example.hexmapcombatgame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class DraggableHexMapView extends GridLayout {

    private int numRows = 10; // Number of rows in the hexagon grid
    private int numCols = 10; // Number of columns in the hexagon grid
    private float lastTouchX;
    private float lastTouchY;

    public DraggableHexMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setRowCount(numRows);
        setColumnCount(numCols);
        createHexagonGrid();
        updateGridPosition(0,0);
    }

    private void createHexagonGrid() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                ImageView hexagonImageView = new ImageView(getContext());
                hexagonImageView.setLayoutParams(new LayoutParams());
                hexagonImageView.setImageDrawable(getResources().getDrawable(R.drawable.hex)); // Use your hexagon image here
                addView(hexagonImageView);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = x;
                lastTouchY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // Your existing code for moving the grid
                float dx = x - lastTouchX;
                float dy = y - lastTouchY;
                updateGridPosition(dx, dy);
                invalidate();
                lastTouchX = x;
                lastTouchY = y;
                break;
        }

        return true;
    }

    private float gridTranslationX = 0;
    private float gridTranslationY = 0;
    private int columnOffset = 190;
    private int verticalOffset = 105; // Adjust this value for the vertical offset

    private void updateGridPosition(float dx, float dy) {
        gridTranslationX += dx;
        gridTranslationY += dy;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                View child = getChildAt(row * numCols + col);
                if (child instanceof ImageView) {
                    // Apply vertical offset every other column
                    int yOffset = (col % 2 == 1) ? verticalOffset : 0;

                    float newX = (col * columnOffset) + gridTranslationX;
                    float newY = row * child.getHeight() + gridTranslationY + yOffset;
                    child.setX(newX);
                    child.setY(newY);
                }
            }
        }
    }
}