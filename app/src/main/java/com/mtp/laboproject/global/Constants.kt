package com.mtp.laboproject.global


class Constants {


    companion object {

        const val CURRENT_POSITION_ZOOM = 15
        const val BASE_URL: String = "http://172.16.1.12:3000/api/"
    }

    inner class EndPointss {
        val LABORATORY: String = "movies"
        fun laboratory() = LABORATORY
    }

    object Urls {
        val CHART_HTML = "file:///android_asset/html/chart.html"
        val CGU_HTML = "file:///android_asset/html/cgu.html"
        val REGULATIONS_HTML = "file:///android_asset/html/reglement.html"
        val APP_URL = "http://www.bingosino.fr/"
        const val GRAPHANA_TEMP="http://192.168.2.40:8989/d-solo/IdULMpyWk/iffcharts?orgId=6&from=1580283032498&to=1580304632498&theme=light&panelId=2"
        const  val GRAPHANA_VOC="http://192.168.2.40:8989/d-solo/IdULMpyWk/iffcharts?orgId=6&from=1580283081858&to=1580304681858&theme=light&panelId=3"
        const  val GRAPHANA_CO2="http://192.168.2.40:8989/d-solo/IdULMpyWk/iffcharts?orgId=6&from=1580283121113&to=1580304721113&theme=light&panelId=5"
        const val GRAPHANA_HUM= "http://192.168.2.40:8989/d-solo/IdULMpyWk/iffcharts?orgId=6&from=1580282366460&to=1580303966460&theme=light&panelId=4"
    }

    object Requests {
        val REQUEST_USER_CONNECTED = 0x99
        val DRAW_REQUEST = 0X11
        val PLAY_SERVICES_RESOLUTION_REQUEST = 121
        val REQUEST_CHECK_GOOGLE_SETTINGS = 321
        val REQUEST_CAMERA_SETTINGS = 3251
        val REQUEST_CALL_SETTINGS = 31
        val MY_PERMISSIONS_REQUEST_LOCATION = 0


    }

    object Parameters {
        val PUSH_DATA_type = "push_data_type"
        val PUSH_DATA_MESSAGE = "push_data_message"

    }

    object EndPoints {
        const val LABORATORY_ENDPOINT  = "labs/all/last_data"
        const val FORGOTTEN_PASSWORD_ENDPOINT  = "forgot_password"
        const val ALERTS_ENDPOINT  = "notification/all"
        const val LOGIN_ENDPOINT  = "users/login"
        const val STATISTICS_ENDPOINT  = "sensor/all_statistics"
    }



    object Variants {
        const val CLICK_OPACITY = 0.75f
        // SPLASHSCREEN
        const val SPLASHTIME: Long = 2000
        const val RESULT_LOAD_IMG: Int = 201
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val FIREBASE_TOKEN = "firebase_token"



    }

}
