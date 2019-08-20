package com.davidargote.appreportsena.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.davidargote.appreportsena.R;
import com.davidargote.appreportsena.model.AdapterList;
import com.davidargote.appreportsena.model.ManagerHelper;
import com.davidargote.appreportsena.model.Report;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private ListView lvList;

    public ListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvList = view.findViewById(R.id.lvLista);

        ManagerHelper managerHelper = new ManagerHelper(getContext());
        ArrayList<Report> list = new ArrayList<>(managerHelper.listReports());
        AdapterList adapterList = new AdapterList(getContext(), list);
        lvList.setAdapter(adapterList);

    }
}
