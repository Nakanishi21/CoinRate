package com.example.coinrate

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.button.MaterialButtonToggleGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_main, container, false)

        val toggleButtonGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)
        toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if(isChecked) {
                when(checkedId) {
                    R.id.oneDay -> viewModel.setSearchDays(1)
                    R.id.oneWeek -> viewModel.setSearchDays(7)
                    R.id.oneMonth -> viewModel.setSearchDays(30)
                }
            }
        }

        viewModel.coinValues.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                val x = viewModel.dateTimes.value!!
                val y = viewModel.coinValues.value!!
                val entryList = mutableListOf<Entry>()//1本目の線
                for(i in x.indices){
                    entryList.add(Entry(x[i], y[i]))
                }
                val lineDataSets = mutableListOf<ILineDataSet>()
                val lineDataSet = LineDataSet(
                    entryList,
                    viewModel.selectedCompany.value!!.name
                )
                lineDataSet.color = Color.BLUE
                lineDataSet.setDrawValues(false)
                lineDataSet.setDrawCircles(false)
                lineDataSets.add(lineDataSet)

                val lineData = LineData(lineDataSets)
                val lineChart = view.findViewById<LineChart>(R.id.lineChart)

                val mv = CoinMarkerView(requireContext(), R.layout.coin_marker)
                mv.chartView = lineChart
                lineChart.marker = mv
                lineChart.description.isEnabled = false
                lineChart.data = lineData
                lineChart.xAxis.apply {
                    isEnabled = false
                    textColor = Color.BLACK
                }
                lineChart.invalidate()
            }
        }
        return view
    }
}