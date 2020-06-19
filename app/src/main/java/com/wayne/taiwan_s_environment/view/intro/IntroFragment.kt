package com.wayne.taiwan_s_environment.view.intro

import android.os.Bundle
import android.view.View
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO
class IntroFragment : BaseFragment(R.layout.fragment_intro) {
    private val viewModel by viewModel<IntroViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}