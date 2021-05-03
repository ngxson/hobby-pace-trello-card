package com.ngxson.pace.trellocard

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.*
import java.lang.Exception
import java.util.*
import kotlin.math.ceil

class Utils {
    fun getParams(ctx: Context): List<String> {
        var raw = this.readFromFile("trello.txt")
        Log.d("trello", raw)

        raw = raw.replace("\r", "")
        val params = raw.split('\n')
        Log.d("trello", params.size.toString())
        Log.d("trello", params.toString())

        if (params.size < 3) {
            Toast.makeText(ctx, "Malformed trello.txt file", Toast.LENGTH_LONG).show()
            throw Exception()
        } else {
            return params
        }
    }

    fun readFromFile(nameFile: String): String {
        val sdcard: File = Environment.getExternalStorageDirectory()
        val file = File(sdcard, nameFile)
        val text = StringBuilder()

        try {
            val br = BufferedReader(FileReader(file))
            var line: String?
            while (br.readLine().also { line = it } != null) {
                text.append(line)
                text.append('\n')
            }
            br.close()
            return text.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return "";
    }

    fun getDelayTimeForClock(): Long {
        val now = Date().time
        val nowMinutes: Double = now.toDouble() / 60000
        val nextRunTime = ceil(nowMinutes).toLong() * 60000
        return nextRunTime - now + 1000
    }
}