package com.example.hexmapcombatgame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import com.example.hexmapcombatgame.gameelements.Hexagon;

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
        updateGridPosition(0.1f,0.1f);
    }

    private void createHexagonGrid() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Hexagon hexagon = new Hexagon(getContext(), row, col);
                hexagon.setLayoutParams(new LayoutParams());
                hexagon.setImageDrawable(getResources().getDrawable(R.drawable.hex)); // Use your hexagon image here
                addView(hexagon);


            }
        }
    }
    private boolean isInsideHexagon(float x, float y, View hexagonView) {
        // Get the position and size of the hexagon view
        float hexagonX = hexagonView.getX();
        float hexagonY = hexagonView.getY();
        int hexagonWidth = hexagonView.getWidth();
        int hexagonHeight = hexagonView.getHeight();

        // Calculate the coordinates of the six corners of the hexagon
        float[] cornersX = {
                hexagonX + hexagonWidth / 4, hexagonX + 3 * hexagonWidth / 4,
                hexagonX + hexagonWidth, hexagonX + 3 * hexagonWidth / 4,
                hexagonX + hexagonWidth / 4, hexagonX
        };
        float[] cornersY = {
                hexagonY, hexagonY,
                hexagonY + hexagonHeight / 2, hexagonY + hexagonHeight,
                hexagonY + hexagonHeight, hexagonY + hexagonHeight / 2
        };

        // Check if the touch coordinates are inside the hexagon
        boolean isInside = false;
        for (int i = 0, j = 5; i < 6; j = i++) {
            if (((cornersY[i] > y) != (cornersY[j] > y)) &&
                    (x < (cornersX[j] - cornersX[i]) * (y - cornersY[i]) / (cornersY[j] - cornersY[i]) + cornersX[i])) {
                isInside = !isInside;
            }
        }

        return isInside;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = x;
                lastTouchY = y;

                // Iterate through hexagons to check for click
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols; col++) {
                        View child = getChildAt(row * numCols + col);
                        if (child instanceof Hexagon) {
                            if (isInsideHexagon(x, y, child)) {
                                // Handle hexagon click here
                                Hexagon hexagon = (Hexagon) child;
                                int clickedRow = hexagon.getRow();
                                int clickedCol = hexagon.getCol();
                                System.out.println("Clicked hexagon coordinates - Row: " + clickedRow + ", Col: " + clickedCol);

                                // Perform any other desired action here

                                return true; // Exit the onTouchEvent
                            }
                        }
                    }
                }

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