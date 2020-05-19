package com.mackwu.languagegenerator.http

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * ===================================================
 * Created by MackWu on 2019/11/20 15:18
 * <a href="mailto:wumengjiao828@163.com">Contact me</a>
 * <a href="https://github.com/mackwu828">Follow me</a>
 * ===================================================
 */
object OkHttpManager {

    /**
     * 初始化
     * connectTimeout: 连接超时时间
     * readTimeout: 读取超时时间
     * writeTimeout: 写入超时时间
     * retryOnConnectionFailure: 超时重试
     * hostnameVerifier: 信任所有证书
     * cookieJar: cookie持久化
     * addInterceptor: 拦截器
     */
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .hostnameVerifier { _, _ -> true }
            .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }) // httpLoggingInterceptor
            .build()

    /**
     * enqueue
     */
    private fun Call.enqueue(onResponse: ((result: String) -> Unit)? = null, onFailure: ((e: IOException) -> Unit)? = null) {
        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // Returns true if the code is in [200..300), which means the request was successfully received, understood, and accepted.
                if (response.isSuccessful) {
                    val body = response.body()
                    if (null == body) {
                        onFailure?.invoke(IOException("body is null"))
                        return
                    }
                    onResponse?.invoke(body.string())
                    return
                }
                onFailure?.invoke(IOException("${response.code()} ${response.message()}"))
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                onFailure?.invoke(e)
            }
        })
    }

    /**
     * execute
     */
    private fun Call.execute(onResponse: ((result: String) -> Unit)? = null, onFailure: ((e: IOException) -> Unit)? = null) {
        try {
            val response = execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (null == body) {
                    onFailure?.invoke(IOException("body is null"))
                    return
                }
                onResponse?.invoke(body.string())
                return
            }
            onFailure?.invoke(IOException("${response.code()} ${response.message()}"))
        } catch (e: IOException) {
            e.printStackTrace()
            onFailure?.invoke(e)
        }
    }

    /**
     * get
     */
    fun get(url: String, onResponse: (result: String) -> Unit) {
        val request = Request.Builder()
                .url(url)
                .build()
        okHttpClient.newCall(request).execute(onResponse)
    }

    /**
     * get
     */
    fun get(url: String, paramMap: HashMap<String, String>, onResponse: (result: String) -> Unit) {
        val httpUrl = HttpUrl.parse(url)!!.newBuilder()
                .apply {
                    for ((name, value) in paramMap) {
                        addEncodedQueryParameter(name, value)
                    }
                }
                .build()
        val request = Request.Builder()
                .url(httpUrl)
                .build()
        okHttpClient.newCall(request).execute(onResponse)
    }

}