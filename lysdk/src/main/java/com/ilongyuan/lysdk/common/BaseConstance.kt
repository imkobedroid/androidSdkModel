package com.ilongyuan.lysdk.common

/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */
class BaseConstance {

    companion object {
        /**
         * okHttp请求超时时间
         */
        const val TIME_OUT: Long = 30
        /**
         * 主机地址
         */
        const val BASE_URL: String = "http://openmusic-api.hifiveai.com"
//        const val BASE_URL: String = "http://172.16.212.40:9093"
        /**
         * 后台返回值code 200代表成功
         */
//        const val SUCCEED=200
        const val SUCCEED = 10200


    }

}