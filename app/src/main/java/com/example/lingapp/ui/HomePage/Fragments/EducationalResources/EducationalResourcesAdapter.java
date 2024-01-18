package com.example.lingapp.ui.HomePage.Fragments.EducationalResources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingapp.R;
import com.example.lingapp.utils.EducationalResourcesModel;

import java.util.ArrayList;

public class EducationalResourcesAdapter extends RecyclerView.Adapter<EducationalResourcesAdapter.ViewHolder>{
    private final ArrayList<EducationalResourcesModel> models;
    private final Context context;

    public EducationalResourcesAdapter(ArrayList<EducationalResourcesModel> models, Context context) {
        this.models = models;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }
    }
}
