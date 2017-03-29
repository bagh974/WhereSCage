package com.supinfo.wherescage.dao;

import com.supinfo.wherescage.models.ScoreDataModel;

import java.util.ArrayList;

/**
 * Created by Bagh on 24/03/2017.
 */

public interface ScoreDao {

    //récupération du top 10 par niveau
    public ArrayList<ScoreDataModel> getAll(String gameMod);

    //enregistrement d'un nouveau score
    public void save(ScoreDataModel score);

    //clean la bdd
    public void deleteAll();



}
