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

        const val LABORATORY: String = "movies"
        const val LABORATORY_ENDPOINT: String = "lab/list"
        const val FORGOTTEN_PASSWORD_ENDPOINT: String = "forgot_password"
        val REFERENCE_PARAM = "reference"
        val BARCODE_PARAM = "barCode"
        val DRY_PARAM = "dry"
        val FRESH_PARAM = "fresh"
        val FRESHFROZEN_PARAM = "freshFrozen"
        val WITHOUTBAG_PARAM = "withoutBag"
        val ORDER_QUANTITY_PARAM = "orderQuantity"
        val QUANTITY_PARAM = "quantity"
        val ORDER_DETAIL_PARAM = "orderDetails"
        val TEXT_PLAIN_APPLICATION = "text/plain"
        val JSON_APPLICATION = "Content-Type: application/json"
        val URL_ENCODED_APPLICATION = "application/x-www-form-urlencoded"
        val MAX_VALUES_ADAPTER = 10000
        val AVAILABLE_WINNINGS_PARAM = "available_winnings"
        val LAST_WINNERS_PARAM = "last-winners"
        val FIRST_NAME = "first_name"
        val LAST_NAME = "last_name"
        val EMAIL = "email"
        val CARS = "cars"

        val MULTIPART = "multipart/form-data"
        val WEB_FIELD_PHOTO = "profile_image"

        val DELETE_PHOTO = "delete_image"
        val DETAILS_PARAM = "details"

        val TITLE_PARAM = "title"
        val SOURCE_PARAM = "source"
        val ID_PARAM = "looId"

        val LIVE_TAG = 0
        val REPLAY_TAG = 1

        val LOTTERY_RESPONSE = "lottery_response"

        val HEADER_BING_RATION = 0.085f

        // profile and user
        val MIN_PASSWORD_SIZE = 6
        val MAX_PASSWORD_SIZE = 25
        val MIN_MOBILE_SIZE = 8
        val MAX_CODE_SIZE = 40
        val MAX_EMAIL_SIZE = 50
        val WHITE_SPACE = "^\\s*$"
        val RULES_SELECTED = "1"
        val RULES_NOT_SELECTED = "0"

        // select image dialog
        val CAMERA_SELECTED = 0
        val GALLERY_SELECTED = 1
        val DELETE_SELECTED = 2
        val CANCEL_SELECTED = 3
        val CGU_FROM_SINGUP = "sing_up"
        val CGU_FROM_MENU = "menu"

        val DEFAULT_CREDIT_VALUE = 1
        val MAX_GRID_COUNT = 5
        val MAX_GRID_NUMBERS_COUNT = 25
        val MAX_NUMBERS = 45
        val DELETE_IMAGE_VALUE = "deleted"
        val SUCCESS = "success"
        val MAX_PROGRESS = 61
        val BARCODE_IMAGE_WIDTH = 600
        val BARCODE_IMAGE_HEIGHT = 400
        val MAX_LINE_COUNT = 5

        val DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss"
        val CHOSEN_GRID = "chosen_grid"
        val WINNER_INDEX = 1
        val END_POINT = "end_point"
        val SELECTED_GIFT = "selected_gift"
        val DRAW_STATE_TAG = "draw_state_tag"

        val PUSH_DATA_type = "push_data_type"
        val PUSH_DATA_MESSAGE = "push_data_message"
        val USERNAME = "username"
        val PASSWORD = "password"
    }

    object EndPoints {
        const val LABORATORY = "movies"
        const val LABORATORY_ENDPOINT  = "lab/list"
        const val FORGOTTEN_PASSWORD_ENDPOINT  = "forgot_password"
        const val HOME = "api/home"
        const val MY_GIFTS = "api/loot"
        const val USE_VOUCHER = "api/loot"
        const val GET_PROMO = "api/code-promo"
        const val USE_PROMO = "api/code-promo"
        const val MY_TICKETS = "api/ticket"
        const val SIGNIN = "/api/auth/login"
        const val GET_PRODUCTS = "/api/order/products"
        const val GET_ORDER_DETAILS = "/api/order/productsByBarCode"
        const val GET_ORDERS = "/api/orders"
        const val SIGNIN_TEST = "api/auth/login"
        const val PROFILE = "api/my-profile"
        const val FORGET_PASSWORD = "api/forgot-password"
        const val SIGNUP = "api/register"
        const val UPDATE_PROFILE = "api/my-profile"
        const val RESET_PASSWORD = "api/update-password"
        const val SIGNUP_WITH_FACEBOOK = "api/register"
        const val POST_GRID = "api/grid"
        const val LOTTERY = "api/lottery"
        const val LOTTERY_LOOT = "api/lottery/loot"
        const val WEATHER = "/weather"
        const val UPDATE_ORDER = "/api/order/update"
    }

    object Durations {
        val DURATION_ANIMATION: Long = 550
        val BALL_SCALE_ANIMATION: Long = 600
        val MILLISECONDS_UNIT = 1000
        val SECONDS_MILLISECONDS_FACTOR: Long = 1000
        val DIALOG_DURATION_ANIMATION: Long = 300
        val SPLASH_LOGO_DELAY: Long = 1000
        val INCREMENT_WINNER_COUNT_DURATION: Long = 50
        val WHEEL_DELAY: Long = 1000
        val ONE_HOUR_DURATION_SECONDS = 3600 * 1000
        val FIVE_MINUTES_DURATION_SECONDS = 200
        val API_CALL_LAP: Long = 60000
        val EARNING_VIEW_PAGER_DURATION = 4000
        val COUNT_DOWN_INTERVAL: Long = 1000

    }


    object Variants {
        const val CLICK_OPACITY = 0.75f

    }

}
