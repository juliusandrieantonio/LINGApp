package com.example.lingapp.ui.HomePage.Fragments.EducationalResources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingapp.R;
import com.example.lingapp.ui.DetailedLearningResources.DetailedLearningResourcesActivity;
import com.example.lingapp.utils.EducationalResourcesModel;

import java.util.ArrayList;

public class EducationalResourcesAdapter extends RecyclerView.Adapter<EducationalResourcesAdapter.ViewHolder>{
    private final ArrayList<EducationalResourcesModel> models;
    private final Context context;
    private final Activity activity;


    public EducationalResourcesAdapter(ArrayList<EducationalResourcesModel> models, Context context, Activity activity) {
        this.models = models;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public EducationalResourcesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.educational_resources_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationalResourcesAdapter.ViewHolder holder, int position) {
        EducationalResourcesModel model = models.get(position);
        holder.title.setText(model.getName());
        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedLearningResourcesActivity.class);
            intent.putExtra("name", model.getName());
            intent.putExtra("drawable", model.getDrawable());
            intent.putExtra("descri", model.getDescription());
            activity.startActivity(intent);
        });
        holder.image.setImageDrawable(AppCompatResources.getDrawable(context, model.getDrawable()));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView image;
        private final LinearLayout card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            card = itemView.findViewById(R.id.card);
        }
    }
}
