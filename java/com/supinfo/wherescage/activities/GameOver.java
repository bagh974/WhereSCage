package com.supinfo.wherescage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.supinfo.wherescage.R;
import com.supinfo.wherescage.adapters.ScoreAdapter;
import com.supinfo.wherescage.dao.ScoreDaoImpl;
import com.supinfo.wherescage.models.ScoreDataModel;
import com.supinfo.wherescage.utils.ScoreListTextviewListener;

import java.util.ArrayList;

/**
 * Created by Bagh on 25/03/2017.
 */

public class GameOver extends AppCompatActivity {

    int MODE_JEU;
    int nbNicoFound;
    String timeString;
    ScoreDaoImpl sDAO;
    public ArrayList<ScoreDataModel> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        Intent intentRecut = getIntent();

        MODE_JEU = intentRecut.getIntExtra("MODE_JEU", 0);
        nbNicoFound = intentRecut.getIntExtra("nbNicoFind", 0);
        timeString = intentRecut.getStringExtra("timeString");

        TextView scoreDiff = (TextView) findViewById(R.id.diffMod);
        TextView score = (TextView) findViewById(R.id.scoreResult);
        //TextView nico = (TextView) findViewById(R.id.scoreNico);

        String str = String.valueOf(MODE_JEU);
        switch (str){
            case "1": scoreDiff.setText(getString(R.string.lvl_name, getString(R.string.mod_1)));
                break;
            case "2": scoreDiff.setText(getString(R.string.lvl_name, getString(R.string.mod_2)));
                break;
            case "3": scoreDiff.setText(getString(R.string.lvl_name, getString(R.string.mod_3)));
                break;
        }
        //scoreDiff.setText(str);

        switch (str) {
            case "1":
                score.setText(timeString);
                break;
            default:
                score.setText(String.valueOf(nbNicoFound));
                break;
        }

        //score.setText(timeString);
        //nico.setText(String.valueOf(nbNicoFound));

        this.sDAO = new ScoreDaoImpl(this);

        ScoreListTextviewListener.setActivity(this);

    }

    //Création de fin de partie
    public void add(View view) {
        Log.d("cra", "ADD CLIK SUR ADD  !!!!");
        EditText scorePlayer = (EditText) findViewById(R.id.playerName);
        TextView timeLong = (TextView) findViewById(R.id.scoreResult);

        Log.d("cra", "Recupe des vues faite !");

        String sP = scorePlayer.getText().toString().trim();
        Log.d("cra", "sP :" + sP);
        String tL = timeLong.getText().toString();
        Log.d("cra", "tL :" + tL);
        int sD = MODE_JEU; //ICI
        Log.d("cra", "sD :" + sD);
        int nNF = nbNicoFound;
        Log.d("cra", "nNF :" + nNF);

        if (sP == null) {
            sP = "";
        }
        if (tL == null) {
            tL = "";
        }
//        if(sD == null){sD=0;}
//        if(nNF == null){nNF=0;}

        Log.d("cra", "sP :" + sP);
        Log.d("cra", "tL :" + tL);
        Log.d("cra", "sD :" + sD);
        Log.d("cra", "nNF :" + nNF);

        if (scorePlayer.getText().toString().trim().length() > 0) {
            this.addScore(sP, tL, sD, nNF);
            Toast.makeText(this, "Score added for " + sP, Toast.LENGTH_SHORT).show();
            //Faire une redirection vers le bon HoF
            Intent intent = new Intent(GameOver.this, HallOfFame.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Name can't be empty !", Toast.LENGTH_LONG).show();
        }

    }

    //Ajout du score à la bdd
    public void addScore(String scorePlayer, String timeLong, int scoreDiff, int nico) {
        Log.d("tot", "un score doit etre ajouté");
        ScoreDataModel score = new ScoreDataModel();
        score.setPlayer(scorePlayer);
        score.setScore(timeLong);
        score.setNico(nico);
        score.setDifficulte(scoreDiff);

        this.sDAO.save(score);
        //Toast.makeText(this, "Score added for "+score.getPlayer() , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(GameOver.this, HallOfFame.class);
        startActivity(intent);

    }

}
