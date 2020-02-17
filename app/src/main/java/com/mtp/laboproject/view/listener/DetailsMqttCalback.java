package com.mtp.laboproject.view.listener;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface DetailsMqttCalback {

     void onSuccess( String topic,  MqttMessage message);
     void onError(String errorMessage);
}
