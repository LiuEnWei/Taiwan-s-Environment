package com.wayne.taiwan_s_environment.view.intro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.view.adapter.IntroPagerAdapter
import com.wayne.taiwan_s_environment.view.adapter.transformer.IntroPageTransformer
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_intro.*

class IntroFragment : BaseFragment(R.layout.fragment_intro), View.OnClickListener {
    private val viewModel by viewModels<IntroViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_skip.setOnClickListener(this)
        btn_start_now.setOnClickListener(this)

        view_pager.adapter = IntroPagerAdapter()
        view_pager.offscreenPageLimit = 3
        view_pager.setPageTransformer(IntroPageTransformer())
        indicator.setViewPager2(view_pager)
    }

    override fun getStatusBarColor(): Int {
        return R.color.colorAmber500
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.text_skip ,R.id.btn_start_now -> {
                if (viewModel.isFirstStartApp()) {
                    viewModel.introFinish()
                    findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToHomeFragment())
                } else {
                    findNavController().popBackStack()
                }
            }
        }
    }
}