package com.mtp.laboproject.data.repository;

import com.mtp.laboproject.data.model.alert.AlertsResponse;
import com.mtp.laboproject.data.remoteApi.ApiInterface;

import io.reactivex.Single;

public class AlertsRepository {

    private ApiInterface api;

    public AlertsRepository(ApiInterface api) {
        this.api = api;
    }
    public Single<AlertsResponse> getAlertsRx(){
         return api.getAlerts();
    }


}
