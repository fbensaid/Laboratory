package com.mtp.laboproject.view.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mtp.laboproject.data.model.alert.AlertsResponse;
import com.mtp.laboproject.data.remoteApi.Apifactory;
import com.mtp.laboproject.data.repository.AlertsRepository;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class AlertViewModel extends ViewModel {

    private AlertsRepository alertRepository2 ;
    public MutableLiveData alertsLiveData= new MutableLiveData();

    public AlertViewModel() {
        this.alertRepository2 = new AlertsRepository(Apifactory.INSTANCE.getApiRx());
    }

     public void getAlerts(){
         alertRepository2.getAlertsRx().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                 .subscribe(new SingleObserver<AlertsResponse>() {
                     @Override
                     public void onSubscribe(Disposable d) {
                         Log.d("rx","onSubscribe-->"+d.toString());
                     }

                     @Override
                     public void onSuccess(AlertsResponse value) {
                         Log.d("rx","onSuccess-->"+value.toString());
                         alertsLiveData.postValue(value);
                     }

                     @Override
                     public void onError(Throwable e) {
                         Log.d("rx","onError-->"+e.toString());
                     }
                 });
     }

}
