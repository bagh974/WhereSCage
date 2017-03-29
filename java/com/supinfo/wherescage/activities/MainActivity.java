package com.supinfo.wherescage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.supinfo.wherescage.R;

import static android.graphics.Color.LTGRAY;
import static android.graphics.Color.YELLOW;

public class MainActivity extends AppCompatActivity {

    RadioGroup gameModSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameModSelection = (RadioGroup) findViewById(R.id.selectionMod);
        gameModSelection.check(R.id.radMod1);


    }


    public void play(View view) {
        if (getSelectedGameMod() != -1) {
            Intent intent = new Intent(MainActivity.this, PlayFullScreen.class);
            intent.putExtra("MODE_JEU", getSelectedGameMod());
            startActivity(intent);
        }
    }

    public void hallOfFame(View view) {
        if (getSelectedGameMod() != -1) {
            Intent intent = new Intent(MainActivity.this, HallOfFame.class);
            intent.putExtra("MODE_JEU", getSelectedGameMod());
            startActivity(intent);
        }
    }

    private int getSelectedGameMod() {
        int selectedGameMod = -1;
        switch (gameModSelection.getCheckedRadioButtonId()) {
            case R.id.radMod1:
                selectedGameMod = 1;
                break;
            case R.id.radMod2:
                selectedGameMod = 2;
                break;
            case R.id.radMod3:
                selectedGameMod = 3;
                break;
        }
        return selectedGameMod;
    }
}
