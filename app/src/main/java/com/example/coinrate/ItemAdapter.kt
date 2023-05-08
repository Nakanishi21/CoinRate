package com.example.coinrate

import android.graphics.Color
import android.graphics.Matrix
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrate.model.CoinCompany
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class ItemAdapter(private val companies: List<CoinCompany>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    var viewData = companies
    lateinit var listener: OnItemClickListener
    private var selectedPosition = -1

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView
        val priceTextView: TextView
        val changePercentageTextView: TextView
        init {
            textView = view.findViewById(R.id.itemTextView)
            imageView = view.findViewById(R.id.imageView)
            priceTextView = view.findViewById(R.id.itemPriceText)
            changePercentageTextView = view.findViewById(R.id.itemChangePercentageText)
            val matrix = Matrix()
            matrix.setScale(0.5f, 0.5f)
            imageView.imageMatrix = matrix
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.coin_company_item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = viewData[position]
        viewHolder.textView.text = item.name
        viewHolder.priceTextView.text = item.currentPrice.toString()

        val roundedPercentage = (item.priceChangePercentage * 100).roundToInt() / 100.0
        viewHolder.changePercentageTextView.text = "${roundedPercentage}%"

        if(roundedPercentage < 0){
            viewHolder.changePercentageTextView.setTextColor(Color.RED)
        }

        Picasso.get().load(item.image)
            .resize(70, 70)
            .into(viewHolder.imageView)
        Log.d("URL", item.image)

        viewHolder.itemView.setOnClickListener {
            selectedPosition = viewHolder.bindingAdapterPosition
            listener.onItemClickListener(it, position, viewData[position])
        }
    }

    override fun getItemCount() = viewData.size

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setBackgroundColor(Color.WHITE)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if(selectedPosition == holder.bindingAdapterPosition){
            holder.itemView.setBackgroundColor(Color.rgb(204,197,253))
        }
    }

    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, itemDetail: CoinCompany)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}

