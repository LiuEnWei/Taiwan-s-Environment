package com.wayne.taiwan_s_environment.view.splash

import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.wayne.taiwan_s_environment.R
import com.wayne.taiwan_s_environment.model.api.ApiResult
import com.wayne.taiwan_s_environment.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SplashFragment : BaseFragment() {
    private val viewModel by viewModel<SplashViewModel>()

    private var vectorDrawable: AnimatedVectorDrawable? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uvList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiResult.Success -> {
                    vectorDrawable?.stop()
                    if (viewModel.isFirstStartApp) {
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToIntroFragment())
                    } else {
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                    }
                }

                is ApiResult.Error -> {
                    Timber.e("${it.throwable}")
                    viewModel.getUV()
                    //TODO show error message
                }
            }
        })

        viewModel.getUV()

        showErrorMessageDialog()
        startSplashAnimator()
    }

    private fun startSplashAnimator() {
        vectorDrawable = (img_outline_taiwan.drawable as AnimatedVectorDrawable)
        vectorDrawable?.start()
        startTextAnimator(text_t,2500)
        startTextAnimator(text_a_1,2600)
        startTextAnimator(text_i,2700)
        startTextAnimator(text_w,2800)
        startTextAnimator(text_a_2,2900)
        startTextAnimator(text_n,3000)
    }

    private fun startTextAnimator(view: View, startDelay: Long) {
        view.translationY = 15f
        view.alpha = 0f
        val textTranslationY = ObjectAnimator.ofFloat(view, "translationY", 15f, -2f , 0f)
        textTranslationY.duration = 500
        textTranslationY.interpolator = AccelerateDecelerateInterpolator()
        textTranslationY.startDelay = startDelay
        textTranslationY.start()

        val textAlpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 0.5f, 1f)
        textAlpha.duration = 500
        textAlpha.interpolator = LinearInterpolator()
        textAlpha.startDelay = startDelay
        textAlpha.start()
    }
}