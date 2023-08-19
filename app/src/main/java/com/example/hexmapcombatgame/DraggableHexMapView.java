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
                float dx = x - lastTouchX;
                float dy = y - lastTouchY;
                // Update the position of the hexagonal grid based on dx and dy
                // Invalidate the view to trigger redraw
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

    private void updateGridPosition(float dx, float dy) {
        gridTranslationX += dx;
        gridTranslationY += dy;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ImageView) {
                child.setTranslationX(gridTranslationX);
                child.setTranslationY(gridTranslationY);
            }
        }
    }

}