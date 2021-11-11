package com.example.jsonparser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DetailAdapter extends ArrayAdapter<Detail> {
    private static class ViewHolder {
        TextView title;
        TextView status;
        TextView episodes;
        TextView score;
        TextView type;
    }

    public DetailAdapter(Context context, int resource, List<Detail> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View ConvertView, ViewGroup parent){
        Detail detail = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewDetail; // view lookup cache stored in tag
        if(ConvertView==null){
            // If there's no view to re-use, inflate a brand new view for row
            viewDetail = new ViewHolder();
            ConvertView = LayoutInflater.from(getContext()).inflate(R.layout.anime_list,parent,false);
            viewDetail.title = ConvertView.findViewById(R.id.anime_title);
            viewDetail.status = ConvertView.findViewById(R.id.anime_status);
            viewDetail.episodes = ConvertView.findViewById(R.id.anime_episodes);
            viewDetail.score = ConvertView.findViewById(R.id.anime_score);
            viewDetail.type = ConvertView.findViewById(R.id.anime_type);
            // Cache the viewHolder object inside the fresh view
            ConvertView.setTag(viewDetail);
        }
        else {
            viewDetail = (ViewHolder) ConvertView.getTag();
        }

        viewDetail.title.setText(detail.getTitle());
        viewDetail.status.setText(detail.getStatus());
        viewDetail.type.setText(detail.getType());
        viewDetail.episodes.setText(detail.getEpisodes());
        viewDetail.score.setText((detail.getScore()));

        return ConvertView;
    }

}
