package com.wayne.taiwan_s_environment.view.intro

import android.os.Bundle
import android.view.View
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class IntroFragment : BaseFragment() {

    companion object {

    }

    private val viewModel by viewModel<IntroViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_intro
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}