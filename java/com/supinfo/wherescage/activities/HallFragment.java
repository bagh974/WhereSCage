package com.supinfo.wherescage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.supinfo.wherescage.R;
import com.supinfo.wherescage.adapters.ScoreAdapter;
import com.supinfo.wherescage.dao.ScoreDaoImpl;
import com.supinfo.wherescage.models.ScoreDataModel;

import java.util.ArrayList;

/**
 * Created by Bagh on 26/03/2017.
 */

public class HallFragment extends Fragment {
    TextView textView;
    private ScoreDaoImpl sDAO;

    public HallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //création du fragment
        View view = inflater.inflate(R.layout.fragment_hall, container, false);
        textView = (TextView) view.findViewById(R.id.section_label);
        ListView scoresView = (ListView) view.findViewById(R.id.scoresView);

        //récupération des arguments = bundle du SwipeAdapter
        Bundle bundle = getArguments();
        String gameMod = Integer.toString(bundle.getInt("mod"));
        //affichage de la difficulté
        switch (gameMod){
            case "1": textView.setText(getString(R.string.lvl_name, getString(R.string.mod_1)));
                break;
            case "2": textView.setText(getString(R.string.lvl_name, getString(R.string.mod_2)));
                break;
            case "3": textView.setText(getString(R.string.lvl_name, getString(R.string.mod_3)));
                break;
        }

        sDAO = new ScoreDaoImpl(this.getActivity());

        //récupère et affiche les scores
        ArrayList<ScoreDataModel> array = sDAO.getAll(gameMod);
        ScoreAdapter adapter = new ScoreAdapter(array, this.getActivity());
        scoresView.setAdapter(adapter);

        return view;
    }

}
