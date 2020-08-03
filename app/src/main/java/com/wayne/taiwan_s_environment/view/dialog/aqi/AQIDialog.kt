package com.wayne.taiwan_s_environment.view.dialog.aqi

import android.os.Bundle
import android.view.View
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.utils.getAQIColor
import com.wayne.taiwan_s_environment.view.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_aqi.*
import kotlinx.android.synthetic.main.item_aqi_more.*
import kotlinx.android.synthetic.main.item_taiwan_aqi.*
import java.lang.Exception
import kotlin.math.ceil

class AQIDialog: BaseDialogFragment(R.layout.dialog_aqi) {
    companion object {
        private const val KEY_AQI = "KEY_AQI"
        fun newInstance(aqi: Home): AQIDialog {
            return AQIDialog().apply {
                arguments = Bundle().also {
                    it.putSerializable(KEY_AQI, aqi)
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = true
        val aqi = arguments?.getSerializable(KEY_AQI) as Home

        text_site_name.text = aqi.siteName

        try {
            val aqiValue = ceil(aqi.aqi!!.toDouble()).toInt()
            text_aqi.text = aqiValue.toString()
            text_aqi.setTextColor(requireContext().getAQIColor(aqiValue))
        } catch (e: Exception) {
            text_aqi.text = "-"
            text_aqi.setTextColor(requireContext().getAQIColor(-1))
        }
        text_agency.text = getString(R.string.environmental_protection_administration)
        text_status.text = getString(R.string.brackets, aqi.status + if (!aqi.pollutant.isNullOrEmpty()) { " ${aqi.pollutant}" } else { "" })

        img_chevron_right.visibility = View.GONE
        aqi_title.setBackgroundResource(R.drawable.bg_white)
        text_pm_2_5_avg.text = aqi.PM2_5_AVG
        text_pm_2_5.text = aqi.PM2_5
        text_pm_10_avg.text = aqi.PM10_AVG
        text_pm_10.text = aqi.PM10
        text_o3_8hr_avg.text = aqi.O3_8hr
        text_o3.text = aqi.O3
        text_c_o_8hr_avg.text = aqi.CO_8hr
        text_c_o.text = aqi.CO
        text_s_o_2_avg.text = aqi.SO2_AVG
        text_s_o_2.text = aqi.SO2
        text_n_o_2.text = aqi.NO2

        parent_layout.setOnClickListener {
            this.dismiss()
        }
    }

    override fun isFull(): Boolean {
        return true
    }
}