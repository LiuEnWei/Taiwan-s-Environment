package com.wayne.taiwan_s_environment.view.more

import android.os.Bundle
import android.view.View
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoreFragment : BaseFragment() {

    companion object {

    }

    private val viewModel by viewModel<MoreViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_more
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun isBottomNavigationShow(): Boolean {
        return true
    }
}