package com.supinfo.wherescage.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.supinfo.wherescage.activities.HallFragment;

/**
 * Created by Bagh on 26/03/2017.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {
    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new HallFragment();
        Bundle bundle = new Bundle();
        //Accorde le fragment au niveau de difficulté
        int gameMod = 0;
        switch (i){
            case 0: gameMod = 1;
                break;
            case 1: gameMod = 2;
                break;
            case 2: gameMod = 3;
                break;
        }
        bundle.putInt("mod", gameMod);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
