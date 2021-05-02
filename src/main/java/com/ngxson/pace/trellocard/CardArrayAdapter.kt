package com.ngxson.pace.trellocard

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import java.text.SimpleDateFormat
import java.util.*

class CardArrayAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    private val values: Array<String>,
    private val sharedPref: SharedPreferences
) : ArrayAdapter<String>(context, layoutResource, values)
{
    private lateinit var clockTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var updatedTextView: TextView
    private var runnableUpdateClock = Runnable { }
    private var mHandler: Handler = Handler()

    fun getNextRunTime(): Long {
        val now = SystemClock.uptimeMillis()
        return now + (60000 - (SystemClock.uptimeMillis() % 60000))
    }

    fun updateData() {
        val content = sharedPref.getString("content", "Swipe down to refresh")
        val updated = sharedPref.getString("updated", "---")
        contentTextView.text = content
        updatedTextView.text = updated
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutResource, parent, false)
        }
        clockTextView = convertView?.findViewById<TextView>(R.id.clock)!!
        contentTextView = convertView?.findViewById<TextView>(R.id.content)
        updatedTextView = convertView?.findViewById<TextView>(R.id.updated_at)
        updateData()

        runnableUpdateClock = Runnable {
            val formatter = SimpleDateFormat("HH:mm")
            val date = Date()
            clockTextView.text = formatter.format(date)
            mHandler.postAtTime(runnableUpdateClock, getNextRunTime())
        }

        runnableUpdateClock.run()

        return convertView as View
    }
}
