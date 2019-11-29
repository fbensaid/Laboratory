package com.mtp.laboproject.data.global


class Constants {


    companion object {

        const val BASE_URL: String = "https://api.simplifiedcoding.in/course-apis/recyclerview/"

    }

    inner class EndPointss {

        val LABORATORY: String = "movies"
        fun laboratory() = LABORATORY
    }

    object EndPoints {
        const  val LABORATORY: String = "movies"

    }

}
