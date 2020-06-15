package com.wayne.taiwan_s_environment.view.taiwan

import android.os.Bundle
import android.view.View
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class TaiwanFragment : BaseFragment() {

    companion object {

    }

    private val viewModel by viewModel<TaiwanViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun isBottomNavigationShow(): Boolean {
        return true
    }
}