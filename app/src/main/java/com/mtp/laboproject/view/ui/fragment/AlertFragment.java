package com.mtp.laboproject.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mtp.laboproject.R;
import com.mtp.laboproject.data.model.alert.AlertsDetailsResponse;
import com.mtp.laboproject.data.model.alert.AlertsResponse;
import com.mtp.laboproject.view.adapter.AlertsAdapter;
import com.mtp.laboproject.view.listener.AlertsClickListener;
import com.mtp.laboproject.view.viewmodel.AlertViewModel;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertFragment extends BaseFragment implements AlertsClickListener {
    private AlertViewModel mViewModel;
    private AlertsAdapter alertsAdapter;

    @BindView(R.id.recycleview_alert)
    public RecyclerView recycleview_alert;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_alert, container, false);
        //ButterKnife.inject(this,v);
        recycleview_alert=v.findViewById(R.id.recycleview_alert);
        ButterKnife.bind(this,v);

        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupAlertList();

    }

    private void setupAlertList() {
        mViewModel = ViewModelProviders.of(this).get(AlertViewModel.class);
        mViewModel.getAlerts();
        mViewModel.alertsLiveData.observe(getViewLifecycleOwner(), (Observer<AlertsResponse>) value -> {
            if (value != null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recycleview_alert.setLayoutManager(layoutManager);
                alertsAdapter= new AlertsAdapter(new ArrayList(value.getData()),this);
                recycleview_alert.setAdapter(alertsAdapter);
            }
        });
    }

    @Override
    public void onRecyclerViewItemClick(@NotNull View view, @NotNull AlertsDetailsResponse labo) {

    }
}
