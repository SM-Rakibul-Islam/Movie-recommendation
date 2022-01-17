package com.example.ToDoList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.model.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<Model, myadapter.myviewholder>{

    public myadapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Model Model) {
        holder.Task.setText(Model.gettask());
        holder.Time.setText(Model.getTime());
        holder.Date.setText(Model.getDate());
        holder.Location.setText(Model.getlocation());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movieRecycler, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView Task, Time, Date, Location;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            Date = (TextView)itemView.findViewById(R.id.Date);
            Task = (TextView)itemView.findViewById(R.id.Task);
            Time = (TextView)itemView.findViewById(R.id.Time);
            Location = (TextView)itemView.findViewById(R.id.Location);

        }
    }
}