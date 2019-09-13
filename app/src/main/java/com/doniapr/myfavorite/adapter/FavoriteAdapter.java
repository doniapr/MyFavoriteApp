package com.doniapr.myfavorite.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doniapr.myfavorite.Favorite;
import com.doniapr.myfavorite.R;
import com.doniapr.myfavorite.detail.DetailActivity;

import java.util.ArrayList;

import static com.doniapr.myfavorite.detail.DetailActivity.EXTRA_ID;
import static com.doniapr.myfavorite.detail.DetailActivity.EXTRA_TYPE;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<Favorite> favItem = new ArrayList<>();

    public void setData(Cursor cursor) {
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow("poster"));
            int isMovie = cursor.getInt(cursor.getColumnIndexOrThrow("isMovie"));
            favItem.add(new Favorite(id, title, rating, poster, isMovie));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, int i) {
        holder.txtTitle.setText(favItem.get(i).getTitle());
        final float value = favItem.get(i).getRating();
        holder.rating.setRating(value);
        holder.rating.setIsIndicator(true);
        Glide.with(holder.itemView.getContext())
                .load(favItem.get(i).getPoster())
                .into(holder.imgPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(EXTRA_ID, favItem.get(holder.getAdapterPosition()).getId());
                if (favItem.get(holder.getAdapterPosition()).getIsMovie() == 1) {
                    intent.putExtra(EXTRA_TYPE, true);
                } else {
                    intent.putExtra(EXTRA_TYPE, false);
                }

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favItem == null ? 0 : favItem.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        RatingBar rating;
        ImageView imgPoster;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            rating = itemView.findViewById(R.id.rating);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
