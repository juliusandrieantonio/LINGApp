package com.example.lingapp.ui.HomePage.Fragments.HomeFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lingapp.R;
import com.example.lingapp.utils.HomeFragmentModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private Context context;
    private Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        if (getContext() != null) {
            context = getContext();
        }
        if (getActivity() != null) {
            activity = getActivity();
        }

        ArrayList<HomeFragmentModel> models = new ArrayList<>();
        String template = activity.getString(R.string.short_paragraph);
        HomeFragmentModel model = new HomeFragmentModel("", template);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);

        HomeFragmentAdapter adapter = new HomeFragmentAdapter(context, models);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }
}