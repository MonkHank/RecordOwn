package com.monk.androidtest.kotlinxc

import android.annotation.SuppressLint
import com.monk.commonutils.LoggerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

interface GithubApi {
    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
            @Query("q") query: String,
            @Query("page") page: Int,
            @Query("per_page") itemsPerPage: Int,
    ):RepoSearchResponse

    companion object{
        private const val BASE_URL="https://api.github.com/"

        private val myTrustManager: MyTrustManager
            get() {
                return MyTrustManager()
            }

        private fun createSSLSocketFactory(): SSLSocketFactory {
            var ssfFactory: SSLSocketFactory? = null
            try {
                val mMyTrustManager = myTrustManager
                val sc = SSLContext.getInstance("TLS")
                sc.init(null, arrayOf(mMyTrustManager), SecureRandom())
                ssfFactory = sc.socketFactory
            } catch (ignored: Exception) {
                ignored.printStackTrace()
            }
            return ssfFactory!!
        }

        //实现X509TrustManager接口
        class MyTrustManager : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        }

        fun createGithubApi(): GithubApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
//                    .sslSocketFactory(createSSLSocketFactory(),myTrustManager)
//                    .hostnameVerifier { _, _ -> true }
//                    .addNetworkInterceptor(LoggerInterceptor())
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GithubApi::class.java)
        }
    }
}