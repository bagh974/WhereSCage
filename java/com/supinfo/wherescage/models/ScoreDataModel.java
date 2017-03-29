package com.supinfo.wherescage.models;

import java.io.Serializable;

/**
 * Created by Bagh on 23/03/2017.
 */

public class ScoreDataModel implements Serializable {
    private String player;
    private String score;
    private int nico;
    private int difficulte;


    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getNico() {
        return nico;
    }

    public void setNico(int nico) {
        this.nico = nico;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }
}
