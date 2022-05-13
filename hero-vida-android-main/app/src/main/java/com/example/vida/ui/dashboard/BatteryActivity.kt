package com.example.vida.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.vida.R
import com.example.vida.databinding.ActivityBatteryBinding
import com.example.vida.ui.personaldetail.CustomSpinnerAdapter
import com.example.vida.ui.personaldetail.SpinnerItems
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue


class BatteryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBatteryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBatteryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        setUpSpinnerAdapter()
        initPieChart()
        drawLineChart()
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun setUpSpinnerAdapter() {
        val bloodList = arrayListOf<SpinnerItems>()

        bloodList.add(SpinnerItems("March 2022", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("February 2022", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("January 2022", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("December 2021", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("November 2021", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("October 2021", "R.drawable.ic_profile_image"))
        bloodList.add(SpinnerItems("September 2021", "R.drawable.ic_profile_image"))

        val customDropDownAdapter = CustomSpinnerAdapter(this, bloodList)
        binding.simpleSpinner.adapter = customDropDownAdapter
    }

    private fun initPieChart() {
        val pieData = ArrayList<SliceValue>()
        pieData.add(SliceValue(20f, ContextCompat.getColor(this, R.color.colorRed)))
        pieData.add(SliceValue(20f, ContextCompat.getColor(this, R.color.colorWarning)))
        pieData.add(SliceValue(25f, ContextCompat.getColor(this, R.color.colorButtonPrimary)))
        pieData.add(SliceValue(35f, ContextCompat.getColor(this, R.color.colorAvailable)))
        val pieChartData = PieChartData(pieData)
        pieChartData.setHasCenterCircle(true)
        binding.pieChart.pieChartData = pieChartData
    }

    private fun drawLineChart() {
        val lineEntries = getDataSet()
        val lineDataSet = LineDataSet(lineEntries, "")
        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet.isHighlightEnabled = true
        lineDataSet.lineWidth = 2f
        lineDataSet.color = ContextCompat.getColor(this, R.color.colorAvailable)
        lineDataSet.setCircleColor(ContextCompat.getColor(this, R.color.colorDarkGray))
        lineDataSet.circleRadius = 6f
//        lineDataSet.circleHoleRadius = 3f
        lineDataSet.setDrawHighlightIndicators(true)
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.highLightColor = Color.RED
        lineDataSet.valueTextSize = 12f
        lineDataSet.valueTextColor = Color.DKGRAY
        val lineData = LineData(lineDataSet)
        binding.reportingChart.description.text = ""
        binding.reportingChart.description.textSize = 12f
        binding.reportingChart.setDrawMarkers(true)
        binding.reportingChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.reportingChart.animateY(1000)
        binding.reportingChart.xAxis.isGranularityEnabled = true
        binding.reportingChart.xAxis.granularity = 1.0f
        binding.reportingChart.xAxis.labelCount = lineDataSet.entryCount
        binding.reportingChart.data = lineData
    }

    private fun getDataSet(): List<Entry> {
        val lineEntries: MutableList<Entry> = ArrayList()
        lineEntries.add(Entry(3f, 60f))
        lineEntries.add(Entry(6f, 80f))
        lineEntries.add(Entry(9f, 90f))
        lineEntries.add(Entry(11f, 90f))
        return lineEntries
    }
}