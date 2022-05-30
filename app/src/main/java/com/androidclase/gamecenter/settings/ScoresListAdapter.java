package com.androidclase.gamecenter.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidclase.gamecenter.R;

import java.util.ArrayList;

public class ScoresListAdapter extends RecyclerView.Adapter<ScoresListAdapter.ScoreViewHolder> {

    ArrayList<Scores> scoresList;

    public ScoresListAdapter(ArrayList<Scores> scoresList) {
        this.scoresList = scoresList;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scores_list_item,
                null, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        holder.viewUser.setText(scoresList.get(position).getUser());
        holder.viewGame.setText(scoresList.get(position).getGame());
        holder.viewScore.setText(scoresList.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return scoresList.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        TextView viewUser, viewGame, viewScore;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);

            viewUser = itemView.findViewById(R.id.tv_user);
            viewGame = itemView.findViewById(R.id.tv_game);
            viewScore = itemView.findViewById(R.id.tv_score);
        }
    }
}
