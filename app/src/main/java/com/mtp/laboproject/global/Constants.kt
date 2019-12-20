package com.mtp.laboproject.global


class Constants {


    companion object {

        const val BASE_URL: String = "http://172.16.1.12:3000/api/"

    }

    inner class EndPointss {

        val LABORATORY: String = "movies"
        fun laboratory() = LABORATORY
    }

    object EndPoints {
        const  val LABORATORY: String = "movies"
        const  val LABORATORY_ENDPOINT: String = "lab/list"
        const  val FORGOTTEN_PASSWORD_ENDPOINT: String = "forgot_password"

    }

    object Variants
    {
        const val CLICK_OPACITY = 0.75f

    }

}
