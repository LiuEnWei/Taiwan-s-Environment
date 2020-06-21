package com.wayne.taiwan_s_environment.model.pref

class Pref(preferenceFileName: String) : AbstractPref(preferenceFileName) {

    private val isFirstStartPref = BooleanPref("IS_FIRST_START", true)
    private val countyPref = StringPref("COUNTY", "")


    var isFirstStartApp: Boolean
        get() = isFirstStartPref.get()
        set(value) = isFirstStartPref.set(value)

    var county: String?
        get() = countyPref.get()
        set(value) = countyPref.set(value)
}