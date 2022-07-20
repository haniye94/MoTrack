package com.hani.motrack.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hani.motrack.Activity.MovieDetailActivity;
import com.hani.motrack.Model.MovieModel;
import com.hani.motrack.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<MovieModel> movieModelArrayList;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;

    public MovieAdapter(Context context) {

        this.context = context;
        this.movieModelArrayList = new ArrayList<>();
    }

    public List<MovieModel> getMovieModelArrayList() {
        return movieModelArrayList;
    }

    public void setMovieModelArrayList(ArrayList<MovieModel> movieModelArrayList) {
        this.movieModelArrayList = movieModelArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_recycler, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        switch (getItemViewType(position)) {
            case ITEM:
                MovieVH viewholder = (MovieVH) holder;
                viewholder.onBind();
                break;
            case LOADING:
//                LoadingVH loadingViewHolder = (LoadingVH) holder;
//                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movieModelArrayList == null ? 0 : movieModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieModelArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(MovieModel mv) {
        movieModelArrayList.add(mv);
        notifyItemInserted(movieModelArrayList.size() - 1);
    }

    public void addAll(List<MovieModel> mvList) {
        for (MovieModel mc : mvList) {
            add(mc);
        }
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new MovieModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieModelArrayList.size() - 1;
        MovieModel item = getItem(position);

        if (item != null) {
            movieModelArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public MovieModel getItem(int position) {
        return movieModelArrayList.get(position);
    }

    public void remove(MovieModel city) {
        int position = movieModelArrayList.indexOf(city);
        if (position > -1) {
            movieModelArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

   /*
   View Holders
   _________________________________________________________________________________________________
    */

    public class MovieVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView txtTitle;
        private final TextView txtYear;
        private final ImageView imgPoster;
        private final TextView txtType;

        public MovieVH(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtYear = itemView.findViewById(R.id.txt_year);
            txtType = itemView.findViewById(R.id.txt_type);

            itemView.setOnClickListener(this);
        }

        public void onBind() {

            Picasso.with(context).load(movieModelArrayList.get(getAdapterPosition()).getPoster()).into(imgPoster);
            txtTitle.setText(movieModelArrayList.get(getAdapterPosition()).getTitle());
            txtYear.setText(movieModelArrayList.get(getAdapterPosition()).getYear());
            txtType.setText(movieModelArrayList.get(getAdapterPosition()).getType());
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(context, MovieDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("i", String.valueOf(movieModelArrayList.get(getAdapterPosition()).getImdbID()));
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
            ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);

        }
    }

}
