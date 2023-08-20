package com.example.hexmapcombatgame.gameelements;

import android.content.Context;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;

public class Hexagon extends AppCompatImageView {


    private int row;
    private int col;

    public Hexagon(Context context, int row, int col) {
        super(context);
        this.row = row;
        this.col = col;

//        setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Handle hexagon click here
//                System.out.println("Clicked hexagon coordinates - Row: " + row + ", Col: " + col);
//            }
//        });
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}