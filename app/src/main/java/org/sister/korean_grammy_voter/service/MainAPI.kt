package org.sister.korean_grammy_voter.service

import android.app.Application
import android.util.Log
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sister.korean_grammy_voter.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MainAPI :Application(){
    //inisialisasi socket dari library Socket.io
    lateinit var socket: Socket
    //inisialisasi URL dari server
    companion object{
        const val URL = "http://192.168.43.115:8080/"
    }
    //inisialisasi library retrofit guna mengambil data dari server
    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY else
                HttpLoggingInterceptor.Level.NONE
        }).readTimeout(30 , TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .build()
    private val retrofit =Retrofit.Builder()
        .baseUrl(URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //memasangkan ApiInterface yang didalamnya terdapat method RPC ke retrofit
    val service : ApiInterface = retrofit.create(ApiInterface :: class.java)
    //inisialisasi/integrasi socket dengan url server dengan kembalian socket yang telah terintegrasi
    fun getSocketInstance() : Socket {
        try {
            socket = IO.socket(URL)
        }catch (e : Exception){
            throw RuntimeException(e)
            Log.e("TAG","terpanggil")
        }
        return socket
    }
}