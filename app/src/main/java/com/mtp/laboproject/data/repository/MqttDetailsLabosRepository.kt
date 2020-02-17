package com.mtp.laboproject.data.repository

import android.util.Log
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.global.Constants.Companion.MQTT_URL
import com.mtp.laboproject.view.listener.DetailsMqttCalback
import com.mtp.laboproject.view.ui.DetailsLaboBottomSheet
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*


class MqttDetailsLabosRepository : BaseRepository() {

    val client by lazy {
        val clientId = MqttClient.generateClientId()
        MqttAndroidClient(LaboApplication.instance, MQTT_URL,
            clientId)
    }

    fun connect(topics: Array<String>? = null, messageCallBack: DetailsMqttCalback) {
        try {
            client.connect()
            client.setCallback(object : MqttCallbackExtended {
                override fun connectComplete(reconnect: Boolean, serverURI: String) {
                    topics?.forEach {
                        subscribeTopic(it)
                    }
                }
                override fun connectionLost(cause: Throwable) {
                    messageCallBack.onError(cause.message)
                }

                @Throws(Exception::class)
                override fun messageArrived(topic: String, message: MqttMessage) {
                    messageCallBack.onSuccess(topic, message)
                }

                override fun deliveryComplete(token: IMqttDeliveryToken) {}
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribeTopic(topic: String, qos: Int = 0) {
        client.subscribe(topic, qos).actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.d(DetailsLaboBottomSheet.TAG, "Subscribed to $topic")
            }
            override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                Log.d(DetailsLaboBottomSheet.TAG, "Failed to subscribe to $topic")
                exception.printStackTrace()
            }
        }
    }




}