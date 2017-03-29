package com.supinfo.wherescage.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.supinfo.wherescage.activities.HallOfFame;
import com.supinfo.wherescage.models.ScoreDataModel;

/**
 * Created by Bagh on 28/03/2017.
 */

public class ScoreListTextviewListener implements View.OnClickListener{
    private static Activity activity;
    private int id;
    private ScoreDataModel score;


    public ScoreListTextviewListener(int id, ScoreDataModel score) {
        this.id = id;
        this.score = score;
    }

    public static void setActivity(Activity a) {
        activity = a;
    }

    public void setQuote(ScoreDataModel score) {
        this.score = score;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, HallOfFame.class);
        intent.putExtra("id", this.id);
        intent.putExtra("score", this.score);
        this.activity.startActivityForResult(intent, HallOfFame.HALL_ACTIVITY_CODE);


    }
}
