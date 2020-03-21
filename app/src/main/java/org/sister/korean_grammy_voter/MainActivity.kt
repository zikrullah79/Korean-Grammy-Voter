package org.sister.korean_grammy_voter

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.sister.korean_grammy_voter.adapter.ClickAdapter
import org.sister.korean_grammy_voter.adapter.ListAdapter
import org.sister.korean_grammy_voter.data.Nominations
import org.sister.korean_grammy_voter.data.NominationsItem
import org.sister.korean_grammy_voter.data.VoteResponse
import org.sister.korean_grammy_voter.databinding.ActivityMainBinding
import org.sister.korean_grammy_voter.service.MainAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(),ClickAdapter {
    //inisiasi log
    companion object{
        const val TAG = "Korean-Grammy-LOG"
        const val INITIATED = "initiated"
    }
    //inisiasi library untuk view
    lateinit var binding : ActivityMainBinding
    //inisiasi library Socket.io
    lateinit var mSocket : Socket
    //inisiasi variable yang nantinya berisi list dari data yang dikirimkan server
    lateinit var list: ArrayList<List<NominationsItem>>
    var initiated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inisiasi library untuk view
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //inisiasi list
        list = ArrayList(5)
        //handler jika layar di rotasi
        if (savedInstanceState != null){
            initiated = true
            Log.e(TAG,"dawdwa "+initiated)
        }
        //inisialisasi socket dari MainAPI.kt
        mSocket = MainAPI().getSocketInstance()
        //mengirimkan data client ke server dengan request SaveCustomId
        var clientInfo = JsonObject()
            mSocket.connect()
            clientInfo.addProperty("macAddress",getMac(this))
            mSocket.emit("SaveCustomId", clientInfo)
            initiated=true
        //handler jika server mengirim data dengan title message
        mSocket.on("message", object : Emitter.Listener{
            override fun call(vararg args: Any?) {
                this@MainActivity.runOnUiThread(object : Runnable{
                    override fun run() {
                        //set data ke view
                        var nominations = Gson().fromJson(args[0].toString(),Nominations::class.java)
                        Toast.makeText(applicationContext,nominations.message,Toast.LENGTH_SHORT).show()
                        list.add(nominations.data?.get(0)?.nominations as List<NominationsItem>)
                        list.add(nominations.data?.get(1)?.nominations as List<NominationsItem>)
                        list.add(nominations.data?.get(2)?.nominations as List<NominationsItem>)
                        list.add(nominations.data?.get(3)?.nominations as List<NominationsItem>)
                        list.add(nominations.data?.get(4)?.nominations as List<NominationsItem>)
                        binding.tv1.text = nominations.data?.get(0)?.name
                        binding.rv1.apply {
                            layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
                            adapter = ListAdapter(list.get(0),nominations?.data?.get(0)?.id,this@MainActivity)
                        }
                        binding.tv2.text = nominations.data?.get(1)?.name
                        binding.rv2.apply {
                            layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
                            adapter = ListAdapter(list.get(1),nominations?.data?.get(1)?.id,this@MainActivity)
                        }
                        binding.tv3.text = nominations.data?.get(2)?.name
                        binding.rv3.apply {
                            layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
                            adapter = ListAdapter(list.get(2),nominations?.data?.get(2)?.id,this@MainActivity)
                        }
                        binding.tv4.text = nominations.data?.get(3)?.name
                        binding.rv4.apply {
                            layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
                            adapter = ListAdapter(list.get(3),nominations?.data?.get(3)?.id,this@MainActivity)
                        }
                        binding.tv5.text = nominations.data?.get(4)?.name
                        binding.rv5.apply {
                            layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
                            adapter = ListAdapter(list.get(4),nominations?.data?.get(4)?.id,this@MainActivity)
                        }
                    }
                })
            }
        })
        //method jika terdapat update dari server
        mSocket.on("update", object : Emitter.Listener{
            override fun call(vararg args: Any?) {
                this@MainActivity.runOnUiThread(object : Runnable{
                    override fun run() {
                        //update view berdasarkan data terbaru
                        var nominations = Gson().fromJson(args[0].toString(),Nominations::class.java)
                        list.set(0,nominations.data?.get(0)?.nominations as List<NominationsItem>)
                        list.set(1,nominations.data?.get(1)?.nominations as List<NominationsItem>)
                        list.set(2,nominations.data?.get(2)?.nominations as List<NominationsItem>)
                        list.set(3,nominations.data?.get(3)?.nominations as List<NominationsItem>)
                        list.set(4,nominations.data?.get(4)?.nominations as List<NominationsItem>)
                        binding.rv1.adapter =ListAdapter(list.get(0),nominations?.data?.get(0)?.id,this@MainActivity)
                        binding.rv2.adapter = ListAdapter(list.get(1),nominations?.data?.get(1)?.id,this@MainActivity)
                        binding.rv3.adapter = ListAdapter(list.get(2),nominations?.data?.get(2)?.id,this@MainActivity)
                        binding.rv4.adapter = ListAdapter(list.get(3),nominations?.data?.get(3)?.id,this@MainActivity)
                        binding.rv5.adapter = ListAdapter(list.get(4),nominations?.data?.get(4)?.id,this@MainActivity)
                    }
                })
            }
        })
    }

    //handler jika layar dirotasi
    override fun onSaveInstanceState(outState: Bundle) {
        Log.e(TAG,initiated.toString())
        outState?.putBoolean(INITIATED,initiated)
        super.onSaveInstanceState(outState)
    }

    //untuk mengambil mac address
    fun getMac(context: Context): String {
        val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo
        return info.macAddress.toUpperCase()
    }

    override fun vote(idNominations: Int?, idNominators: Int?) {
        //inisialisasi object json yang nantinya dikirim ke server
        val jsonObject = JsonObject()
        jsonObject.addProperty("id", 1)
        //mengakses method vote pada server
        jsonObject.addProperty("method", "vote")
        jsonObject.addProperty("jsonrpc", "2.0")
        val jsonObject2 = JsonObject()
        //memasukkan parameter
        jsonObject2.addProperty("idnominations",idNominations)
        jsonObject2.addProperty("idnominator",idNominators)
        jsonObject2.addProperty("macaddress",getMac(applicationContext))
        val nomArray = JsonArray()
        nomArray.add(jsonObject2)
        jsonObject.add("params", nomArray)
        //mengirim data ke server dengan retrofit
        MainAPI().service.voteNominations(jsonObject).enqueue(object  : Callback<VoteResponse>{
            override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                //handler jika request tidak berhasil
                Log.e(TAG,t.message)
            }

            override fun onResponse(call: Call<VoteResponse>, response: Response<VoteResponse>) {
                //handler jika request berhasil
                Log.i(TAG,response.body().toString())
            }
        })
    }
}
