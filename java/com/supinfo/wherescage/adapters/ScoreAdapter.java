package com.supinfo.wherescage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supinfo.wherescage.R;
import com.supinfo.wherescage.models.ScoreDataModel;

import java.util.List;

/**
 * Created by Bagh on 23/03/2017.
 */

public class ScoreAdapter extends BaseAdapter {

    private List<ScoreDataModel> scores;
    private Context context;

    public ScoreAdapter(List<ScoreDataModel> scores, Context context) {
        this.scores = scores;
        this.context = context;
    }

    public int getCount() {
        return scores.size();
    }

    public ScoreDataModel getItem(int position) {
        return scores.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //gérer la vue
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_score, null);
        }
        //éléments de la vue
        TextView player = (TextView) convertView.findViewById(R.id.itPlayer);
        TextView score = (TextView) convertView.findViewById(R.id.itScore);

        //item actuel
        ScoreDataModel item = getItem(position);

        player.setText(item.getPlayer());
        StringBuilder texScore = new StringBuilder();
        switch (item.getDifficulte()) {
            case 1:
                texScore.append("Timer : ")
                        .append(item.getScore());
                break;
            default:
                texScore.append("Trouvé(s) : ")
                        .append(item.getNico());
                break;
        }
        score.setText(texScore);

        return convertView;
    }

}