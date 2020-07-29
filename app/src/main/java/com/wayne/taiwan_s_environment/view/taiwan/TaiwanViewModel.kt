package com.wayne.taiwan_s_environment.view.taiwan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wayne.taiwan_s_environment.model.Repository
import com.wayne.taiwan_s_environment.model.db.vo.Home
import com.wayne.taiwan_s_environment.view.base.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.inject

class TaiwanViewModel : BaseViewModel() {

    private val repository: Repository by inject()

    private val _epaList = MutableLiveData<ArrayList<Home>>()
    val epaList: LiveData<ArrayList<Home>>
        get() = _epaList

    fun getUVByCounty(county: String? = null) {
        viewModelScope.launch {
            val list = repository.getAllNewestByCounty(county)
            _epaList.postValue(list)
        }
    }
}