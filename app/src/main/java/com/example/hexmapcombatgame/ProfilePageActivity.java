package com.example.hexmapcombatgame;

import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.view.View;

public class ProfilePageActivity extends AppCompatActivity {

    private CurrencyDatabaseHelper dbHelper;
    private TextView currencyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_profile_page);

        dbHelper = new CurrencyDatabaseHelper(this);
        currencyTextView = findViewById(R.id.currencyTextView);

        updateCurrencyDisplay();
    }

    public void goBackToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addCurrency(View view) {
        int currentCurrency = dbHelper.getCurrencyAmount();
        int newCurrency = currentCurrency + 100;
        dbHelper.updateCurrencyAmount(newCurrency);

        updateCurrencyDisplay();

        Log.d("Currency", "Currency added: " + newCurrency); // debug, tells terminal that currency is added
    }

    private void updateCurrencyDisplay() {
        int currentCurrency = dbHelper.getCurrencyAmount();
        currencyTextView.setText("Currency: " + currentCurrency);
        System.out.println(currentCurrency);
        Log.d("Currency", "Currency updated: "); // debug, tells terminal that currency is updated
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}