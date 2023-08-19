package com.example.hexmapcombatgame;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    private int highlightedRow = -1; // Track the currently highlighted hexagon row
    private int highlightedCol = -1; // Track the currently highlighted hexagon column

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = x;
                lastTouchY = y;

                // Check if there is a highlighted hexagon
                if (highlightedRow != -1 && highlightedCol != -1) {
                    // Clear the highlight by resetting the background color
                    View highlightedHexagon = getChildAt(highlightedRow * numCols + highlightedCol);
                    highlightedHexagon.setBackgroundResource(0); // Clear background color

                    // Reset the highlighted coordinates
                    highlightedRow = -1;
                    highlightedCol = -1;

                    return true; // Exit the onTouchEvent
                }

                // Iterate through hexagons to check for click
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols; col++) {
                        View child = getChildAt(row * numCols + col);
                        if (child instanceof ImageView) {
                            if (isInsideHexagon(x, y, child)) {
                                // Highlight the hexagon
                                child.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                                // Store the highlighted coordinates
                                highlightedRow = row;
                                highlightedCol = col;

                                // Print the coordinates to the terminal
                                System.out.println("Clicked hexagon coordinates - Row: " + row + ", Col: " + col);

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

    private boolean isInsideHexagon(float x, float y, View hexagonView) {
        // Get the position and size of the hexagon view
        float hexagonX = hexagonView.getX();
        float hexagonY = hexagonView.getY();
        int hexagonWidth = hexagonView.getWidth();
        int hexagonHeight = hexagonView.getHeight();

        // Check if the touch coordinates are inside the hexagon
        boolean isInside = x >= hexagonX && x <= hexagonX + hexagonWidth &&
                y >= hexagonY && y <= hexagonY + hexagonHeight;

        return isInside;
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