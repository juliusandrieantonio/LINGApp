package com.example.lingapp.ui.HomePage.Fragments.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingapp.R;
import com.example.lingapp.ui.DetailedLearningResources.DetailedLearningResourcesActivity;
import com.example.lingapp.utils.HomeFragmentModel;

import java.util.ArrayList;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HomeFragmentModel> models;
    private Activity activity;

    public HomeFragmentAdapter(Activity activity, Context context, ArrayList<HomeFragmentModel> models) {
        this.context = context;
        this.models = models;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HomeFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentAdapter.ViewHolder holder, int position) {
        HomeFragmentModel model = models.get(position);
        holder.imageView.setImageDrawable(AppCompatResources.getDrawable(context, model.getDrawable()));
//        holder.description.setText(model.getDescription());
        holder.title.setText(model.getTitle());
        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedLearningResourcesActivity.class);
            intent.putExtra("name", model.getTitle());
            intent.putExtra("drawable", model.getDrawable());
            intent.putExtra("descri", model.getContent());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private final TextView description;
        private final TextView title;
        private final ImageView imageView;
        private final CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            description = itemView.findViewById(R.id.description);
            imageView= itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            card = itemView.findViewById(R.id.card);
        }
    }
}
