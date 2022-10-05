package com.crazycoder.prodzy.rest.retrofit.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class NetworkInterceptor(private val context: Context) : Interceptor {

    private val networkEvent: NetworkEvent = NetworkEvent

    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()

        /*
         * We check if there is internet
         * available in the device. If not, pass
         * the networkState as NO_INTERNET.
         * */

        if (!ConnectivityStatus.isConnectionAvailable(context)) {
            networkEvent.publish(NetworkState.NO_INTERNET)
        } else {
            try {
                /*
                 * Get the response code from the
                 * request. In this scenario we are only handling
                 * unauthorised and server unavailable error
                 * scenario
                 * */
                var response = chain.proceed(request)
                when (response.code()) {
                    204 -> {
                        response = response.newBuilder().code(200)
                            .body(ResponseBody.create(MediaType.parse("application/json"), "{}"))
                            .build()
                    }

                    401, 403 -> networkEvent.publish(NetworkState.UNAUTHORISED)

                    500 -> networkEvent.publish(NetworkState.SERVER_ERROR)

                    503 -> networkEvent.publish(NetworkState.NO_RESPONSE)
                }
                return response

            } catch (e: IOException) {
                networkEvent.publish(NetworkState.NO_RESPONSE)
            }
        }
        return null
    }
}