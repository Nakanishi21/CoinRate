package com.example.coinrate

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CoinMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource){
    private lateinit var textView: TextView
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        textView = findViewById(R.id.markerText)

        val date = Date(e?.x!!.toLong())
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val formattedDate = format.format(date)
        textView.text = "¥ ${e?.y}\n${formattedDate}"
        super.refreshContent(e, highlight)
    }
    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -height.toFloat() - 20f)
    }
}