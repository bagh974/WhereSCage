package com.supinfo.wherescage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.supinfo.wherescage.R;
import com.supinfo.wherescage.adapters.ScoreAdapter;
import com.supinfo.wherescage.adapters.SwipeAdapter;
import com.supinfo.wherescage.dao.ScoreDaoImpl;
import com.supinfo.wherescage.models.ScoreDataModel;

import java.util.ArrayList;

/**
 * Created by Bagh on 23/03/2017.
 */

public class HallOfFame extends AppCompatActivity {
    ViewPager viewPager;
    public static final int HALL_ACTIVITY_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);
        viewPager = (ViewPager) findViewById(R.id.view_page);
        //déclaration adapter
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hof, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.back) {
            Intent intent = new Intent(HallOfFame.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
