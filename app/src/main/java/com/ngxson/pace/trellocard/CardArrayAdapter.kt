package com.ngxson.pace.trellocard

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
import kotlin.math.ceil

class CardArrayAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    private val values: Array<String>,
    private val sharedPref: SharedPreferences
) : ArrayAdapter<String>(context, layoutResource, values)
{
    private lateinit var titleTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var updatedTextView: TextView
    private var hasInitView = false

    fun updateData() {
        if (!hasInitView) return
        val name = sharedPref.getString("name", "Swipe down to refresh")
        val content = sharedPref.getString("desc", "")
        val updated = sharedPref.getString("updated", "None")
        titleTextView.text = name
        contentTextView.text = content
        updatedTextView.text = "Last updated: ${updated}"
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutResource, parent, false)
        }
        titleTextView = convertView?.findViewById<TextView>(R.id.card_title)!!
        contentTextView = convertView.findViewById<TextView>(R.id.content)
        updatedTextView = convertView.findViewById<TextView>(R.id.updated_at)
        hasInitView = true

        updateData()

        return convertView as View
    }
}
