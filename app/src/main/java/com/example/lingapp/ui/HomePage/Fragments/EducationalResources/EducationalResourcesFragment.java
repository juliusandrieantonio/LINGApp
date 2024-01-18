package com.example.lingapp.ui.HomePage.Fragments.EducationalResources;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lingapp.R;
import com.example.lingapp.utils.EducationalResourcesModel;

import java.util.ArrayList;

public class EducationalResourcesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_educational_resourcement, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ArrayList<EducationalResourcesModel> models = new ArrayList<>();
        EducationalResourcesModel model = new EducationalResourcesModel("Tomato", "");
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);

        EducationalResourcesAdapter adapter = new EducationalResourcesAdapter(models, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;
    }
}