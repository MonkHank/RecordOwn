package com.monk.commonutils

import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import com.monk.commonutils.l.e2
import com.monk.commonutils.l.v2
import okhttp3.*
import okio.Buffer
import java.io.IOException

class LoggerInterceptor(tag: String="LoggerInterceptor",
                        showResponse: Boolean = true) : Interceptor {
    private val TAG = "LoggerInterceptor"
    private var tag: String=""
    private val showResponse: Boolean

    init {
        if (TextUtils.isEmpty(tag)) {
            this.tag = TAG
        }
        this.showResponse = showResponse
        this.tag = tag
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        return logForResponse(request, response)
    }

    private fun logForResponse(request: Request, response: Response): Response {
        try {
            //===>response log
            e2(tag, "========response'log============start")
            val builder: Response.Builder = response.newBuilder()
            val clone: Response = builder.build()
            val url = clone.request.url
            e2(tag, "url : $url")
            if (!TextUtils.isEmpty(clone.message)) {
                e2(tag, "message : " + clone.message)
                if ("OK" != clone.message) {
                    ToastUtils.showLong(clone.message)
                }
            }
            logForRequest(request)
            if (showResponse) {
                var body = clone.body
                if (body != null) {
                    val mediaType = body.contentType()
                    if (mediaType != null) {
                        v2(tag, "responseBody's contentType : $mediaType")
                        if (isText(mediaType)) {
                            val resp = body.string()
                            v2(tag, "responseBody's content : $resp")
                            body = ResponseBody.create(mediaType, resp)
                            e2(tag, "========response'log=====================end")
                            return response.newBuilder().body(body).build()
                        } else {
                            v2(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!")
                        }
                    }
                }
            }
        } catch (e: Exception) {
        }
        return response
    }

    private fun logForRequest(request: Request) {
        try {
            val headers = request.headers
            e2(tag, "method : " + request.method)
            if (headers.size > 0) {
                e2(tag, "headers : $headers")
            }
            val requestBody = request.body
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    e2(tag, "requestBody's contentType : $mediaType")
                    e2(tag, "mediaType.type：" + mediaType.type)
                    e2(tag, "mediaType.subtype：" + mediaType.subtype)
                    if (isText(mediaType)) {
                        e2(tag, "requestBody's content : " + bodyToString(request))
                    } else {
                        e2(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }
            e2(tag, "========request'log===================end")
        } catch (e: Exception) {
//            e.printStackTrace();
        }
    }

    private fun isText(mediaType: MediaType): Boolean {
        if ("text" == mediaType.type) return true
        val subtype = mediaType.subtype
        return "json" == subtype || "xml" == subtype || "html" == subtype || "webviewhtml" == subtype || "x-www-form-urlencoded" == subtype
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "something error when show requestBody."
        }
    }


}