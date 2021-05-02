package com.ngxson.pace.trellocard

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kieronquinn.library.amazfitcommunication.internet.LocalHTTPRequest
import com.kieronquinn.library.amazfitcommunication.internet.LocalHTTPResponse
import com.kieronquinn.library.amazfitcommunication.internet.LocalURLConnection
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var listView: ListView
    private lateinit var sharedPref: SharedPreferences
    private lateinit var adapter: CardArrayAdapter
    private val utils: Utils = Utils()
    private var renderNote: Runnable = Runnable {
        adapter.updateData()
        swipeRefreshLayout.isRefreshing = false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.hide()

        swipeRefreshLayout = findViewById(R.id.swipetorefresh)
        listView = findViewById(R.id.listView)
        sharedPref = this.getSharedPreferences("pref", Context.MODE_PRIVATE)

        val listItems = Array<String>(1, { "" } )
        listItems[0] = ""
        adapter = CardArrayAdapter(this, R.layout.main_view, listItems, sharedPref)
        listView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            fetchCard()
        }
    }

    fun fetchCard() {
        val localURLConnection = LocalURLConnection()
        localURLConnection.isDoOutput = false
        localURLConnection.isUseCaches = false

        try {
            val params = utils.getParams(this)
            val card = params[0]
            val key = params[1]
            val token = params[2]
            localURLConnection.url = URL("https://api.trello.com/1/cards/${card}?key=${key}&token=${token}")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        LocalHTTPRequest(this, localURLConnection, object : LocalHTTPResponse {
            override fun onResult(httpURLConnection: HttpURLConnection) {
                try {
                    var response = IOUtils.toString(httpURLConnection.getInputStream())
                    val convertedObject: JsonObject = Gson().fromJson(response, JsonObject::class.java)
                    var desc: String = convertedObject.get("desc").asString

                    val formatter = SimpleDateFormat("dd/MM HH:mm")
                    val date = Date()

                    sharedPref.edit()
                            .putString("content", desc)
                            .putString("updated", formatter.format(date))
                            .apply()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                this@MainActivity.runOnUiThread(renderNote)
            }

            override fun onConnectError() {
                Toast.makeText(this@MainActivity, "Connect Error", Toast.LENGTH_SHORT).show()
                this@MainActivity.runOnUiThread(renderNote)
            }

            override fun onTimeout() {
                Toast.makeText(this@MainActivity, "Timeout", Toast.LENGTH_SHORT).show()
                this@MainActivity.runOnUiThread(renderNote)
            }
        })
    }

}