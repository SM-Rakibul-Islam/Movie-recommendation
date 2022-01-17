package com.example.ToDoList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

class customAdapterMovie extends FirebaseRecyclerAdapter<todoModel, customAdapterMovie.myviewholder>{

    private ArrayList<todoModel> modelArrayList = new ArrayList<>();

    public customAdapterMovie(@NonNull FirebaseRecyclerOptions<todoModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull todoModel todoModel) {
        holder.movieName.setText(todoModel.getMovieName());
        holder.actorName.setText(todoModel.getActorName());
        Glide.with(holder.moviePoster.getContext()).load(todoModel.getmoviePoster()).into(holder.moviePoster);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movieRecycler, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView movieName, actorName;
        ImageView moviePoster;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            movieName = (TextView)itemView.findViewById(R.id.movieName);
            actorName = (TextView)itemView.findViewById(R.id.Actor);
            moviePoster = (ImageView)itemView.findViewById(R.id.moviePoster);
        }
    }
}