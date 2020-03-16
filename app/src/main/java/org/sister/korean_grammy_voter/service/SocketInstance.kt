package org.sister.korean_grammy_voter.service

import android.app.Application
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.lang.Exception

class SocketInstance : Application(){
    lateinit var socket:Socket
    companion object{
        const val URL = "http://192.168.1.14:8080/"
    }

    override fun onCreate() {
        super.onCreate()
        try {
            socket = IO.socket(URL)
        }catch (e : Exception){
            throw RuntimeException(e)
        }
    }
    fun getSocketInstance() : Socket = socket
}