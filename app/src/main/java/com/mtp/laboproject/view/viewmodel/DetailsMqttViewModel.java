package com.mtp.laboproject.view.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.mtp.laboproject.data.model.detailsLabsMqtt.DetailsResponse;
import com.mtp.laboproject.data.repository.MqttDetailsLabosRepository;
import com.mtp.laboproject.view.listener.DetailsMqttCalback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import static com.mtp.laboproject.global.Constants.Variants.TOPIC_DETAILS;
import static com.mtp.laboproject.global.Constants.Variants.TOPIC_LIST;

public class DetailsMqttViewModel extends ViewModel {


    public MutableLiveData detailsLiveData= new MutableLiveData();
    MqttDetailsLabosRepository mqttHelper=new MqttDetailsLabosRepository();
    String[] TOPIC = { TOPIC_DETAILS, TOPIC_LIST };

     public void connectToMQQtt(String reference){
         //take the reference of 
        mqttHelper.connect(TOPIC, new DetailsMqttCalback() {
            @Override
            public void onSuccess(String topic, MqttMessage message)  {
                    setData(topic,message);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("MqttViewModel","onError==>"+errorMessage);
            }
        });
    }

    private void setData(String topic, MqttMessage msg ) {
            switch (topic){
                case (TOPIC_DETAILS):
                    DetailsResponse object = new Gson().fromJson(new String(msg.getPayload()), DetailsResponse.class);
                    if(object!=null){
                        detailsLiveData.postValue(object);
                    }
                    break;
                case (TOPIC_LIST):
                    Log.d("mqttViewModel","TOPIC_LIST"+msg);
                    break;
            }

    }

}