package com.supinfo.wherescage.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.supinfo.wherescage.dao.ScoreDaoImpl;
import com.supinfo.wherescage.models.ScoreDataModel;

/**
 * Created by Bagh on 24/03/2017.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //information bdd
    public static final int VERSIONDB = 1;
    private static final String DATABASE_NAME = "halloffame.db";
    ScoreDaoImpl sDao;

    //information table


    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSIONDB);
        //sDao = new ScoreDaoImpl(context);
    }

    public SQLiteDatabase open(SQLiteDatabase db) {

        if(db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }

        return db;
    }


    public SQLiteDatabase close(SQLiteDatabase db){

        if(db.isOpen() && db != null) {
            db.close();
        }
        return db;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
/*        ScoreDataModel score = new ScoreDataModel();
        score.setPlayer("Bagh");
        score.setScore("0:01:0942");
        score.setDifficulte(1);*/
        db.execSQL(ScoreDaoImpl.getCreate());
        //sDao.populate(score);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(ScoreDaoImpl.getDrop());
        db.execSQL(ScoreDaoImpl.getCreate());
        db.execSQL(ScoreDaoImpl.getUpgrade(oldVersion, newVersion));


    }
}
