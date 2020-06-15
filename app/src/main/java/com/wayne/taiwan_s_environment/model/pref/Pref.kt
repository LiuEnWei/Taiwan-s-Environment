package com.wayne.taiwan_s_environment.model.pref

class Pref(preferenceFileName: String) : AbstractPref(preferenceFileName) {

    private val isFirstStartPref = BooleanPref("IS_FIRST_START", true)

    var isFirstStartApp: Boolean
        get() = isFirstStartPref.get()
        set(value) = isFirstStartPref.set(value)
}