package com.wayne.taiwan_s_environment.view.splash

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SplashFragment : BaseFragment(R.layout.fragment_splash) {
    private val viewModel by viewModel<SplashViewModel>()

    private var vectorDrawable: AnimatedVectorDrawable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uvList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Success -> {
                    vectorDrawable?.stop()
//                    if (viewModel.isFirstStartApp) {
//                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToIntroFragment())
//                    } else {
//                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
//                    }
                    // TODO
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                }

                is ApiResult.Error -> {
                    Timber.e("${it.throwable}")
                    viewModel.getOpenUV()
                    //TODO show error message
                }
            }
        })

        viewModel.getOpenUV()
        startSplashAnimator()
    }

    private fun startSplashAnimator() {
        vectorDrawable = (img_outline_taiwan.drawable as AnimatedVectorDrawable)
        vectorDrawable?.start()
    }

    override fun getStatusBarColor(): Int {
        return R.color.colorAmber500
    }
}