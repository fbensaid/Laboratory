package com.mtp.laboproject.global


class Constants {


    companion object {

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
    }

    object Requests {
        val REQUEST_USER_CONNECTED = 0x99
        val DRAW_REQUEST = 0X11
        val PLAY_SERVICES_RESOLUTION_REQUEST = 121
        val REQUEST_CHECK_GOOGLE_SETTINGS = 321
        val REQUEST_CAMERA_SETTINGS = 3251
        val REQUEST_CALL_SETTINGS = 31
        val MY_PERMISSIONS_REQUEST_LOCATION = 99

    }

    object Parameters {



        val PUSH_DATA_type = "push_data_type"
        val PUSH_DATA_MESSAGE = "push_data_message"
        val USERNAME = "username"
        val PASSWORD = "password"
    }

    object EndPoints {
        const val LABORATORY_ENDPOINT  = "lab/list"
        const val FORGOTTEN_PASSWORD_ENDPOINT  = "forgot_password"
        const val ALERTS_ENDPOINT  = "alerts"
    }



    object Variants {
        const val CLICK_OPACITY = 0.75f

    }

}
