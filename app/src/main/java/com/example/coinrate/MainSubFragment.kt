package com.example.coinrate

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinrate.model.CoinCompany
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainSubFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_main_sub, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.listView)

        val adapter = ItemAdapter(viewModel.companies.value!!)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        viewModel.companies.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                adapter.viewData = it
                adapter.notifyDataSetChanged()
            }
        }

        adapter.setOnItemClickListener(object:ItemAdapter.OnItemClickListener{
            override fun onItemClickListener(view: View, position: Int, itemDetail: CoinCompany) {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(viewModel.selectedItem.value!!)
                viewHolder?.itemView?.setBackgroundColor(Color.WHITE)
                view.setBackgroundColor(Color.rgb(204,197,253))
                viewModel.selectCompany(itemDetail, position)
            }
        })
    }
}