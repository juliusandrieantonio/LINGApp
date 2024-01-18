package com.example.lingapp.ui.HomePage.Fragments.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingapp.R;
import com.example.lingapp.utils.HomeFragmentModel;

import java.util.ArrayList;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HomeFragmentModel> models;

    public HomeFragmentAdapter(Context context, ArrayList<HomeFragmentModel> models) {
        this.context = context;
        this.models = models;
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

        holder.description.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView description;
        private final ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            imageView= itemView.findViewById(R.id.image);
        }
    }
}
