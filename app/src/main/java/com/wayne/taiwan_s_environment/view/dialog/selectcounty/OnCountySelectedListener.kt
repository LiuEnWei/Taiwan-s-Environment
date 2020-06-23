package com.wayne.taiwan_s_environment.view.dialog.selectcounty

import java.io.Serializable

interface OnCountySelectedListener: Serializable {
    fun onSelected(county: String)
}