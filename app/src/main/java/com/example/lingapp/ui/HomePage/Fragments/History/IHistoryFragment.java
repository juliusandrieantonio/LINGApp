package com.example.lingapp.ui.HomePage.Fragments.History;

import com.example.lingapp.utils.HistoryFragmentModel;

import java.util.ArrayList;

public interface IHistoryFragment {
    void onGetBMIHistory(ArrayList<HistoryFragmentModel> models);

    void onGetIntakeStatus(ArrayList<HistoryFragmentModel> models, ArrayList<String> labels);
}
