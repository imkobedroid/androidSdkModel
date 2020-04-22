package com.ilongyuan.lysdk.net

import android.util.Log
import com.ilongyuan.lysdk.common.BaseConstance
import com.ilongyuan.lysdk.manager.LyManager.Companion.APP_ID
import com.ilongyuan.lysdk.manager.LyManager.Companion.DEVICE_ID
import com.ilongyuan.lysdk.manager.LyManager.Companion.NONCE
import com.ilongyuan.lysdk.manager.LyManager.Companion.PG
import com.ilongyuan.lysdk.manager.LyManager.Companion.PLATFFORM
import com.ilongyuan.lysdk.manager.LyManager.Companion.SECRET
import com.ilongyuan.lysdk.net.Encryption.Companion.getNum
import com.ilongyuan.lysdk.net.Encryption.Companion.hmacSHA256Andeccry64
import com.ilongyuan.lysdk.utils.NetWorkUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.concurrent.TimeUnit


/**
 * @author Dsh  imkobedroid@gmail.com
 * @date 2019-07-09
 */
class RetrofitFactory private constructor() {

    private var TIMESTAMP: String? = null

    companion object {
        private val instance: RetrofitFactory by lazy { RetrofitFactory() }

        /**
         * 提供api接口
         */
        fun Api() = instance.createApi(Api::class.java)
    }

    private val retrofit: Retrofit
    private val valueList: ArrayList<String> = arrayListOf()

    private val encryptionInterceptor: Interceptor

    init {


        /**
         * 加密拦截器
         */
        encryptionInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                TIMESTAMP = System.currentTimeMillis().toString()
                NONCE = getNum().toString()
                val request = chain.request().newBuilder()
                    .addHeader("appId", APP_ID ?: "")
                    .addHeader("deviceId", DEVICE_ID ?: "")
                    .addHeader("platform", PLATFFORM)
                    .addHeader("pg", PG ?: "")
                    .addHeader("nonce", NONCE ?: "")
                    .addHeader("timestamp", TIMESTAMP!!)
                    .addHeader("sign", getSign(chain))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("charset", "utf-8")
                    .build()
                Log.v("请求头", request.headers.toString())
                return chain.proceed(request)
            }

        }


        retrofit = Retrofit
            .Builder()
            .baseUrl(BaseConstance.BASE_URL)
            .client(initClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun initClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(encryptionInterceptor)
            .addInterceptor(initLogInterceptor())
            .retryOnConnectionFailure(true)
            .connectTimeout(BaseConstance.TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(BaseConstance.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(BaseConstance.TIME_OUT, TimeUnit.SECONDS)
            .build()
    }


    /**
     * 打包次方法要去掉
     */
    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }


    private fun <T> createApi(api: Class<T>): T {
        return retrofit.create(api)
    }


    fun getSign(chain: Interceptor.Chain): String {
        valueList.clear()
        val request = chain.request()
        /**
         * 拼接url参数
         */
        val GET_URL = request.url.toString().trim()
        Log.v("请求地址", GET_URL)
        val index = GET_URL.indexOf("?")
        if (index != -1) {
            val parameters = GET_URL.substring(index, GET_URL.length)
            val parts =
                parameters.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            parts.forEach {
                val p = URLDecoder.decode(it.split("=")[1], "UTF-8")
                valueList.add(p)
            }
        }
        valueList.add(APP_ID ?: "".trim())
        valueList.add(DEVICE_ID ?: "".trim())
        valueList.add(PLATFFORM)
        valueList.add(PG ?: "".trim())
        valueList.add(NONCE ?: "".trim())
        TIMESTAMP?.trim()?.let { valueList.add(it) }
        valueList.sort()

        val builder = StringBuilder()
        if (valueList.size > 0) {
            for (v in 0 until valueList.size) {
                val value = valueList[v].trim()
                if (value.isNotEmpty()) {
                    builder.append(valueList[v].trim())
                    if (v != valueList.size - 1) {
                        builder.append("&")
                    } else {
                        builder.append(SECRET ?: "".trim())
                    }
                }
            }
            Log.v("签名", builder.toString())
            val values256 = hmacSHA256Andeccry64(SECRET ?: "", builder.toString())
            Log.v("签名", values256)
            val SIGN = URLEncoder.encode(NetWorkUtils.replaceBlank(values256.trim()), "UTF-8")
            Log.v("签名", SIGN)
            return SIGN
        }
        return ""
    }
}