package com.example.lingapp.ui.HomePage.Fragments.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingapp.R;
import com.example.lingapp.StaticValues.StaticClass;
import com.example.lingapp.utils.HistoryFragmentModel;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryFragmentAdapter extends RecyclerView.Adapter<HistoryFragmentAdapter.ViewHolder> {
    private final ArrayList<HistoryFragmentModel> models;
    private final Context context;
    private final StaticClass staticClass;

    public HistoryFragmentAdapter(ArrayList<HistoryFragmentModel> models, Context context) {
        this.models = models;
        this.context = context;
        staticClass = new StaticClass(context);
    }

    @NonNull
    @Override
    public HistoryFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryFragmentAdapter.ViewHolder holder, int position) {
        HistoryFragmentModel model = models.get(position);
        holder.height.setText(String.valueOf(model.getHeight()));
        holder.weight.setText(String.valueOf(model.getWeight()));
        holder.status.setText(String.valueOf(model.getStatus()));
        if (model.getStatus().equals("Underweight")) {
            holder.actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(model.getGender())).get(0));
        }
        if (model.getStatus().equals("Normal")) {
            holder.actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(model.getGender())).get(1));
        }
        if (model.getStatus().equals("Overweight")) {
            holder.actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(model.getGender())).get(2));
        }
        if (model.getStatus().equals("Obese")) {
            holder.actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(model.getGender())).get(3));
        }
        if (model.getStatus().equals("Extremely Obese")) {
            holder.actionImage.setImageDrawable(Objects.requireNonNull(staticClass.getImages().get(model.getGender())).get(4));
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView weight;
        private final TextView height;
        private final TextView status;
        private final ImageView actionImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weight = itemView.findViewById(R.id.weight);
            height = itemView.findViewById(R.id.height);
            status = itemView.findViewById(R.id.status);
            actionImage = itemView.findViewById(R.id.actionImage);
        }
    }
}
