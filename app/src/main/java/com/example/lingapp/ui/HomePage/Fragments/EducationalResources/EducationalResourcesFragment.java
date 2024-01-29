package com.example.lingapp.ui.HomePage.Fragments.EducationalResources;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lingapp.R;
import com.example.lingapp.utils.EducationalResourcesModel;

import java.util.ArrayList;

public class EducationalResourcesFragment extends Fragment {
    private final TextView title;
    private SearchView searchView;
    private EducationalResourcesAdapter adapter;

    private Toolbar toolbar;

    public EducationalResourcesFragment(Toolbar toolbar, TextView title) {
        this.toolbar = toolbar;
        this.title = title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_educational_resourcement, container, false);


        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.search);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ArrayList<EducationalResourcesModel> models = new ArrayList<>();

        // add new resources here
        EducationalResourcesModel model = new EducationalResourcesModel("Tomato", R.drawable.tomato_sample, getString(R.string.very_long_paragraph));
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);
        models.add(model);

        adapter = new EducationalResourcesAdapter(models, getContext(), getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setOnSearchClickListener(v -> title.setVisibility(View.GONE));
            searchView.setOnCloseListener(() -> {
                title.setVisibility(View.VISIBLE);
                return false;
            });
            searchView.setQueryHint("Search here...");
            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Toast like print
                    if(!searchView.isIconified()) {
                        searchView.setIconified(true);
                    }
                    if (searchItem != null) {
                        searchItem.collapseActionView();
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    ArrayList<EducationalResourcesModel> tempModel = new ArrayList<>();
                    for (EducationalResourcesModel model: models) {
                        if (model.getName().toLowerCase().contains(s.toLowerCase())) {
                            tempModel.add(model);
                        }
                    }
                    recyclerView.removeAllViews();
                    adapter = new EducationalResourcesAdapter(tempModel, getContext(), getActivity());
                    recyclerView.setAdapter(adapter);
                    return false;
                }
            });
        }
        return view;
    }
}