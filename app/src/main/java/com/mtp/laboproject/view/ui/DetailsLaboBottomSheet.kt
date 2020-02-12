package com.mtp.laboproject.view.ui

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.labs.LabsObjectResponse
import com.mtp.laboproject.databinding.BottomsheetDetailsLaboBinding
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*


class DetailsLaboBottomSheet(private var laboratory: LabsObjectResponse) : BottomSheetDialogFragment() {

    private lateinit var dataBinding: BottomsheetDetailsLaboBinding
    val client by lazy {
        val clientId = MqttClient.generateClientId()
        MqttAndroidClient(context, "mqtt://151.80.103.151",
            clientId)
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        dataBinding =  DataBindingUtil.inflate(inflater,R.layout.bottomsheet_details_labo, container,false)
        dataBinding.laboratoryResponseDetailsData=laboratory
        connect(arrayOf(TOPIC, HUMIDITY_TOPIC), ::setData)
        return dataBinding.root
    }

    private fun setData(topic: String, msg: MqttMessage) {
        when (topic) {
            TOPIC -> {
                Log.d(TAG, "Connected to: \" ${String(msg.payload)} ° c\"")
            }
            else -> {
                Log.d(TAG, "Connected to: \" ${String(msg.payload)} ° c\"")
            }
        }
    }



    fun connect(topics: Array<String>? = null,
                messageCallBack: ((topic: String, message: MqttMessage) -> Unit)? = null) {
        try {
            client.connect()
            client.setCallback(object : MqttCallbackExtended {
                override fun connectComplete(reconnect: Boolean, serverURI: String) {
                    topics?.forEach {
                        subscribeTopic(it)
                    }
                    Log.d(TAG, "Connected to: $serverURI")
                }

                override fun connectionLost(cause: Throwable) {
                    Log.d(TAG, "The Connection was lost.")
                }

                @Throws(Exception::class)
                override fun messageArrived(topic: String, message: MqttMessage) {
                    Log.d(TAG, "Incoming message from $topic: " + message.toString())
                    messageCallBack?.invoke(topic, message)
                }

                override fun deliveryComplete(token: IMqttDeliveryToken) {

                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }


    fun subscribeTopic(topic: String, qos: Int = 0) {
        client.subscribe(topic, qos).actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.d(TAG, "Subscribed to $topic")
            }

            override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                Log.d(TAG, "Failed to subscribe to $topic")
                exception.printStackTrace()
            }
        }
    }

    companion object {
        fun newInstance(labo :LabsObjectResponse): DetailsLaboBottomSheet {
            return DetailsLaboBottomSheet(labo)
        }
        const val TAG = "MQTT READER"
        const val TOPIC = "Fullrama/ReliagatePLV/DIAG"
        const val HUMIDITY_TOPIC = "t0th/humidity"
        const val BROKER: String = "tcp://broker.hivemq.com"
    }
}