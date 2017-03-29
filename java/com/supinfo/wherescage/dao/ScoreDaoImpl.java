package com.supinfo.wherescage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.supinfo.wherescage.models.ScoreDataModel;
import com.supinfo.wherescage.utils.MySQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Bagh on 24/03/2017.
 */

public class ScoreDaoImpl implements ScoreDao {

    //information table

    public static final String TABLE_NAME = "scores";
    public static final String SCORE__PLAYER = "player";
    public static final String SCORE_RESULT = "timeLong";
    public static final String SCORE_NICO = "nico";
    public static final String SCORE__MOD = "chronoMod";


    public static final String[] COLUMNS = {SCORE__PLAYER, SCORE_RESULT, SCORE_NICO, SCORE__MOD};
    private MySQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private Context context;

    public ScoreDaoImpl(Context context) {
        this.context = context;
        this.openHelper = new MySQLiteOpenHelper(this.context);
    }

    private void open() {
        this.db = this.openHelper.open(this.db);
    }

    private void close() {
        this.db = this.openHelper.close(this.db);
    }


    public static String getCreate() {
        String creation = "CREATE TABLE " + TABLE_NAME + " (" +
                "'" + SCORE__PLAYER + "' text NOT NULL," +
                "'" + SCORE_RESULT + "' text ," +
                "'" + SCORE_NICO + "' INTEGER ," +
                "'" + SCORE__MOD + "' INTEGER  NOT NULL);";

        return creation;
    }

    public static String getDrop() {
        return "DROP TABLE " + TABLE_NAME + ";";
    }

    public static String getUpgrade(int oldVersion, int currentVersion) {
        return ScoreDaoImpl.getDrop() + ScoreDaoImpl.getCreate();
    }

    @Override
    public ArrayList<ScoreDataModel> getAll(String gameMod) {
        ArrayList<ScoreDataModel> result = new ArrayList<ScoreDataModel>();
        this.open();
        // SQL
        String[] difficulte = {gameMod};

        //requete de sélection du top 10
        Cursor cursor = this.db.query(TABLE_NAME, COLUMNS, SCORE__MOD + "=?", difficulte, null, null, SCORE_RESULT, "10");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            ScoreDataModel s = new ScoreDataModel();
            s.setPlayer(cursor.getString(cursor.getColumnIndex(SCORE__PLAYER)));
            s.setScore(cursor.getString(cursor.getColumnIndex(SCORE_RESULT)));
            s.setNico(cursor.getInt(cursor.getColumnIndex(SCORE_NICO)));
            s.setDifficulte(cursor.getInt(cursor.getColumnIndex(SCORE__MOD)));


            result.add(s);
            cursor.moveToNext();
        }
        this.close();
        return result;

    }

    @Override
    public void save(ScoreDataModel score) {
        Log.d("save", "ajout dans bdd");
        this.open();

        ContentValues values = new ContentValues();
        values.put(SCORE__PLAYER, score.getPlayer());
        values.put(SCORE_RESULT, score.getScore());
        values.put(SCORE_NICO, score.getNico());
        values.put(SCORE__MOD, score.getDifficulte());

        Log.d("DEBUG", "save score");

        this.db.insert(TABLE_NAME, null, values);

        this.close();
    }

    @Override
    public void deleteAll() {
        this.open();

        this.db.execSQL("TRUNCATE TABLE " + TABLE_NAME);

        this.close();
    }
}
